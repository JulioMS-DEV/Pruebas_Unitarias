package ni.edu.uam.pruebasunitariastareas.logic

import ni.edu.uam.pruebasunitariastareas.model.EstadoTarea
import ni.edu.uam.pruebasunitariastareas.model.Tarea

class GestorTareas {
    private val tareas = mutableListOf<Tarea>()
    private var proximoId = 1

    fun agregarTarea(titulo: String, descripcion: String = ""): Tarea {
        val nuevaTarea = Tarea(proximoId++, titulo, descripcion)
        tareas.add(nuevaTarea)
        return nuevaTarea
    }

    fun eliminarTarea(id: Int) {
        tareas.removeAll { it.id == id }
    }

    fun marcarComoCompletada(id: Int) {
        val index = tareas.indexOfFirst { it.id == id }
        if (index != -1) {
            tareas[index] = tareas[index].copy(estado = EstadoTarea.COMPLETADA)
        }
    }

    fun marcarComoPendiente(id: Int) {
        val index = tareas.indexOfFirst { it.id == id }
        if (index != -1) {
            tareas[index] = tareas[index].copy(estado = EstadoTarea.PENDIENTE)
        }
    }

    fun obtenerTodasLasTareas(): List<Tarea> {
        return tareas.toList()
    }

    fun obtenerTareasPendientes(): List<Tarea> {
        return tareas.filter { it.estado == EstadoTarea.PENDIENTE }
    }

    fun obtenerTareasCompletadas(): List<Tarea> {
        return tareas.filter { it.estado == EstadoTarea.COMPLETADA }
    }

    fun contarTareasPendientes(): Int {
        return tareas.count { it.estado == EstadoTarea.PENDIENTE }
    }

    fun ordenarTareasAlfabeticamente(): List<Tarea> {
        return tareas.sortedBy { it.titulo }
    }

    fun calcularPorcentajeCompletadas(): Int {
        if (tareas.isEmpty()) return 0
        val completadas = tareas.count { it.estado == EstadoTarea.COMPLETADA }
        return (completadas * 100) / tareas.size
    }
}
