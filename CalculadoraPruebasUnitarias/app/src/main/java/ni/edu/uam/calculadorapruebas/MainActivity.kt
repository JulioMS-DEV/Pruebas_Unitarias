package ni.edu.uam.calculadorapruebas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import ni.edu.uam.calculadorapruebas.ui.theme.CalculadoraPruebasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraPruebasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaCalculadora(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaCalculadora(modifier: Modifier = Modifier) {

    val calculadora = Calculadora()
    var resultado by remember { mutableStateOf("") }

    Column(modifier = modifier) {

        Button(
            onClick = {
                resultado = calculadora.sumar(5, 3).toString()
            }
        ) {
            Text("Calcular Suma")
        }

        Button(
            onClick = {
                resultado = calculadora.restar(10, 4).toString()
            }
        ) {
            Text("Calcular Resta")
        }

        Text(
            text = resultado,
            modifier = Modifier.testTag("resultado")
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaCalculadoraPreview() {
    CalculadoraPruebasTheme {
        PantallaCalculadora()
    }
}
