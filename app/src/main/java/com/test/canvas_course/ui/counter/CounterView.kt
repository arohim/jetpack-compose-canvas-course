package com.test.canvas_course.ui.counter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.test.canvas_course.R
import kotlin.math.round

class CounterView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var cornerRadious: Float
    private var backgroundRect: RectF
    private var numberPaint: Paint
    private var linePaint: Paint
    private var backgroundPaint: Paint
    private var mCount: Int = 0
    private var displayCount: String = "%d.4"

    init {
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.color = ContextCompat.getColor(context, R.color.teal_200)

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint.color = ContextCompat.getColor(context, R.color.purple_200)
        linePaint.strokeWidth = 2f

        numberPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        numberPaint.color = ContextCompat.getColor(context, R.color.white)

        numberPaint.textSize = round(64f * resources.displayMetrics.scaledDensity)
        backgroundRect = RectF()

        cornerRadious = round(2f * resources.displayMetrics.density)
        setCount(0)
    }

    fun setCount(count: Int) {
        mCount = count
        displayCount = "%04d".format(count)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
        val width = canvas.width
        val height = canvas.height
        val centerX = width * 0.5f

        backgroundRect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRect(backgroundRect, backgroundPaint)

        val baselineY = round(height * 0.6f)
        canvas.drawLine(0f, baselineY, width.toFloat(), baselineY, linePaint)


        val textWidth = numberPaint.measureText(displayCount)
        val textX = round(centerX - textWidth * 0.5f)
        canvas.drawText(displayCount, textX, baselineY, numberPaint)
    }

}