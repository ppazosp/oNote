package ochat.onote.data

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import ochat.onote.R
import ochat.onote.backend.Subject
import ochat.onote.backend.fetchSubjects
import ochat.onote.utils.binaryToImageBitmap

fun subjectToUISubject(subject: Subject, context: Context): UISubject {
    var imageBitmap = binaryToImageBitmap(subject.photo)
    if (imageBitmap == null) {
        val defaultBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.brain)
        imageBitmap = defaultBitmap.asImageBitmap()
    }
    return UISubject(subject.name, imageBitmap)
}

suspend fun getSubjects(context: Context): List<UISubject> {
    val subjectList = fetchSubjects()
    return subjectList.map { subjectToUISubject(it, context) }
}