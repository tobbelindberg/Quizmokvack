package com.quizmokvack.widgets

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.quizmokvack.R
import kotlin.math.roundToInt

class CountDownView
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var timeLeft = 15000L
    var countDownTimer = newCounter(timeLeft)

    init {
        text = (timeLeft / 1000.0).roundToInt().toString()

    }

    var onTimeIsUpCallback = {

    }

    private fun newCounter(startTime: Long): CountDownTimer {
        return object : CountDownTimer(startTime, 1000L) {
            override fun onTick(millisLeft: Long) {
                timeLeft = millisLeft
                text = (timeLeft / 1000.0).roundToInt().toString()

            }

            override fun onFinish() {
                setText(R.string.time_is_up)
                onTimeIsUpCallback()
            }

        }
    }

    fun start() {
        countDownTimer.start()
    }

    fun cancel() {
        countDownTimer.cancel()
    }

    fun boostTime() {
        countDownTimer.cancel()
        timeLeft = timeLeft + 10000L
        text = (timeLeft / 1000.0).roundToInt().toString()
        countDownTimer = newCounter(timeLeft)
        countDownTimer.start()
    }
}