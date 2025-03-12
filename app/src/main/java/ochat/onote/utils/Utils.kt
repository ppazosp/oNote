package ochat.onote.utils

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import ochat.onote.ui.theme.USColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.Locale


val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun LocalDate.formatDate(): String {
    return this.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es", "ES")))
}

fun Color.toArgbColor(): Int {
    return android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}

fun createPlaceholderBitmap(): Bitmap {
    val size = 1
    val placeholderBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(placeholderBitmap)
    val paint = Paint().apply { color = USColor.toArgbColor() }
    canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paint)
    return placeholderBitmap
}

fun decodeBase64Image(base64: String, context: Context): Bitmap {
    return try {
        val cleanBase64 = base64.substringAfter("base64,")

        val decodedBytes = Base64.getDecoder().decode(cleanBase64)

        val options = BitmapFactory.Options().apply { inPreferredConfig = Bitmap.Config.ARGB_8888 }
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size, options)
            ?: throw IllegalArgumentException("Decoded bitmap is null")

    } catch (e: Exception) {
        Log.e("ERROR", "Failed to decode Base64 image: ${e.message}")

        createPlaceholderBitmap()
    }
}

fun downloadFile(context: Context, url: String, fileName: String, fileExt: String) {
    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle(fileName)
        .setDescription("Downloading $fileName.$fileExt")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName.$fileExt")
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)

    Toast.makeText(context, "Descargando $fileName.$fileExt", Toast.LENGTH_SHORT).show()
}
