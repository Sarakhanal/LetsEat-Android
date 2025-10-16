package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.uc.letseat.R

class CheckoutActivity : AppCompatActivity() {

    private lateinit var txtTotal: TextView
    private lateinit var btnProceed: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        txtTotal = findViewById(R.id.txtTotal)
        btnProceed = findViewById(R.id.btnProceedPayment)

        val totalAmount = intent.getDoubleExtra("total", 0.0)
        txtTotal.text = "Pay Rs. %.1f".format(totalAmount)

        btnProceed.setOnClickListener {
            val intent = Intent(this, OrderStatusActivity::class.java)
            startActivity(intent)
        }
    }
}
