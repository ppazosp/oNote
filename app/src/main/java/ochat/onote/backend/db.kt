package ochat.onote.backend
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import org.bson.Document
import java.net.URLEncoder

/**
 * Clase de conexión a MongoDB en Kotlin usando Flow
 */
internal class Db {
    private val username = System.getenv("MONGO_USER") ?: "davidmanuelraposeiras"
    private val password = System.getenv("MONGO_PASS") ?: "Metalor01"
    private val databaseName = "impacthon"
    private val conectionString = "mongodb+srv://${URLEncoder.encode(username, "UTF-8")}:${URLEncoder.encode(password, "UTF-8")}" +
            "@impacthon.vd55b.mongodb.net/?retryWrites=true&w=majority&tls=true"

    private val mongoClient: MongoClient = MongoClient.create(conectionString)
    private val database: MongoDatabase = mongoClient.getDatabase(databaseName)

    fun close() {
        mongoClient.close()
    }

    /**
     * Obtiene los documentos de la colección 'subject' como un Flow
     */
    fun obtenerDocumentos(): Flow<Document> = flow {
        val collection = database.getCollection<Document>("subject")
        val documentos = collection.find().toList()
        documentos.forEach { emit(it) }
    }.flowOn(Dispatchers.IO) // Se ejecuta en un hilo de I/O para evitar bloquear el principal

    /**
     * Actualiza los códigos de todos los documentos de la colección 'subject'
     */
    suspend fun actualizarCodigos() {
        withContext(Dispatchers.IO) { // Ejecutar en un hilo de I/O
            val collection = database.getCollection<Document>("subject")
            val documentos = collection.find().toList()

            documentos.forEach { doc ->
                val nuevoCodigo = generarCodigo()
                collection.updateOne(eq("_id", doc["_id"]), Updates.set("code", nuevoCodigo))
                println("Código asignado a ${doc["name"]}: $nuevoCodigo")
            }

            println("Todos los documentos han sido actualizados con un código de 8 caracteres.")
        }
    }

    /**
     * Genera un código aleatorio de 8 caracteres
     */
    fun generarCodigo(): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..8).map { caracteres.random() }.joinToString("")
    }
}



