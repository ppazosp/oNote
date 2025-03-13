package ochat.onote.backend.chat

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import io.ktor.client.call.body
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import ochat.onote.backend.DriveFile
import ochat.onote.backend.client
import java.io.InputStream
import java.time.LocalDate



fun _guessMimeType(fileName: String): String {
    val mimeType = when (fileName.substringAfterLast('.', "").lowercase()) {
        "pdf" -> "application/pdf"
        "png" -> "image/png"
        "jpg", "jpeg" -> "image/jpeg"
        "txt" -> "text/plain"
        "json" -> "application/json"
        "mp4" -> "video/mp4"
        else -> "application/octet-stream"
    }
    return mimeType
}

fun getDriveFile(context: Context, uri: Uri?, subjectName: String, className: String): DriveFile? {
    uri?.let {
        val contentResolver = context.contentResolver

        val documentFile = DocumentFile.fromSingleUri(context, uri)
        var fileName = documentFile?.name

        if (fileName == null) {
            fileName = contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1 && cursor.moveToFirst()) {
                    cursor.getString(nameIndex)
                } else null
            }
        }

        if (fileName == null) {
            fileName = uri.lastPathSegment?.substringAfterLast("/")
        }

        fileName = fileName ?: "unknown_file"

        val mimeType = _guessMimeType(fileName)

        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.use { it.readBytes() }

        val fileBase64 = fileBytes?.let { Base64.encodeToString(it, Base64.NO_WRAP) }

        return DriveFile(
            name = fileName,
            subject = subjectName,
            ext = mimeType,
            url = "",
            classes = listOf(className),
            owner = "ochat@gmail.com",
            date = LocalDate.now().toString(),
             file64 = fileBase64 ?: ""
        )
    }

    return null
}

suspend fun uploadFile(driveFile: DriveFile): String? {
    Log.d("UPLOADING", "Putting driveFile...")

    return try {
        val response: HttpResponse = client.put("http://100.68.193.4:8080/upload") {
            contentType(ContentType.Application.Json)
            setBody(driveFile)
        }

        Log.d("UPLOADING", "Got response: ${response.status}")

        if (response.status.isSuccess()) {
            val url: String = response.body()
            Log.d("UPLOADING", "File uploaded successfully: $url")
            url
        } else {
            Log.e("UPLOADING", "Failed to upload file: ${response.status}")
            null
        }
    } catch (e: Exception) {
        Log.e("UPLOADING", "Error uploading file: ${e.message}", e)
        null
    }
}