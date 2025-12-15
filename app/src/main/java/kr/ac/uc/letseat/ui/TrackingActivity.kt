package kr.ac.uc.letseat.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.uc.letseat.R

class TrackingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)

        val txt = findViewById<TextView>(R.id.txtTracking)
        val orderId = intent.getStringExtra("orderId") ?: ""

        txt.text = "Tracking Order: $orderId\nDriver is on the way..."
    }
}
