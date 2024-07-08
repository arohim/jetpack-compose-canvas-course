package com.test.canvas_course

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.test.canvas_course.ui.counter.CounterView

class CounterActivity : ComponentActivity() {
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counter_activity)
        val counterView = findViewById<CounterView>(R.id.counterView) as CounterView
        findViewById<Button>(R.id.button).setOnClickListener {
            counterView.setCount(++counter)
        }
    }
}
