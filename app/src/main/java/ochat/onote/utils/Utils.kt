package ochat.onote.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.bson.types.Binary
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun LocalDate.formatDate(): String {
    return this.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es", "ES")))
}

fun binaryToImageBitmap(binary: Binary): ImageBitmap? {
    return try {
        val byteArray = binary.data
        val inputStream = ByteArrayInputStream(byteArray)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}