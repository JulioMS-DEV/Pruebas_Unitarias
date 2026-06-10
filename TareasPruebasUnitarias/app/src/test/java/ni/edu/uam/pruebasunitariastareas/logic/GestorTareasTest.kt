package ni.edu.uam.pruebasunitariastareas.logic

import ni.edu.uam.pruebasunitariastareas.model.EstadoTarea
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class GestorTareasTest {

    private lateinit var gestor: GestorTareas

    @Before
    fun setup() {
        gestor = GestorTareas()
    }

    @Test
    fun agregarTareaIncrementaListaEnUno() {
        val inicial = gestor.obtenerTodasLasTareas().size
        gestor.agregarTarea("Tarea 1")
        assertEquals(inicial + 1, gestor.obtenerTodasLasTareas().size)
    }

    @Test
    fun eliminarTareaDesapareceDeLaLista() {
        val tarea = gestor.agregarTarea("Tarea a eliminar")
        gestor.eliminarTarea(tarea.id)
        assertFalse(gestor.obtenerTodasLasTareas().contains(tarea))
    }

    @Test
    fun completarTareaCambiaEstadoACompletada() {
        val tarea = gestor.agregarTarea("Tarea a completar")
        gestor.marcarComoCompletada(tarea.id)
        val tareaActualizada = gestor.obtenerTodasLasTareas().find { it.id == tarea.id }
        assertEquals(EstadoTarea.COMPLETADA, tareaActualizada?.estado)
    }

    @Test
    fun contarTareasPendientesRetornaValorCorrecto() {
        gestor.agregarTarea("Tarea 1")
        gestor.agregarTarea("Tarea 2")
        val tarea3 = gestor.agregarTarea("Tarea 3")
        gestor.marcarComoCompletada(tarea3.id)
        assertEquals(2, gestor.contarTareasPendientes())
    }

    @Test
    fun listaVaciaRetornaCeroPendientes() {
        assertEquals(0, gestor.contarTareasPendientes())
    }

    @Test
    fun obtenerTareasPendientesSoloRetornaPendientes() {
        gestor.agregarTarea("Pendiente")
        val completada = gestor.agregarTarea("Completada")
        gestor.marcarComoCompletada(completada.id)
        
        val pendientes = gestor.obtenerTareasPendientes()
        assertTrue(pendientes.all { it.estado == EstadoTarea.PENDIENTE })
        assertEquals(1, pendientes.size)
    }

    @Test
    fun ordenarTareasAlfabeticamenteFuncionaCorrectamente() {
        gestor.agregarTarea("C")
        gestor.agregarTarea("A")
        gestor.agregarTarea("B")
        
        val ordenadas = gestor.ordenarTareasAlfabeticamente()
        assertEquals("A", ordenadas[0].titulo)
        assertEquals("B", ordenadas[1].titulo)
        assertEquals("C", ordenadas[2].titulo)
    }

    @Test
    fun calcularPorcentajeCompletadasRetornaValorCorrecto() {
        gestor.agregarTarea("T1")
        val t2 = gestor.agregarTarea("T2")
        gestor.marcarComoCompletada(t2.id)
        
        assertEquals(50, gestor.calcularPorcentajeCompletadas())
    }

    //@Ignore("Prueba fallida intencionalmente para fines demostrativos")
    @Test
    fun pruebaFallidaIntencionalmente() {
        gestor.agregarTarea("Nueva Tarea")
        // Debería ser 1, pero esperamos 0 para que falle
        assertEquals(0, gestor.contarTareasPendientes())
    }
}
