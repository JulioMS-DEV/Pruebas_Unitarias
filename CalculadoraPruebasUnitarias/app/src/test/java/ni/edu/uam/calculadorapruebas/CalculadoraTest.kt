package ni.edu.uam.calculadorapruebas

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculadoraTest {

    private val calculadora = Calculadora()

    @Test
    fun verificarSuma() {
        val resultado = calculadora.sumar(5, 3)
        assertEquals(8, resultado)
    }

    // Actividades Complementarias
    @Test
    fun verificarResta() {
        val resultado = calculadora.restar(10, 4)
        assertEquals(6, resultado)
    }

    @Test
    fun verificarMultiplicacion() {
        val resultado = calculadora.multiplicar(5, 3)
        assertEquals(15, resultado)
    }

    @Test
    fun verificarDivision() {
        val resultado = calculadora.dividir(10, 2)
        assertEquals(5, resultado)
    }

    @Test
    fun verificarNumerosNegativos() {
        val resultado = calculadora.sumar(-5, -3)
        assertEquals(-8, resultado)
        
        val resultadoResta = calculadora.restar(-5, -3)
        assertEquals(-2, resultadoResta)
    }

    @Test
    fun pruebaQueFallaIntencionalmente() {
        // Esta prueba fallará porque 5 + 3 es 8, no 10.
        // La causa del error es que el valor esperado (10) no coincide con el valor real (8).
        val resultado = calculadora.sumar(5, 3)
        assertEquals("La suma de 5 y 3 debería ser 8", 10, resultado)
    }
}
