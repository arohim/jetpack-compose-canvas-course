package com.test.canvas_course.ui.clock

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.withRotation
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun MyClock() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val milliSeconds = remember {
            System.currentTimeMillis()
        }
        var seconds by remember {
            mutableStateOf((milliSeconds / 1000) % 60f)
        }
        var minutes by remember {
            mutableStateOf(((milliSeconds / 1000) / 60f) % 60f)
        }
        var hours by remember {
            mutableStateOf(((milliSeconds / 1000) / 3600f) % 60f)
        }
        LaunchedEffect(key1 = seconds) {
            delay(1000L)
            minutes += 1f / 60f
            hours += 1f / (60f * 12f)
            seconds += 1f
        }
        Clock(
            seconds = seconds,
            minutes = minutes,
            hours = hours
        )
    }
}

@Composable
fun Clock(
    modifier: Modifier = Modifier,
    style: ClockStyle = ClockStyle(minutesLineLength = 50.dp, minutesLineStrokeWidth = 4.dp),
    seconds: Float,
    minutes: Float,
    hours: Float,
) {
    Canvas(modifier = modifier.size(style.radius * 2f)) {
        drawContext.canvas.nativeCanvas.apply {
            withRotation(
                degrees = -90f,
                pivotY = center.y,
                pivotX = center.x,
            ) {
                drawCircle(
                    center.x,
                    center.y,
                    style.radius.toPx(),
                    Paint().apply {
                        color = Color.White.toArgb()
                        setShadowLayer(
                            50f,
                            0f,
                            0f,
                            android.graphics.Color.argb(50, 0, 0, 0)
                        )
                    }
                )
            }
        }
        drawLine(
            color = Color.Green,
            start = Offset.Zero,
            end = Offset(0f, 100f),
            strokeWidth = 1.dp.toPx()
        )

        (0..59).forEach { i ->
            val angleInRad = i * (360f / 60f) * (PI.toFloat() / 180f)
            val isMod5 = i % 5 == 0
            val lineLength = if (isMod5)
                style.fiveStepLineLength.toPx()
            else
                style.normalLineLength.toPx()
            val lineColor = if (isMod5)
                style.fiveStepLineColor
            else
                style.normalLineColor
            val strokeWidth = if (isMod5)
                style.fiveStrokeWidth.toPx()
            else
                style.normalStrokeWidth.toPx()

            val lineStart = Offset(
                x = style.radius.toPx() * cos(angleInRad) + center.x,
                y = style.radius.toPx() * sin(angleInRad) + center.y
            )

            val lineEnd = Offset(
                x = (style.radius.toPx() - lineLength) * cos(angleInRad) + center.x,
                y = (style.radius.toPx() - lineLength) * sin(angleInRad) + center.y
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = strokeWidth
            )

            // Draw Number
            if (isMod5) {
                drawContext.canvas.nativeCanvas.apply {
                    val textX =
                        (style.radius.toPx() - lineLength - style.fiveTextSize.toPx() - 2.dp.toPx()) * cos(
                            angleInRad
                        ) + center.x
                    val textY =
                        (style.radius.toPx() - lineLength - style.fiveTextSize.toPx() - 2.dp.toPx()) * sin(
                            angleInRad
                        ) + center.y
                    drawText(
                        (i / 5).toString(),
                        textX,
                        textY,
                        Paint().apply {
                            textSize = 16.sp.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }

        rotate(degrees = seconds * (360f / 60f)) {
            drawLine(
                color = style.secondsLineColor,
                start = center,
                end = center.copy(y = style.secondsLineLength.toPx()),
                strokeWidth = style.secondsLineStrokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }

        rotate(degrees = minutes * (360f / 60f)) {
            drawLine(
                color = style.minutesLineColor,
                start = center,
                end = center.copy(y = style.minutesLineLength.toPx()),
                strokeWidth = style.minutesLineStrokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }

        rotate(degrees = hours * (360f / 60f)) {
            drawLine(
                color = style.hoursLineColor,
                start = center,
                end = center.copy(y = style.hoursLineLength.toPx()),
                strokeWidth = style.hoursLineStrokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}