package kr.ac.uc.letseat.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.uc.letseat.R

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val textPay = findViewById<TextView>(R.id.textPayAmount)
        val total = intent.getStringExtra("total") ?: "Total: Rs. 0.00"
        textPay.text = total
    }
}
