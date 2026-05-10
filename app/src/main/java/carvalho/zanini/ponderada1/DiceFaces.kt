package carvalho.zanini.ponderada1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val DICE_SIZE = 120.dp
private val SHADOW_ELEVATION = 8.dp

private val triangleShape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density) =
        Outline.Generic(Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        })
}

// Pipa: ponto mais largo a 35% da altura, triângulo superior menor e inferior mais longo.
private val kiteShape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density) =
        Outline.Generic(Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height * 0.35f)
            lineTo(size.width / 2f, size.height)
            lineTo(0f, size.height * 0.35f)
            close()
        })
}

// Polígono de 36 vértices com raio variando ±4% para simular esfera não perfeita.
private val granularCircleShape = object : Shape {
    private val radiusFactors = floatArrayOf(
        1.00f, 0.97f, 1.03f, 0.96f, 1.02f, 0.98f, 1.04f, 0.97f, 1.00f, 0.99f,
        1.03f, 0.96f, 1.01f, 0.98f, 1.04f, 0.97f, 1.00f, 0.99f, 1.02f, 0.96f,
        1.03f, 0.98f, 1.00f, 0.97f, 1.02f, 0.99f, 1.04f, 0.96f, 1.00f, 0.98f,
        1.03f, 0.97f, 1.01f, 0.99f, 1.04f, 0.96f
    )

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density) =
        Outline.Generic(Path().apply {
            val cx = size.width / 2f
            val cy = size.height / 2f
            val baseRadius = size.width * 0.44f
            val n = radiusFactors.size
            for (i in 0 until n) {
                val angle = 2f * PI.toFloat() * i / n
                val r = baseRadius * radiusFactors[i]
                val x = cx + r * cos(angle)
                val y = cy + r * sin(angle)
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        })
}

@Composable
fun D6Face(result: Int) {
    Box(
        modifier = Modifier
            .size(DICE_SIZE)
            .shadow(SHADOW_ELEVATION)
            .background(Color(0xFF5C6BC0)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = result.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
    }
}

@Composable
fun D10Face(result: Int) {
    Box(
        modifier = Modifier
            .size(DICE_SIZE)
            .shadow(SHADOW_ELEVATION, kiteShape)
            .clip(kiteShape)
            .background(Color(0xFF26A69A)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = result.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
    }
}

@Composable
fun D20Face(result: Int) {
    Box(
        modifier = Modifier
            .size(DICE_SIZE)
            .shadow(SHADOW_ELEVATION, triangleShape)
            .clip(triangleShape)
            .background(Color(0xFFEF5350)),
        contentAlignment = Alignment.Center
    ) {
        // Centróide do triângulo está a 2/3 da altura, compensado com offset.
        Text(
            text = result.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.offset(y = 20.dp)
        )
    }
}

@Composable
fun D100Face(result: Int) {
    Box(
        modifier = Modifier
            .size(DICE_SIZE)
            .shadow(SHADOW_ELEVATION, CircleShape)
            .clip(granularCircleShape)
            .background(Color(0xFFAB47BC)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = result.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = if (result == 100) 22.sp else 28.sp
        )
    }
}

@Composable
fun DiceFace(diceType: String, result: Int) {
    when (diceType) {
        "D6" -> D6Face(result)
        "D10" -> D10Face(result)
        "D20" -> D20Face(result)
        "D100" -> D100Face(result)
    }
}
