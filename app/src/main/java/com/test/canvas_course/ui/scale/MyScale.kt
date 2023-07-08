package com.test.canvas_course.ui.scale

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
import com.test.canvas_course.ui.scale.Scale
import com.test.canvas_course.ui.theme.CanvascourseTheme

@Composable
fun MyScale() {
    var weight by remember {
        mutableStateOf(80)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = weight.toString(),
                    fontSize = 56.sp
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "KG", color = Color.Green,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(56.dp))
            Scale(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                weight = it
            }
        }
    }
}