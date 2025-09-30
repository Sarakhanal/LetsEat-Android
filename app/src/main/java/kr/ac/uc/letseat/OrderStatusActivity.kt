package kr.ac.uc.letseat.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R

class OrderStatusActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status)

        val orderId = intent.getStringExtra("ORDER_ID")
        val statusText: TextView = findViewById(R.id.orderStatusText)  // ✅ match XML id

        if (orderId != null) {
            db.collection("orders").document(orderId)
                .addSnapshotListener { snapshot, _ ->
                    if (snapshot != null && snapshot.exists()) {
                        val status = snapshot.getString("status") ?: "Unknown"
                        statusText.text = "Order Status: $status"
                    } else {
                        statusText.text = "Order not found"
                    }
                }
        } else {
            statusText.text = "No Order ID passed"
        }
    }
}
