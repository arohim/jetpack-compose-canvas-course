package com.test.canvas_course.ui.shape

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.test.canvas_course.ui.theme.CanvascourseTheme

//https://akexorcist.dev/shape-in-jetpack-compose/
class CouponShape(private val cornerRadius: Dp, private val holeSize: Dp) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        val rect = size.toRect()
        val cornerRadiusInPx = cornerRadius.toPx(density)
        val holeRadiusInPx = holeSize.toPx(density) / 2f
        val path = Path().apply {
            // Create path with your logic
            moveTo(cornerRadiusInPx, 0f)
            lineTo(rect.width - cornerRadiusInPx, 0f)
            arcTo(
                Rect(
                    left = rect.width - cornerRadiusInPx,
                    top = 0f,
                    right = rect.width,
                    bottom = cornerRadiusInPx,
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(rect.width, rect.centerRight.y - holeRadiusInPx)
            arcTo(
                Rect(
                    left = rect.centerRight.x - holeRadiusInPx,
                    top = rect.centerRight.y - holeRadiusInPx,
                    right = rect.centerRight.x + holeRadiusInPx,
                    bottom = rect.centerRight.y + holeRadiusInPx,
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            lineTo(rect.width, rect.height - cornerRadiusInPx)
            arcTo(
                Rect(
                    left = rect.width - cornerRadiusInPx,
                    top = rect.height - cornerRadiusInPx,
                    right = rect.width,
                    bottom = rect.height,
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(cornerRadiusInPx, rect.height)
            arcTo(
                Rect(
                    left = 0f,
                    top = rect.height - cornerRadiusInPx,
                    right = cornerRadiusInPx,
                    bottom = rect.height,
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, rect.centerLeft.y + holeRadiusInPx)
            arcTo(
                Rect(
                    left = -holeRadiusInPx,
                    top = rect.centerLeft.y - holeRadiusInPx,
                    right = holeRadiusInPx,
                    bottom = rect.centerLeft.y + holeRadiusInPx,
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            lineTo(0f, cornerRadiusInPx)
            arcTo(
                Rect(
                    left = 0f,
                    top = 0f,
                    right = cornerRadiusInPx,
                    bottom = cornerRadiusInPx,
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            close()
        }
        return Outline.Generic(path)
    }

    private fun Dp.toPx(density: Density) = with(density) { this@toPx.toPx() }
}

@Preview
@Composable
private fun Preview() {
    CanvascourseTheme {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .background(
                    color = Color.White,
                    shape = CouponShape(16.dp, 16.dp)
                )
                .border(
                    width = 0.5.dp,
                    color = Color.Red,
                    shape = CouponShape(16.dp, 16.dp)
                )
        ) { }
    }

}