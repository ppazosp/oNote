package ochat.onote.data

import ochat.onote.backend.Subject
import ochat.onote.db
import ochat.onote.utils.binaryToImageBitmap

fun subjectToUISubject(subject: Subject): UISubject{
    val imageBitmap = binaryToImageBitmap(subject.photo);
    return UISubject(subject.name, imageBitmap!!)
}

suspend fun get_subjects(): List<UISubject>{
    val subjectList = db.obtenerSubject()
    return subjectList.map { subjectToUISubject(it) }
}