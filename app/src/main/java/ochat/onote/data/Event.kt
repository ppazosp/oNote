package ochat.onote.data

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val location: String,
    val type: EventType
)

enum class EventType(val color: Color) {
    FORMALLY( Color(0xFF000000)),
    SMARTLY( Color(0xFF455A64)),
    FESTIVE( Color(0xFFE91E63)),
    CASUAL( Color(0xFF2196F3)),
    SPORTIVE( Color(0xFF4CAF50)),
}