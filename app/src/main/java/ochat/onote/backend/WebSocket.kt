package ochat.onote.backend

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive

class WebSocketService {
    private val client = HttpClient {
        install(WebSockets)
        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
        }
    }

    private val _transcriptionText = MutableStateFlow("")
    val transcriptionText = _transcriptionText.asStateFlow()

    suspend fun connect() {
        client.webSocket(
            method = HttpMethod.Get,
            host = "100.87.246.86",
            port = 8000,
            path = "/",
        ) {
            while (isActive) {
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        Log.d("MESSAGE", "Received WebSocket message: $frame")
                        _transcriptionText.value = frame.readText()
                    }
                }
            }
        }
    }

    fun disconnect() {
        client.close()
    }
}