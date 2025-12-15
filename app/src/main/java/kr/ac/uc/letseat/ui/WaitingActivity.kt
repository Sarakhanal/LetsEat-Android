package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R

class WaitingActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)

        txtStatus = findViewById(R.id.txtStatus)

        val orderId = intent.getStringExtra("orderId")
        if (orderId == null) {
            Log.e("WAITING", "Missing orderId")
            finish()
            return
        }

        listenForStatusUpdates(orderId)
    }

    private fun listenForStatusUpdates(orderId: String) {

        db.collection("orders")
            .document(orderId)
            .addSnapshotListener { snap, error ->

                if (error != null) {
                    Log.e("WAITING", "Listener error: ${error.message}")
                    return@addSnapshotListener
                }

                if (snap == null || !snap.exists()) {
                    txtStatus.text = "Order not found."
                    return@addSnapshotListener
                }

                val status = snap.getString("status") ?: "pending"
                Log.d("WAITING", "Status update: $status")

                when (status) {

                    "pending" -> {
                        txtStatus.text = "Waiting for restaurant to accept…"
                    }

                    "accepted" -> {
                        txtStatus.text = "Restaurant accepted your order!"

                        // Move to OrderStatusActivity (tracking screen)
                        val i = Intent(this, OrderStatusActivity::class.java)
                        i.putExtra("orderId", orderId)
                        startActivity(i)
                        finish()
                    }

                    "rejected" -> {
                        txtStatus.text = "Restaurant rejected your order."
                    }

                    "ready" -> {
                        txtStatus.text = "Food is ready!"
                    }

                    "delivered" -> {
                        txtStatus.text = "Order delivered! Enjoy!"
                    }
                }
            }
    }
}
