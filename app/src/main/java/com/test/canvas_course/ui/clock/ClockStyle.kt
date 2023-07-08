package com.test.canvas_course.ui.clock

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClockStyle(
    val radius: Dp = 200.dp,
    val fiveTextSize: TextUnit = 16.sp,
    val fiveStepLineColor: Color = Color.DarkGray,
    val fiveStrokeWidth: Dp = 2.dp,
    val fiveStepLineLength: Dp = 25.dp,
    val hoursLineColor: Color = Color.Black,
    val hoursLineLength: Dp = 100.dp,
    val hoursLineStrokeWidth: Dp = 6.dp,
    val normalLineColor: Color = Color.LightGray,
    val normalLineLength: Dp = 15.dp,
    val secondsLineColor: Color = Color.Red,
    val secondsLineLength: Dp = 30.dp,
    val secondsLineStrokeWidth: Dp = 2.dp,
    val normalStrokeWidth: Dp = 1.5.dp,
    val minutesLineColor: Color = Color.Black,
    val minutesLineLength: Dp = 40.dp,
    val minutesLineStrokeWidth: Dp = 4.dp,
)