package com.test.canvas_course.ui.soundwave

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SoundWaveContainer() {
    val amplitudes = List(500) {
        (100..800).random()
    }
//    val amplitudes = listOf(100, 50)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        SoundWave(amplitudes)
    }

}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun SoundWave(amplitudes: List<Int>) {
    val barColor = Color.Green
    val lineColor = Color.White
    val width = 0.5f
    val space = 0.5f
    val waveWidth = amplitudes.size * (space + width)
    val strokeWidth = 2.dp.dpToPx()
    val bgColor = barColor.copy(alpha = 0.35f)
    val cornerRadius = 5.dp
    val cornerRadiusPx = cornerRadius.dpToPx()
    Canvas(
        modifier = Modifier
            .padding(top = 10.dp, start = 6.dp, end = 6.dp)
            .fillMaxWidth()
            .height(100.dp),
        onDraw = {
            amplitudes.forEachIndexed { i, amp ->
                val normalized = (amp / 4).coerceAtMost(size.height.toInt()).toFloat()
                val halfAmp = normalized / 2f
                val halfH = size.height / 2
                drawRect(
                    color = barColor,
                    topLeft = Offset(i.toFloat() * (space + width), halfH - halfAmp),
                    size = Size(width, normalized),
                    style = Stroke(
                        cap = StrokeCap.Round
                    )
                )
            }
            val indicator = Path().apply {
                moveTo(cornerRadiusPx, 0f)
                lineTo(waveWidth - cornerRadiusPx, 0f)
                cubicTo(
                    waveWidth - cornerRadiusPx, 0f,
                    waveWidth, 0f,
                    waveWidth, cornerRadiusPx
                )
                lineTo(waveWidth, size.height - cornerRadiusPx)
                cubicTo(
                    waveWidth, size.height - cornerRadiusPx,
                    waveWidth, size.height,
                    waveWidth - cornerRadiusPx, size.height
                )
                lineTo(cornerRadiusPx, size.height)
                cubicTo(
                    cornerRadiusPx, size.height,
                    0f, size.height,
                    0f, size.height - cornerRadiusPx
                )
                lineTo(0f, cornerRadiusPx)
                cubicTo(
                    0f, cornerRadiusPx,
                    0f, 0f,
                    cornerRadiusPx, 0f
                )
            }
            drawRoundRect(
                color = bgColor,
                topLeft = Offset(0f, 0f),
                size = Size(waveWidth, size.height),
                cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
            )
            drawPath(
                path = indicator,
                color = lineColor,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Bevel
                )
            )
            drawCircle(
                color = lineColor,
                5.dp.toPx(),
                center = Offset(waveWidth, size.height / 2f)
            )
//            drawLine(
//                color = Color.Red,
//                start = Offset(size.width, 0f),
//                end = Offset(size.width, size.height),
//                strokeWidth = 5.dp.toPx(),
//                cap = StrokeCap.Round,
//            )
        }
    )
}

@Preview
@Composable
fun PreviewSoundWave() {
    SoundWaveContainer()
}