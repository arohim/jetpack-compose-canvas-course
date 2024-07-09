package com.test.canvas_course.ui.linechart

import android.graphics.PointF
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntRect
import androidx.compose.ui.unit.toSize
import com.test.canvas_course.ui.theme.CanvascourseTheme
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.math.roundToInt

// date + balance
// list of date + balanc
@RequiresApi(Build.VERSION_CODES.O)
val graphData = listOf(
    Balance(LocalDate.now(), BigDecimal(65631)),
    Balance(LocalDate.now().plusWeeks(1), BigDecimal(65931)),
    Balance(LocalDate.now().plusWeeks(2), BigDecimal(65851)),
    Balance(LocalDate.now().plusWeeks(3), BigDecimal(65931)),
    Balance(LocalDate.now().plusWeeks(4), BigDecimal(66484)),
    Balance(LocalDate.now().plusWeeks(5), BigDecimal(67684)),
    Balance(LocalDate.now().plusWeeks(6), BigDecimal(66684)),
    Balance(LocalDate.now().plusWeeks(7), BigDecimal(66984)),
    Balance(LocalDate.now().plusWeeks(8), BigDecimal(70600)),
    Balance(LocalDate.now().plusWeeks(9), BigDecimal(71600)),
    Balance(LocalDate.now().plusWeeks(10), BigDecimal(72600)),
    Balance(LocalDate.now().plusWeeks(11), BigDecimal(72526)),
    Balance(LocalDate.now().plusWeeks(12), BigDecimal(72976)),
    Balance(LocalDate.now().plusWeeks(13), BigDecimal(73589)),
)

data class Balance(val date: LocalDate, val amount: BigDecimal)

val PurpleBackgroundColor = Color(0xff322049)
val BarColor = Color.White.copy(alpha = 0.3f)
val HighlightColor = Color.White.copy(alpha = 0.7f)

@OptIn(ExperimentalTextApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LineGraph(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val textMeasure = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.labelSmall

    val animationProgress = remember {
        Animatable(0f)
    }
    var highlightedWeek by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(graphData) {
        animationProgress.animateTo(1f, tween(3000))
    }
    Box(
        modifier = modifier
            .background(PurpleBackgroundColor)
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .padding(8.dp)
                .aspectRatio(3 / 2f)
                .fillMaxSize()
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            highlightedWeek =
                                (it.x / (size.width / (graphData.size - 1))).roundToInt()
                        },
                        onDragEnd = { highlightedWeek = null },
                        onDragCancel = { highlightedWeek = null },
                        onDrag = { change, _ ->
                            highlightedWeek =
                                (change.position.x / (size.width / (graphData.size - 1))).roundToInt()
                        }
                    )
                }
                .drawWithCache {
                    val path = generateSmoothPath(graphData, size)
                    val fillPath = Path()
                    fillPath.addPath(path)
                    fillPath.relativeLineTo(0f, size.height)
//                    fillPath.lineTo(size.width, size.height)
                    fillPath.lineTo(0f, size.height)
                    fillPath.close()
                    val brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Green.copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    )

                    onDrawBehind {
                        val barWithPx = 1.dp.toPx()
                        drawRect(BarColor, style = Stroke(barWithPx))

                        // draw vertical lines
                        val verticalLines = 4
                        val verticalSize = size.width / (verticalLines + 1)
                        repeat(verticalLines) { i ->
                            val startX = verticalSize * (i + 1)
                            drawLine(
                                BarColor,
                                start = Offset(startX, 0f),
                                end = Offset(startX, size.height),
                                strokeWidth = barWithPx
                            )
                        }

                        // draw horizontal lines
                        val horizontalLines = 3
                        val horizontalSize = size.height / (horizontalLines + 1)
                        repeat(horizontalLines) { i ->
                            val startY = horizontalSize * (i + 1)
                            drawLine(
                                BarColor,
                                start = Offset(0f, startY),
                                end = Offset(size.width, startY),
                                strokeWidth = barWithPx
                            )
                        }

                        // draw path
                        clipRect(right = size.width * animationProgress.value) {
                            drawPath(path, Color.Green, style = Stroke(2.dp.toPx()))
                            drawPath(
                                fillPath,
                                brush = brush,
                                style = Fill
                            )
                        }

                        highlightedWeek?.let {
                            drawHighLight(
                                graphData,
                                it,
                                textMeasure,
                                textStyle
                            )
                        }
                    }
                }
        )
    }
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawHighLight(
    data: List<Balance>,
    highlightedWeek: Int,
    textMeasure: TextMeasurer,
    textStyle: TextStyle
) {
    val amount = data[highlightedWeek].amount
    val minAmount = data.minBy { it.amount }.amount
    val range = data.maxBy { it.amount }.amount - minAmount
    val percentageHeight = ((amount - minAmount).toFloat() / range.toFloat())
    val y = size.height - (size.height * percentageHeight)
    val x = highlightedWeek * (size.width / (data.size - 1))

    drawLine(
        HighlightColor,
        start = Offset(x, 0f),
        end = Offset(x, size.height),
        strokeWidth = 2.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    )

    drawCircle(
        Green,
        radius = 4.dp.toPx(),
        center = Offset(x, y)
    )

    val textMeasureResult = textMeasure.measure("$amount", textStyle)
    val highlightContainerSize = textMeasureResult.size.toIntRect().inflate(4.dp.roundToPx()).size
    val boxTopLeft = (x - highlightContainerSize.width / 2).coerceIn(
        0f,
        size.width - highlightContainerSize.width
    )
    drawRoundRect(
        color = HighlightColor,
        topLeft = Offset(boxTopLeft, 0f),
        size = highlightContainerSize.toSize(),
        cornerRadius = CornerRadius(8.dp.toPx())
    )
    drawText(
        textMeasureResult,
        color = Color.Black,
        topLeft = Offset(boxTopLeft + 4.dp.toPx(), 4.dp.toPx())
    )
}

fun CacheDrawScope.generatePath(data: List<Balance>): Path {
    val path = Path()
    val numEntries = data.size - 1
    val weekWidth = size.width / numEntries

    val min = data.minBy { it.amount }
    val max = data.maxBy { it.amount }
    val range = max.amount - min.amount
    val heightPxPerAmount = size.height / range.toFloat()

    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.amount - min.amount).toFloat() * heightPxPerAmount
            )
        }
        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.amount - min.amount).toFloat() * heightPxPerAmount
        path.lineTo(balanceX, balanceY)
    }
    return path
}

fun generateSmoothPath(data: List<Balance>, size: Size): Path {
    val path = Path()
    val numEntries = data.size - 1
    val weekWidth = size.width / numEntries

    val min = data.minBy { it.amount }
    val max = data.maxBy { it.amount }
    val range = max.amount - min.amount
    val heightPxPerAmount = size.height / range.toFloat()
    var previousBalanceX = 0f
    var previousBalanceY = size.height
    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.amount - min.amount).toFloat() * heightPxPerAmount
            )
        }
        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.amount - min.amount).toFloat() * heightPxPerAmount

        val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
        val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)

        path.cubicTo(
            controlPoint1.x,
            controlPoint1.y,
            controlPoint2.x,
            controlPoint2.y,
            balanceX,
            balanceY
        )

        previousBalanceX = balanceX
        previousBalanceY = balanceY
    }
    return path
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun Preview() {
    CanvascourseTheme {
        LineGraph()
    }
}