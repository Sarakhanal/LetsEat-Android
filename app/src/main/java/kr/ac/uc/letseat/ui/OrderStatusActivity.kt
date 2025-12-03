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

        val txtStatus = findViewById<TextView>(R.id.txtOrderStatus)
        val orderId = intent.getStringExtra("orderId") ?: return

        db.collection("orders")
            .document(orderId)
            .addSnapshotListener { snap, _ ->

                if (snap != null && snap.exists()) {
                    val status = snap.getString("status") ?: "pending"

                    txtStatus.text = when (status) {

                        "pending" ->
                            "Waiting for restaurant to accept..."

                        "accepted_restaurant" ->
                            "Restaurant accepted your order.\nPreparing food..."

                        "rejected_restaurant" ->
                            "Restaurant rejected your order."

                        "waiting_driver" ->
                            "Waiting for driver to accept your delivery..."

                        "accepted_driver" ->
                            "Driver accepted your order.\nEnjoy your food soon!"

                        "rejected_driver" ->
                            "Driver rejected the delivery."

                        "delivered" ->
                            "Delivered! Enjoy your food! ❤️"

                        else -> "Updating..."
                    }
                }
            }
    }
}
