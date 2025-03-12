package ochat.onote.backend

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
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

suspend fun fetchSubjects(): List<Subject> {

    Log.d("FETCH", "GettingSubjects...")

    val response: HttpResponse = client.get("http://10.0.2.2:8080/subjects")

    Log.d("FETCH", "Got Subjects")

    val subjects: List<Subject> = response.body()

    return subjects
}

suspend fun fetchFiles(): List<Files> {

    Log.d("FETCH", "GettingFiles...")

    val response: HttpResponse = client.get("http://10.0.2.2:8080/files")

    Log.d("FETCH", "Got Files")

    val files: List<Files> = response.body()

    return files
}

suspend fun fetchClasses(): List<Class> {

    Log.d("FETCH", "GettingClasses...")

    val response: HttpResponse = client.get("http://10.0.2.2:8080/classes")

    Log.d("FETCH", "Got Classes")

    val classes: List<Class> = response.body()

    return classes
}

