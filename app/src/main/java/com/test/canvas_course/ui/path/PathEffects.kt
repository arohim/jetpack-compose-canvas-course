package com.test.canvas_course.ui.path

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


@Composable
fun PathEffects() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10000f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing)
        ),
        label = ""
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(100f, 100f)
            cubicTo(100f, 300f, 600f, 700f, 600f, 1100f)
            lineTo(800f, 800f)
            lineTo(1000f, 1100f)
        }
        val oval = Path().apply {
            addOval(
                Rect(
                    topLeft = Offset.Zero,
                    bottomRight = Offset(40f, 10f)
                )
            )
        }
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 5.dp.toPx(),
//                pathEffect = PathEffect.dashPathEffect(
//                    intervals = floatArrayOf(50f, 30f),
//                    phase = phase
//                )
//                pathEffect = PathEffect.cornerPathEffect(
//                    radius = 1000f
//                )
//                pathEffect = PathEffect.stampedPathEffect(
//                    shape = oval,
//                    advance = 100f,
//                    phase = phase,
//                    style = StampedPathEffectStyle.Morph
//                )
                pathEffect = PathEffect.chainPathEffect(
                    outer = PathEffect.stampedPathEffect(
                        shape = oval,
                        advance = 30f,
                        phase = 0f,
                        style = StampedPathEffectStyle.Rotate
                    ),
                    inner = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(100f, 100f)
                    )
                )
            )
        )
    }
}