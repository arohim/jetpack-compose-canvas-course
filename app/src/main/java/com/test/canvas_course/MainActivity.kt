package com.test.canvas_course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.canvas_course.ui.DetectingTouch
import com.test.canvas_course.ui.clock.MyClock
import com.test.canvas_course.ui.linechart.LineChart
import com.test.canvas_course.ui.linechart.MyLineChart
import com.test.canvas_course.ui.scale.MyScale
import com.test.canvas_course.ui.scale.Scale
import com.test.canvas_course.ui.theme.CanvascourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            MyLineChart()
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
