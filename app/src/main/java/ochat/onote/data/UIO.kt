package ochat.onote.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Base64

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
    val startDate: LocalDate,
    val endDate: LocalDate,
)

data class UIAttachment(
    val id: String,
    val name: String,
    val ext: String,
    val owner: String,
    val date: LocalDate,
)

data class UIAttachmentSimple(
    val id:String,
    val name: String,
    val ext: String,
)

data class UISubject(
    val name: String,
    val banner: ImageBitmap,
)

data class UISubjectData(
    val calendar: Map<LocalDate, List<Task>>,
    val classes: List<UIClass>,
    val attachments: List<UIAttachment>
)

