package ni.edu.uam.pruebasunitariastareas.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import ni.edu.uam.pruebasunitariastareas.logic.GestorTareas
import ni.edu.uam.pruebasunitariastareas.ui.theme.PruebasUnitariasTareasTheme
import org.junit.Rule
import org.junit.Test

class PantallaTareasTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun campoDeEntradaAceptaTextoCorrectamente() {
        val gestorTest = GestorTareas()
        composeTestRule.setContent {
            PruebasUnitariasTareasTheme {
                PantallaTareas(gestorTest)
            }
        }

        composeTestRule.onNodeWithTag("campoTitulo")
            .performTextInput("Comprar leche")
        
        composeTestRule.onNodeWithTag("campoTitulo")
            .assertTextContains("Comprar leche")
    }

    @Test
    fun agregarTareaApareceEnPantalla() {
        val gestorTest = GestorTareas()
        composeTestRule.setContent {
            PruebasUnitariasTareasTheme {
                PantallaTareas(gestorTest)
            }
        }

        composeTestRule.onNodeWithTag("campoTitulo")
            .performTextInput("Nueva Tarea")
        
        composeTestRule.onNodeWithTag("botonAgregar")
            .performClick()

        composeTestRule.onNodeWithText("Nueva Tarea")
            .assertIsDisplayed()
    }

    @Test
    fun botonAgregarRespondeAlClickCorrectamente() {
        val gestorTest = GestorTareas()
        composeTestRule.setContent {
            PruebasUnitariasTareasTheme {
                PantallaTareas(gestorTest)
            }
        }

        composeTestRule.onNodeWithTag("campoTitulo")
            .performTextInput("Tarea")
        
        composeTestRule.onNodeWithTag("botonAgregar")
            .assertHasClickAction()
            .performClick()
            
        // El campo debería limpiarse después de agregar
        composeTestRule.onNodeWithTag("campoTitulo")
            .assertTextContains("")
    }

    @Test
    fun eliminarTareaDesapareceDeLaLista() {
        val gestorTest = GestorTareas()
        composeTestRule.setContent {
            PruebasUnitariasTareasTheme {
                PantallaTareas(gestorTest)
            }
        }

        // Agregar tarea
        composeTestRule.onNodeWithTag("campoTitulo").performTextInput("Eliminarme")
        composeTestRule.onNodeWithTag("botonAgregar").performClick()
        
        // Verificar que existe
        composeTestRule.onNodeWithText("Eliminarme").assertIsDisplayed()

        // Eliminarla. Como el testTag del botón eliminar es dinámico con el ID, 
        // y el primer ID es 1, usamos botonEliminar_1
        composeTestRule.onNodeWithTag("botonEliminar_1").performClick()

        // Verificar que ya no está
        composeTestRule.onNodeWithText("Eliminarme").assertDoesNotExist()
    }

    @Test
    fun mostrarPendientesCantidadCorrecta() {
        val gestorTest = GestorTareas()

        composeTestRule.setContent {
            PruebasUnitariasTareasTheme {
                PantallaTareas(gestorTest)
            }
        }

        composeTestRule.waitForIdle()

        // Inicialmente debe mostrar cero tareas pendientes
        composeTestRule
            .onNodeWithTag("contadorPendientes")
            .assertTextContains("Pendientes: 0")

        // Agregar primera tarea
        composeTestRule
            .onNodeWithTag("campoTitulo")
            .performTextInput("T1")

        composeTestRule
            .onNodeWithTag("botonAgregar")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("contadorPendientes")
            .assertTextContains("Pendientes: 1")

        // Agregar segunda tarea
        composeTestRule
            .onNodeWithTag("campoTitulo")
            .performTextClearance()

        composeTestRule
            .onNodeWithTag("campoTitulo")
            .performTextInput("T2")

        composeTestRule
            .onNodeWithTag("botonAgregar")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("contadorPendientes")
            .assertTextContains("Pendientes: 2")

        // Completar la primera tarea
        composeTestRule
            .onNodeWithTag("botonCompletar_1")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("contadorPendientes")
            .assertTextContains("Pendientes: 1")
    }
}
