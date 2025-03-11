import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.*

import ochat.onote.backend.Db
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue


class ExampleUnitTest {

    private val db = Db() // Instancia real de la base de datos

    /**
     * Prueba obtenerNombres()
     */
    @Test
    fun testObtenerNombres() = runBlocking {
        val nombres = db.obtenerNombres()

        // Verifica que la lista no está vacía
        assertTrue("La lista de nombres no debería estar vacía", nombres.isNotEmpty())

        // Imprime los resultados (opcional)
        println("Nombres obtenidos: $nombres")
    }

    /**
     * Prueba obtenerAsignaturasComoMapa()
     */
    @Test
    fun testObtenerAsignaturasComoMapa() = runBlocking {
        val asignaturas = db.obtenerAsignaturasComoMapa()

        // Verifica que el mapa no está vacío
        assertTrue("El mapa de asignaturas no debería estar vacío", asignaturas.isNotEmpty())

        // Imprime los resultados (opcional)
        println("Asignaturas obtenidas: $asignaturas")
    }
}

