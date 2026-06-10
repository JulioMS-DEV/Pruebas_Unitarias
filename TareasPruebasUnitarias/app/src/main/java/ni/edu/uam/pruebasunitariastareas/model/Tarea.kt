package ni.edu.uam.pruebasunitariastareas.model

enum class EstadoTarea {
    PENDIENTE,
    COMPLETADA
}

data class Tarea(
    val id: Int,
    val titulo: String,
    val descripcion: String = "",
    val estado: EstadoTarea = EstadoTarea.PENDIENTE
)
