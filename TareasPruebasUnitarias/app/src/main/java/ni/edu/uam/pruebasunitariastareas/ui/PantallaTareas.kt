package ni.edu.uam.pruebasunitariastareas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ni.edu.uam.pruebasunitariastareas.logic.GestorTareas
import ni.edu.uam.pruebasunitariastareas.model.EstadoTarea
import ni.edu.uam.pruebasunitariastareas.model.Tarea

@Composable
fun PantallaTareas(gestorTareas: GestorTareas = remember { GestorTareas() }) {
    var listaTareasActualizada by remember(gestorTareas) { mutableStateOf(gestorTareas.obtenerTodasLasTareas()) }
    var tituloTarea by remember { mutableStateOf("") }
    var filtroActual by remember { mutableStateOf("Todas") }
    var ordenadoAZ by remember { mutableStateOf(false) }
    
    // Estados para los contadores que obligan a la UI a actualizarse
    var totalPendientes by remember(gestorTareas) { mutableIntStateOf(gestorTareas.contarTareasPendientes()) }
    var porcentajeCompletadas by remember(gestorTareas) { mutableIntStateOf(gestorTareas.calcularPorcentajeCompletadas()) }

    fun refrescarLista() {
        var tareasFiltradas = when (filtroActual) {
            "Pendientes" -> gestorTareas.obtenerTareasPendientes()
            "Completadas" -> gestorTareas.obtenerTareasCompletadas()
            else -> gestorTareas.obtenerTodasLasTareas()
        }
        
        if (ordenadoAZ) {
            tareasFiltradas = tareasFiltradas.sortedBy { it.titulo }
        }
        
        listaTareasActualizada = tareasFiltradas
        totalPendientes = gestorTareas.contarTareasPendientes()
        porcentajeCompletadas = gestorTareas.calcularPorcentajeCompletadas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gestión de Tareas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = tituloTarea,
            onValueChange = { tituloTarea = it },
            label = { Text("Título de la tarea") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("campoTitulo")
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (tituloTarea.isNotBlank()) {
                    gestorTareas.agregarTarea(tituloTarea)
                    tituloTarea = ""
                    refrescarLista()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("botonAgregar")
        ) {
            Text("Agregar Tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Pendientes: $totalPendientes",
                modifier = Modifier.testTag("contadorPendientes")
            )
            Text(
                text = "Completadas: $porcentajeCompletadas%",
                modifier = Modifier.testTag("porcentajeCompletadas")
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val filtros = listOf("Todas", "Pendientes", "Completadas")
            filtros.forEach { filtro ->
                Button(
                    onClick = { filtroActual = filtro; refrescarLista() },
                    colors = if (filtroActual == filtro) {
                        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    } else {
                        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                ) {
                    Text(filtro)
                }
            }
        }
        
        Button(
            onClick = { ordenadoAZ = !ordenadoAZ; refrescarLista() },
            modifier = Modifier.padding(top = 8.dp),
            colors = if (ordenadoAZ) {
                ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            } else {
                ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        ) {
            Text(if (ordenadoAZ) "Ordenado A-Z ✓" else "Ordenar A-Z")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("listaTareas")
        ) {
            items(listaTareasActualizada) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onCompletar = {
                        if (tarea.estado == EstadoTarea.PENDIENTE) {
                            gestorTareas.marcarComoCompletada(tarea.id)
                        } else {
                            gestorTareas.marcarComoPendiente(tarea.id)
                        }
                        refrescarLista()
                    },
                    onEliminar = {
                        gestorTareas.eliminarTarea(tarea.id)
                        refrescarLista()
                    }
                )
            }
        }
    }
}

@Composable
fun TareaItem(tarea: Tarea, onCompletar: () -> Unit, onEliminar: () -> Unit) {
    val colorFondo = if (tarea.estado == EstadoTarea.COMPLETADA) {
        androidx.compose.ui.graphics.Color(0xFFC8E6C9) // Verde claro
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tarea.titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = "Estado: ${tarea.estado}", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onCompletar, modifier = Modifier.testTag("botonCompletar_${tarea.id}")) {
                    Text(if (tarea.estado == EstadoTarea.PENDIENTE) "✔" else "↩")
                }
                IconButton(onClick = onEliminar, modifier = Modifier.testTag("botonEliminar_${tarea.id}")) {
                    Text("🗑")
                }
            }
        }
    }
}
