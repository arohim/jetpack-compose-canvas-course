package com.test.canvas_course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.test.canvas_course.ui.DetectingTouch
import com.test.canvas_course.ui.images.ImageBlendModes
import com.test.canvas_course.ui.images.ImageBlendQuiz
import com.test.canvas_course.ui.theme.CanvascourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            ImageBlendQuiz()
        }
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CanvascourseTheme {
        DetectingTouch()
    }
}
