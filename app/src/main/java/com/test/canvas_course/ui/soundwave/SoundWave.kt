package com.test.canvas_course.ui.soundwave

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.lang.Exception
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun SoundWaveContainer() {
    val amplitudes = List(1_000) {
        (100..800).random()
    }
//    val amplitudes = listOf(100, 50)

    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(top = 10.dp, start = 24.dp, end = 6.dp)
            .fillMaxSize()
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
    var selectingWidth by remember { mutableStateOf(waveWidth - 500f) }
//    val selectingBarCount = try {
//        selectingWidth / (space + width)
//    } catch (e: Exception) {
//        10
//    }
//    val selectedAmps = amplitudes.take(selectingBarCount.toInt())

    val strokeWidth = 3.dp.dpToPx()
    val bgColor = barColor.copy(alpha = 0.35f)
    val cornerRadius = 8.dp
    val cornerRadiusPx = cornerRadius.dpToPx()

    var startDraggingOffset: Offset? = null
    val circlePosition = Offset(selectingWidth, 50.dp.dpToPx())
    val circleRadius = 10.dp.dpToPx()
    Log.d("Touch area", "circle=$circlePosition radius:$circleRadius")
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        startDraggingOffset = it
                        Log.d(
                            "Touch area onDragStart",
                            "$startDraggingOffset"
                        )
                    }) { change, dragAmount ->
                    // end of the wave
                    val isWithInTouchArea =
                        startDraggingOffset!!.x <= selectingWidth + 40.dp.toPx() &&
                                startDraggingOffset!!.x >= selectingWidth - 40.dp.toPx() &&
                                startDraggingOffset!!.x <= size.width
                    // the circle
//                    val sqrt = sqrt(
//                        (startDraggingOffset!!.x - circlePosition.x).pow(2) +
//                                (startDraggingOffset!!.y - circlePosition.y).pow(2)
//                    )
//                    val isWithInTouchArea = sqrt - 10.dp.toPx() <= circleRadius
//                    Log.d(
//                        "Touch area onDrag",
//                        change.position.toString() + " sqrt:$sqrt isWithInTouchArea:$isWithInTouchArea"
//                    )
                    if (isWithInTouchArea) {
                        startDraggingOffset = change.position
                        selectingWidth = if (change.position.x <= waveWidth) {
                            change.position.x
                        } else {
                            waveWidth
                        }
                    }
                }
            },
        onDraw = {
            amplitudes.forEachIndexed { i, amp ->
                val normalized = (amp / 4).coerceAtMost(size.height.toInt()).toFloat()
                val halfAmp = normalized / 2f
                val halfH = size.height / 2
                val topLeft = i.toFloat() * (space + width)
                val color = if (topLeft < selectingWidth)
                    barColor
                else
                    Color.DarkGray
                drawRect(
                    color = color,
                    topLeft = Offset(topLeft, halfH - halfAmp),
                    size = Size(width, normalized),
                    style = Stroke(
                        cap = StrokeCap.Round
                    )
                )
            }
            val indicator = Path().apply {
                moveTo(cornerRadiusPx, 0f)
                lineTo(selectingWidth - cornerRadiusPx, 0f)
                cubicTo(
                    selectingWidth - cornerRadiusPx, 0f,
                    selectingWidth, 0f,
                    selectingWidth, cornerRadiusPx
                )
                lineTo(selectingWidth, size.height - cornerRadiusPx)
                cubicTo(
                    selectingWidth, size.height - cornerRadiusPx,
                    selectingWidth, size.height,
                    selectingWidth - cornerRadiusPx, size.height
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
                size = Size(selectingWidth, size.height),
                cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
            )
            drawRect(
                color = bgColor.copy(alpha = 0.2f),
                topLeft = Offset(selectingWidth, 0f),
                size = Size(waveWidth - selectingWidth, size.height),
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
                radius = circleRadius,
                center = circlePosition
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