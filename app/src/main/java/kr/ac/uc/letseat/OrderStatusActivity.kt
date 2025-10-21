package kr.ac.uc.letseat.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.uc.letseat.R

class OrderStatusActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var timerText: TextView
    private lateinit var progressBar: ProgressBar

    private val stageDuration = 15000L // 15 seconds per stage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status)

        statusText = findViewById(R.id.txtOrderStatus)
        timerText = findViewById(R.id.txtTimer)
        progressBar = findViewById(R.id.progressBar)

        progressBar.max = 100
        startOrderProgress()
    }

    private fun startOrderProgress() {
        startStage("✅ Your order has been received!", "👨‍🍳 Preparing your food...", 0)
    }

    private fun startStage(currentText: String, nextText: String, stage: Int) {
        statusText.text = currentText
        progressBar.progress = 0

        object : CountDownTimer(stageDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsPassed = (stageDuration - millisUntilFinished) / 1000
                val secondsLeft = millisUntilFinished / 1000

                // Progress updates gradually
                val progress = ((secondsPassed.toFloat() / 15f) * 100).toInt()
                progressBar.progress = progress
                timerText.text = "⏳ $secondsLeft sec remaining"
            }

            override fun onFinish() {
                progressBar.progress = 100
                when (stage) {
                    0 -> startStage("👨‍🍳 Preparing your food...", "🚴 Your food is on the way!", 1)
                    1 -> startStage("🚴 Your food is on the way!", "🍽️ Order delivered! Enjoy your meal!", 2)
                    2 -> {
                        statusText.text = "🍽️ Order delivered! Enjoy your meal!"
                        timerText.text = ""
                        progressBar.progress = 100
                    }
                }
            }
        }.start()
    }
}
