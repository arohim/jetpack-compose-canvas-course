package com.test.canvas_course.ui

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp

@Composable
private fun DrawTextOnCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Text Text",
                50f,
                200f,
                Paint().apply {
                    color = Color.RED
                    textSize = 100.sp.toPx()
                }
            )
        }
    }
}