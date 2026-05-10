package carvalho.zanini.ponderada1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LancadorDeDadosApp()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LancadorDeDadosApp() {
    var dadoSelecionado by remember { mutableStateOf("D6") }
    var resultado by remember { mutableStateOf<Int?>(null) }

    val dados = listOf("D6", "D10", "D20", "D100")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Lançador de Dados",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Escolha o tipo de dado:")

        dados.forEach { dado ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = dadoSelecionado == dado,
                    onClick = {
                        dadoSelecionado = dado
                        resultado = null
                    }
                )
                Text(text = dado)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val valorSorteado = when (dadoSelecionado) {
                    "D6" -> Random.nextInt(1, 7)
                    "D10" -> Random.nextInt(1, 11)
                    "D20" -> Random.nextInt(1, 21)
                    "D100" -> Random.nextInt(1, 101)
                    else -> 0
                }


                resultado = valorSorteado
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Lançar dado")
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedContent(
            targetState = resultado,
            transitionSpec = {
                (scaleIn(initialScale = 0.55f) + fadeIn()) togetherWith
                        (scaleOut(targetScale = 0.55f) + fadeOut())
            },
            label = "dice_result"
        ) { state ->
            if (state != null) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    DiceFace(diceType = dadoSelecionado, result = state)
                }
            } else {
                Text(text = "Clique no botão para lançar o dado", fontSize = 16.sp)
            }
        }
    }
}