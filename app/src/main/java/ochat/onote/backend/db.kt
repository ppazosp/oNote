package ochat.onote.backend
import android.util.Log
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.client.result.InsertOneResult
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.Binary
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


/**
 * Clase de conexión a MongoDB en Kotlin usando Flow
 */
class Db {
    init{
        System.setProperty("org.mongodb.dns.disable", "true")
    }
    private val username = URLEncoder.encode("davidmanuelraposeiras", StandardCharsets.UTF_8.toString())
    private val password = URLEncoder.encode("Metalor01", StandardCharsets.UTF_8.toString())
    private val databaseName = "impacthon"

    private val connectionString = "mongodb://davidmanuelraposeiras:Metalor01@impacthon-shard-00-00.vd55b.mongodb.net:27017,impacthon-shard-00-01.vd55b.mongodb.net:27017,impacthon-shard-00-02.vd55b.mongodb.net:27017/?ssl=true&replicaSet=atlas-v7wies-shard-0&authSource=admin&retryWrites=true&w=majority&appName=impacthon"
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(connectionString))
        .applyToSslSettings { it.enabled(true) }
        .build()

    private val mongoClient: MongoClient = MongoClient.create(settings)
    private val database: MongoDatabase = mongoClient.getDatabase(databaseName)

    fun close() {
        mongoClient.close()
    }

    /**
     * Obtiene los nombres de la colección 'subject' como un Flow
     * !! USO CON rememberCouroutineScope() !!
     * Ejemplo: val coroutineScope = rememberCoroutineScope()
     *
     * Button(onClick = {
     *     coroutineScope.launch {
     *         val nombres = db.obtenerNombres()
     *         Log.d("MongoDB", nombres.toString())
     *     }
     * }) {
     *     Text("Obtener Nombres")
     * }
     */
    suspend fun obtenerSubject(): List<Subject> {
        return database.getCollection<Document>("subject")
            .find()
            .toList()
            .map { doc -> Subject(
                name = doc.getString("name"),
                photo = doc.get("photo", Binary::class.java)
            ) }
    }

    suspend fun checkConnection(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("MongoDB", "🔄 Connecting to MongoDB...")
                val command = Document("ping", 1)
                database.runCommand<Document>(command)
                Log.d("MongoDB", "✅ Connection successful!")
                true
            } catch (e: Exception) {
                Log.e("MongoDB", "❌ Connection failed: ${e.message}")
                false
            }
        }
    }

    /*suspend fun actualizarFotoEnSubject(subjectName: String, base64Image: String): Boolean {
        // Decodificar la imagen Base64 a un array de bytes
        val imageBytes = Base64.getDecoder().decode(base64Image)
        val binaryImage = Binary(imageBytes)

        // Buscar el subject por nombre y actualizar su campo "photo"
        val resultado = database.getCollection<org.bson.Document>("subject")
            .updateOne(
                Filters.eq("name", subjectName), // Filtra por el nombre del subject
                Updates.set("photo", binaryImage) // Actualiza el campo "photo"
            )

        return resultado.modifiedCount > 0 // Retorna true si se actualizó algún documento
    }*/

    /**
     * Obtiene los calendar de la colección 'subject' y los convierte
     * json
     * */
    suspend fun obtenerCalendar(subjectName: String): String {
        //devolver el calendar en formato String del calendar de la Subject
        val documento =  database.getCollection<Document>("subject")
            .find(eq("name", subjectName))
            .first()

        return documento.getString("calendar")
    }

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

    suspend fun obtenerClasesParaCalendario(): List<Clase> {
        val clases = database.getCollection<Document>("class")
            .find()
            .toList()
            .map { doc ->
                Clase(
                    id = doc.getObjectId("_id").toHexString(),
                    name = doc.getString("name"),
                    profesor = doc.getString("profesor"),
                    date_start = doc.getDate("date_start"),
                    date_end = doc.getDate("date_end")
                )
            }
        return clases
    }


    suspend fun obtenerDocumentosPorClase(clase: String, asignatura: String): List<MyFile> {
        return database.getCollection<Document>("files")
            .find(
                Filters.and(
                    eq("subject", asignatura), // Filtrar por asignatura
                    Filters.elemMatch("class", eq(clase)) // Filtrar si "class" contiene la clase
                )
            )
            .toList()
            .map { doc ->
                MyFile(
                    id = doc.getObjectId("_id").toHexString(), // Convertir el ObjectId a String
                    data = doc.get("data", Binary::class.java), // Extrae los datos binarios
                    name = doc.getString("name"), // Nombre del archivo
                    type = doc.getString("type"), // Tipo del archivo (ej. "pdf", "png")
                    classes = doc.getList("class", String::class.java) ?: emptyList(), // Lista de clases
                    subject = doc.getString("subject") // Asignatura
                )
            }
    }

    suspend fun obtenerDatosBinariosPorClase(asignatura: String, clase: String): List<Binary> {
        return obtenerDocumentosPorClase(clase, asignatura) // Llamamos a la nueva versión de la función
            .map { it.data } // Extraemos solo los datos binarios de cada archivo
    }

    suspend fun obtenerArchivosPorClase(asignatura: String, clase: String): List<File> {
        val documentos = obtenerDocumentosPorClase(clase, asignatura)

        return documentos.map { file ->
            val archivo =
                File.createTempFile(file.name, ".${file.type}") // Crear archivo temporal
            archivo.writeBytes(file.data.data) // Escribir los datos binarios en el archivo

            archivo // Devolver el archivo creado
        }
    }

    suspend fun insertarArchivo(file: MyFile): InsertOneResult {
        val documento = Document().apply {
            append("name", file.name)
            append("type", file.type)
            append("data", file.data) // El campo "data" ya es Binary en MyFile
            append("class", file.classes)
            append("subject", file.subject)
        }

        return database.getCollection<Document>("files").insertOne(documento)
    }

    suspend fun obtenerDocumentosPorAsignatura(asignatura: String): List<MyFile> {
        return database.getCollection<Document>("files")
            .find(eq("subject", asignatura))
            .toList()
            .map { doc ->
                MyFile(
                    id = doc.getObjectId("_id").toHexString(), // Convertir el ObjectId a Stringº
                    data = doc.get("data", Binary::class.java), // Extrae los datos binarios
                    name = doc.getString("name"), // Nombre del archivo
                    type = doc.getString("type"), // Tipo del archivo (ej. "pdf", "png")
                    classes = doc.getList("class", String::class.java) ?: emptyList(), // Lista de clases
                    subject = doc.getString("subject") // Asignatura
                )
            }
    }

    suspend fun obtenerDatosBinariosPorAsignatura(asignatura: String): List<Binary> {
        return obtenerDocumentosPorAsignatura(asignatura) // Llamamos a la nueva versión de la función
            .map { it.data } // Extraemos solo los datos binarios de cada archivo
    }

    suspend fun obtenerArchivosPorAsignatura(asignatura: String): List<File> {
        val documentos = obtenerDocumentosPorAsignatura(asignatura)

        return documentos.map { file ->
            val archivo =
                File.createTempFile(file.name, ".${file.type}") // Crear archivo temporal
            archivo.writeBytes(file.data.data) // Escribir los datos binarios en el archivo

            archivo // Devolver el archivo creado
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