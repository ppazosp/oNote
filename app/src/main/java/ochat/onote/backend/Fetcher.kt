package ochat.onote.backend

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNames
import java.time.LocalDate
import java.time.LocalDateTime


val client = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 15_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 15_000
    }
}

@Serializable
data class Subject(
    val id: String,
    val name: String,
    val photo: String

)

@Serializable
data class Files(
    val id: String,
    val name: String,
    val subject: String,
    val ext: String,
    val url: String,
    val owner: String,
    val date: String,
    val classes: List<String>
)

@Serializable
data class Class(
    val id: String,
    val name: String,
    val teacher: String,
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String,
    val subject: String,
)

@Serializable
data class Reminder(
    val id: String,
    val name: String,
    val date: String,
    val description: String,
    val subject: String
)

suspend fun fetchSubjects(): List<Subject> {

    Log.d("FETCH", "GettingSubjects...")

    val response: HttpResponse = client.get("http://100.68.193.4:8080/subjects")

    Log.d("FETCH", "Got Subjects")

    val subjects: List<Subject> = response.body()

    return subjects
}

suspend fun fetchFiles(subject: String): List<Files> {

    Log.d("FETCH", "GettingFiles...")

    val response: HttpResponse = client.get("http://100.68.193.4:8080/files"){
        parameter("subject", subject)
    }

    Log.d("FETCH", "Got Files")

    val files: List<Files> = response.body()

    return files
}

suspend fun fetchClasses(subject: String): List<Class> {
    Log.d("FETCH", "Getting Classes...")

    val response: HttpResponse = client.get("http://100.68.193.4:8080/classes") {
        parameter("subject", subject)
    }

    Log.d("FETCH", "Got Classes")

    return response.body()
}

suspend fun fetchReminder(subject: String): List<Reminder> {

    Log.d("FETCH", "GettingReminders...")

    val response: HttpResponse = client.get("http://100.68.193.4:8080/reminders") {
        parameter("subject", subject)
    }

    Log.d("FETCH", "Got Reminders")

    val reminders: List<Reminder> = response.body()

    return reminders
}

