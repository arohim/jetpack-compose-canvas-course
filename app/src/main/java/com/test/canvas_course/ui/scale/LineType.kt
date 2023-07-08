package com.test.canvas_course.ui.scale

sealed interface LineType {
    object Normal : LineType
    object FiveStep : LineType
    object TenStep : LineType
}