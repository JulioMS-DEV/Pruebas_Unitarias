package ni.edu.uam.calculadorapruebas

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class PantallaCalculadoraTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verificarBotonSumaYResultado() {
        composeTestRule.setContent {
            PantallaCalculadora()
        }

        composeTestRule
            .onNodeWithText("Calcular Suma")
            .performClick()

        composeTestRule
            .onNodeWithTag("resultado")
            .assertTextEquals("8")
    }

    @Test
    fun verificarBotonRestaYResultado() {
        composeTestRule.setContent {
            PantallaCalculadora()
        }

        composeTestRule
            .onNodeWithText("Calcular Resta")
            .performClick()

        composeTestRule
            .onNodeWithTag("resultado")
            .assertTextEquals("6")
    }
}
