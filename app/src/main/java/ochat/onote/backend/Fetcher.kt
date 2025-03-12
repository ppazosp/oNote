package ochat.onote.backend

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import org.bson.types.Binary
import java.util.Base64


val client = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}

@Serializable
data class Subject(
    val id: String,
    val name: String,
    val photoBase64: String = ""
) {
    @Transient
    var photo: Binary = Binary(Base64.getDecoder().decode(photoBase64))
}

suspend fun fetchSubjects(): List<Subject> {

    val response: HttpResponse = client.get("http://10.0.2.2:8080/subjects")

    val subjects: List<Subject> = response.body()

    client.close()
    return subjects
}

