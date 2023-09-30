package com.test.canvas_course.ui.soundwave

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SoundWaveContainer() {
    val amplitudes = List(1_000) {
        (100..800).random()
    }
//    val amplitudes = listOf(100, 50)

    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(top = 100.dp)
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
    var startSelectionOffsetX by remember { mutableStateOf(0f) }
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
    val startCirclePosition = Offset(startSelectionOffsetX, 50.dp.dpToPx())
    val startingOfTheEndOffsetX by remember {
        derivedStateOf { startSelectionOffsetX + selectingWidth }
    }
    val endCirclePosition = Offset(startingOfTheEndOffsetX, 50.dp.dpToPx())
    val circleRadius = 10.dp.dpToPx()
    val selectionBoxWidth = 20.dp.dpToPx()

    Log.d(
        "Touch area startOffX", "startingOfTheEndOffsetX=$startingOfTheEndOffsetX" +
                " startSelectionOffsetX=$startSelectionOffsetX"
    )
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
                    }
                ) { change, dragAmount ->
                    val startOfStartTouchBox = startSelectionOffsetX - selectionBoxWidth
                    val endOfStartTouchBox = startSelectionOffsetX + selectionBoxWidth

                    val endOfEndTouchBox = startingOfTheEndOffsetX + selectionBoxWidth
                    val startOfEndTouchBox = startingOfTheEndOffsetX - selectionBoxWidth

                    // start of the wave
                    val isWithInStartSelection =
                        startDraggingOffset!!.x in startOfStartTouchBox..endOfStartTouchBox
                                && startDraggingOffset!!.x < startOfEndTouchBox + selectionBoxWidth

                    // end of the wave
                    Log.d(
                        "Touch area onDrag",
                        change.position.toString() + " starOfEndTouchBox:$startOfEndTouchBox" +
                                " endOfEndTouchBox:$endOfEndTouchBox"
                    )
                    val isWithInEndSelection =
                        startDraggingOffset!!.x in startOfEndTouchBox..endOfEndTouchBox
                                && startDraggingOffset!!.x <= waveWidth
                                && startDraggingOffset!!.x > endOfStartTouchBox + selectionBoxWidth

                    Log.d(
                        "Touch area onDrag",
                        change.position.toString() + " isWithInStartSelection:$isWithInStartSelection" +
                                " isWithInEndSelection:$isWithInEndSelection"
                    )

                    val isWithInTheBox =
                        startDraggingOffset!!.x in startSelectionOffsetX..startingOfTheEndOffsetX


                    if (isWithInEndSelection) {
                        val changeAmountX = change.position.x - startSelectionOffsetX
                        selectingWidth = if (changeAmountX <= waveWidth) {
                            if (change.position.x > endOfStartTouchBox)
                                changeAmountX
                            else
                                changeAmountX + selectionBoxWidth
                        } else {
                            waveWidth
                        }
                        startDraggingOffset = change.position
                    } else if (isWithInStartSelection) {
                        val newStartSelectionOffsetX = if (change.position.x >= 0f) {
                            if (change.position.x < startOfEndTouchBox)
                                change.position.x
                            else
                                startOfEndTouchBox - selectionBoxWidth
                        } else {
                            0f
                        }
                        val moveAmountX = startSelectionOffsetX - newStartSelectionOffsetX
                        startSelectionOffsetX = newStartSelectionOffsetX
                        selectingWidth += moveAmountX
                        startDraggingOffset = change.position
                    } else if (isWithInTheBox) {
                        val newStartSelectionOffsetX = startSelectionOffsetX + dragAmount.x
                        startSelectionOffsetX = if (newStartSelectionOffsetX <= 0f)
                            0f
                        else if (newStartSelectionOffsetX + selectingWidth >= waveWidth)
                            startSelectionOffsetX
                        else
                            newStartSelectionOffsetX
                        startDraggingOffset = change.position
                    }
                }
            },
        onDraw = {
            drawAmp(
                amplitudes,
                space,
                width,
                startSelectionOffsetX,
                startingOfTheEndOffsetX,
                barColor
            )
            // background
            drawRect(
                color = bgColor.copy(alpha = 0.2f),
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height),
            )
            // selecting background
            drawRoundRect(
                color = bgColor,
                topLeft = Offset(startSelectionOffsetX, 0f),
                size = Size(selectingWidth, size.height),
                cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
            )
            // selecting path
            selectingPath(
                startSelectionOffsetX,
                cornerRadiusPx,
                startingOfTheEndOffsetX,
                lineColor,
                strokeWidth,
                circleRadius,
                startCirclePosition,
                endCirclePosition
            )
        }
    )
}

private fun DrawScope.selectingPath(
    startSelectionOffsetX: Float,
    cornerRadiusPx: Float,
    startingOfTheEndOffsetX: Float,
    lineColor: Color,
    strokeWidth: Float,
    circleRadius: Float,
    startCirclePosition: Offset,
    endCirclePosition: Offset
) {
    val indicator = Path().apply {
        moveTo(startSelectionOffsetX, cornerRadiusPx)
        cubicTo(
            startSelectionOffsetX, cornerRadiusPx,
            startSelectionOffsetX, 0f,
            startSelectionOffsetX + cornerRadiusPx, 0f
        )
        lineTo(startingOfTheEndOffsetX - cornerRadiusPx, 0f)
        cubicTo(
            startingOfTheEndOffsetX - cornerRadiusPx, 0f,
            startingOfTheEndOffsetX, 0f,
            startingOfTheEndOffsetX, cornerRadiusPx
        )
        lineTo(startingOfTheEndOffsetX, size.height - cornerRadiusPx)
        cubicTo(
            startingOfTheEndOffsetX, size.height - cornerRadiusPx,
            startingOfTheEndOffsetX, size.height,
            startingOfTheEndOffsetX - cornerRadiusPx, size.height
        )
        lineTo(startSelectionOffsetX + cornerRadiusPx, size.height)
        cubicTo(
            startSelectionOffsetX + cornerRadiusPx, size.height,
            startSelectionOffsetX, size.height,
            startSelectionOffsetX, size.height - cornerRadiusPx
        )
        close()
    }
    drawPath(
        path = indicator,
        color = lineColor,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Butt
        )
    )
    drawCircle(
        color = lineColor,
        radius = circleRadius,
        center = startCirclePosition
    )
    drawCircle(
        color = lineColor,
        radius = circleRadius,
        center = endCirclePosition
    )
}

fun DrawScope.drawAmp(
    amplitudes: List<Int>,
    space: Float,
    width: Float,
    startSelectionOffsetX: Float,
    startingOfTheEndOffsetX: Float,
    barColor: Color
) {
    amplitudes.forEachIndexed { i, amp ->
        val normalized = (amp / 4).coerceAtMost(size.height.toInt()).toFloat()
        val halfAmp = normalized / 2f
        val halfH = size.height / 2
        val topLeft = i.toFloat() * (space + width)
        val color = if (topLeft in startSelectionOffsetX..startingOfTheEndOffsetX) {
            barColor
        } else
            barColor.copy(alpha = 0.2f)
        drawRect(
            color = color,
            topLeft = Offset(topLeft, halfH - halfAmp),
            size = Size(width, normalized),
            style = Stroke(
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview
@Composable
fun PreviewSoundWave() {
    SoundWaveContainer()
}