package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.MenuItem

class CheckoutActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val placeOrderBtn: Button = findViewById(R.id.btnPlaceOrder)
        placeOrderBtn.setOnClickListener {
            placeOrder()
        }
    }

    private fun placeOrder() {
        db.collection("users").document("demoUser").collection("cart")
            .get()
            .addOnSuccessListener { result ->
                val items = result.map { doc ->
                    val name = doc.getString("name") ?: "Unknown"
                    val price = doc.getDouble("price") ?: 0.0
                    val qty = doc.getLong("quantity")?.toInt() ?: 1
                    mapOf("name" to name, "price" to price, "quantity" to qty)
                }

                val order = hashMapOf(
                    "userId" to "demoUser",
                    "items" to items,
                    "status" to "Received"
                )

                db.collection("orders")
                    .add(order)
                    .addOnSuccessListener { docRef ->
                        Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show()

                        // clear cart
                        for (doc in result) {
                            doc.reference.delete()
                        }

                        // go to Order Status screen
                        val intent = Intent(this, OrderStatusActivity::class.java)
                        intent.putExtra("ORDER_ID", docRef.id)
                        startActivity(intent)
                        finish()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to place order", Toast.LENGTH_SHORT).show()
            }
    }
}
