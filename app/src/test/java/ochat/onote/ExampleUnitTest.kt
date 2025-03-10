package ochat.onote

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import ochat.onote.backend.Db
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue



/**
 * Clase de pruebas unitarias para la base de datos usando Flow
 */
class ExampleUnitTest {
    private lateinit var db: Db

    @Before
    fun setup() {
        db = Db() // Inicializa la conexión antes de cada prueba
    }

    @After
    fun tearDown() {
        db.close() // Cierra la conexión después de cada prueba
    }

    /**
     * Prueba para verificar que `obtenerDocumentos()` devuelve documentos de MongoDB
     */
    @Test
    fun testObtenerDocumentos() = runTest {
        val documentos = db.obtenerDocumentos().toList()
        assertTrue("La colección no debería estar vacía", documentos.isNotEmpty())
        documentos.forEach { doc ->
            println("Documento obtenido: ${doc.toJson()}")
            assertNotNull("Cada documento debe tener un _id",doc["_id"])
        }
    }

    /**
     * Prueba para verificar que `actualizarCodigos()` funciona correctamente
     */
    @Test
    fun testActualizarCodigos() = runTest {
        db.actualizarCodigos() // Llama a la función suspendida

        // Verifica que los códigos han sido actualizados
        val documentos = db.obtenerDocumentos().toList()
        documentos.forEach { doc ->
            assertNotNull("Cada documento debe tener un campo 'code' actualizado", doc["code"])
            println("Código actualizado para ${doc["name"]}: ${doc["code"]}")
        }
    }
}
