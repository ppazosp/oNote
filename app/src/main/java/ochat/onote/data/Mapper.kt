package ochat.onote.data

import android.content.Context
import ochat.onote.backend.Class
import ochat.onote.backend.Files
import ochat.onote.backend.Reminder
import ochat.onote.backend.Subject
import ochat.onote.backend.fetchClassFiles
import ochat.onote.backend.fetchClasses
import ochat.onote.backend.fetchFiles
import ochat.onote.backend.fetchReminder
import ochat.onote.backend.fetchStreamingClass
import ochat.onote.backend.fetchSubjects
import ochat.onote.utils.decodeBase64Image
import java.time.LocalDate
import java.time.LocalDateTime

fun subjectToUISubject(subject: Subject, context: Context): UISubject {

    return UISubject(subject.name, decodeBase64Image(subject.photo, context))
}

suspend fun getSubjects(context: Context): List<UISubject> {
    val subjectList = fetchSubjects()
    return subjectList.map { subjectToUISubject(it, context) }
}

fun filesToUIFiles(files: Files): UIFiles{
    return UIFiles(files.name, files.ext, files.url, files.owner, LocalDate.parse(files.date))
}

suspend fun getFiles(subject: String): List<UIFiles> {
    val filesList = fetchFiles(subject)
    return filesList.map { filesToUIFiles(it) }
}

fun classToUIClass(classs: Class): UIClass{
    return UIClass(classs.id, classs.name, classs.teacher, LocalDateTime.parse(classs.startDate), LocalDateTime.parse(classs.endDate))
}

suspend fun getClasses(subject: String): List<UIClass> {
    val classesList = fetchClasses(subject)
    return classesList.map { classToUIClass(it) }
}

fun reminderToUIReminder(reminder: Reminder): UIReminder{
    return UIReminder(reminder.name, LocalDateTime.parse(reminder.date), reminder.description, reminder.subject)
}

suspend fun getReminders(subject: String): List<UIReminder> {
    val remindersList = fetchReminder(subject)
    return remindersList.map { reminderToUIReminder(it) }
}

fun filesToUIFilesSimple(files: Files): UIFilesSimple{
    return UIFilesSimple(files.name, files.ext, files.url)
}

suspend fun getStreamingClass(classname: String, subject: String): UIStreamingClass {
    val streamingClass = fetchStreamingClass(classname, subject)
    val fileList = fetchClassFiles(classname, subject)
    val uifilesList = fileList.map { filesToUIFilesSimple(it) }

    return UIStreamingClass(streamingClass.name, streamingClass.teacher, streamingClass.transcript, streamingClass.resume, uifilesList)
}