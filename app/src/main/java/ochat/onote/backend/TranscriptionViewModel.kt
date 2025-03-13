package ochat.onote.backend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class TranscriptionViewModel : ViewModel() {
    private val webSocketService = WebSocketService()
    val transcriptionText = webSocketService.transcriptionText

    private var job: Job? = null

    fun startListening() {
        job = viewModelScope.launch {
            webSocketService.connect()
        }
    }

    fun stopListening() {
        job?.cancel()
        webSocketService.disconnect()
    }
}