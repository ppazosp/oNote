package ochat.onote.data

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import ochat.onote.backend.Class
import ochat.onote.backend.Files
import ochat.onote.backend.Reminder
import ochat.onote.backend.Subject
import ochat.onote.backend.fetchClasses
import ochat.onote.backend.fetchFiles
import ochat.onote.backend.fetchReminder
import ochat.onote.backend.fetchSubjects
import ochat.onote.utils.decodeBase64Image
import java.time.LocalDate
import java.time.LocalDateTime

fun subjectToUISubject(subject: Subject, context: Context): UISubject {

    return UISubject(subject.name, decodeBase64Image(subject.photo, context))
}

fun Color.toArgbColor(): Int {
    return android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}


suspend fun getSubjects(context: Context): List<UISubject> {
    val subjectList = fetchSubjects()
    return subjectList.map { subjectToUISubject(it, context) }
}

fun filesToUIFiles(files: Files): UIFiles{
    return UIFiles(files.name, files.ext, files.url, files.owner, LocalDate.parse(files.date))
}

suspend fun getFiles(): List<UIFiles> {
    val filesList = fetchFiles()
    return filesList.map { filesToUIFiles(it) }
}

fun classToUIClass(classs: Class): UIClass{
    return UIClass(classs.id, classs.name, classs.teacher, LocalDateTime.parse(classs.startDate), LocalDateTime.parse(classs.endDate))
}

suspend fun getClasses(): List<UIClass> {
    val classesList = fetchClasses()
    return classesList.map { classToUIClass(it) }
}

fun reminderToUIReminder(reminder: Reminder): UIReminder{
    return UIReminder(reminder.id, reminder.name, LocalDateTime.parse(reminder.date), reminder.description, reminder.subject)
}

suspend fun getReminders(): List<UIReminder> {
    val remindersList = fetchReminder()
    return remindersList.map { reminderToUIReminder(it) }
}