package com.test.canvas_course.ui.path

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp

@Composable
fun PathTransformationAndClipping() {
    Canvas(modifier = Modifier.fillMaxSize()) {
//        rotate(45f, pivot = Offset(200f, 200f)) {
//        translate(left = 300f, top = 300f) {
//            rotate(45f, pivot = Offset(100f, 100f)) {
//        scale(0.5f, pivot = Offset(200f, 200f)) {
        val circle = Path().apply {
            addOval(Rect(center = Offset(400f, 400f), radius = 300f))
        }
        drawPath(
            path = circle,
            color = Color.Black,
            style = Stroke(width = 5.dp.toPx())
        )
        clipPath(
            path = circle,
        ) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(400f, 400f),
                size = Size(400f, 400f)
            )
        }
    }
}