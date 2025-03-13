package ochat.onote.backend

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class WebSocketService {
    private val client = HttpClient {
        install(WebSockets)
    }

    private val _transcriptionText = MutableStateFlow("")
    val transcriptionText = _transcriptionText.asStateFlow()

    suspend fun connect() {
        while (coroutineContext.isActive) {
            try {
                client.webSocket(
                    method = HttpMethod.Get,
                    host = "100.87.246.86",
                    port = 8000,
                    path = "/"
                ) {
                    Log.d("WebSocket", "Connected to WebSocket server!")

                    launch {
                        while (isActive) {
                            send(Frame.Ping(ByteArray(0)))
                            delay(30000)
                        }
                    }

                    while (isActive) {
                        incoming.consumeEach { frame ->
                            if (frame is Frame.Text) {
                                val message = frame.readText()
                                _transcriptionText.value = message
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("WebSocket", "Connection failed, retrying in 5s: ${e.message}")
                delay(5000)
            }
        }
    }

    fun disconnect() {
        client.close()
    }
}