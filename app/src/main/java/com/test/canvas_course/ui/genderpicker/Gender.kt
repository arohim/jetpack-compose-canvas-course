package com.test.canvas_course.ui.genderpicker

sealed interface Gender {
    object Male : Gender
    object Female : Gender
}