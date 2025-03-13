package ochat.onote.data

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val location: String,
    val type: TaskType
)

enum class TaskType(val color: Color) {
    FORMALLY( Color(0xFF000000)),
    SMARTLY( Color(0xFF455A64)),
    FESTIVE( Color(0xFFE91E63)),
    CASUAL( Color(0xFF2196F3)),
    SPORTIVE( Color(0xFF4CAF50)),
}

data class UIClass(
    val id: String,
    val name: String,
    val teacher: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)

data class UIFiles(
    val name: String,
    val ext: String,
    val url: String,
    val owner: String,
    val date: LocalDate,
)

data class UIFilesSimple(
    val name: String,
    val ext: String,
    val url: String
)

data class UISubject(
    val name: String,
    val banner: Bitmap,
)

data class UIReminder(
    val name: String,
    val date: LocalDateTime,
    val description: String,
    val subject: String
)

data class UIStreamingClass(
    val name: String,
    val teacher: String,
    val transcript: String,
    val resume: String,
    val files: List<UIFilesSimple>
)


