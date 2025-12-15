package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.CartItem
import kr.ac.uc.letseat.models.OrderItemModel
import kr.ac.uc.letseat.models.OrderModel

class CheckoutActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var cartItems: ArrayList<CartItem>
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        total = intent.getIntExtra("total", 0)
        cartItems = intent.getParcelableArrayListExtra<CartItem>("cartItems") ?: arrayListOf()

        findViewById<TextView>(R.id.txtTotalPrice).text = "Total: Rs. $total"
        findViewById<Button>(R.id.btnConfirmOrder).setOnClickListener {
            placeOrder()
        }
    }

    private fun placeOrder() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

        if (cartItems.isEmpty()) {
            Log.e("ORDER", "Cart empty, cannot place order")
            return
        }

        val restaurantId = cartItems[0].restaurantId
        val orderId = db.collection("orders").document().id

        // Convert CartItem → OrderItemModel
        val orderItems = cartItems.map {
            OrderItemModel(
                id = it.menuId,             // menuId → id
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                imageURL = it.imageUrl      // imageUrl → imageURL
            )
        }

        // Build the Firestore order EXACTLY matching your database structure
        val order = OrderModel(
            orderId = orderId,
            customerId = userId,
            customerName = userEmail,
            customerAddress = "",                  // optional

            restaurantId = restaurantId,
            restaurantName = "",
            restaurantAddress = "",

            driverId = "",
            driverName = "",

            items = orderItems,

            totalPrice = total,
            status = "pending",
            createdAt = System.currentTimeMillis()
        )

        db.collection("orders")
            .document(orderId)
            .set(order)
            .addOnSuccessListener {
                Log.d("ORDER", "🔥 ORDER SAVED SUCCESSFULLY")

                clearCart(userId)

                val i = Intent(this, WaitingActivity::class.java)
                i.putExtra("orderId", orderId)
                startActivity(i)
                finish()
            }
            .addOnFailureListener { e ->
                Log.e("ORDER", "❌ Failed to save order: ${e.message}")
            }
    }

    private fun clearCart(userId: String) {
        db.collection("users")
            .document(userId)
            .collection("cart")
            .get()
            .addOnSuccessListener { snap ->
                for (doc in snap.documents) {
                    doc.reference.delete()
                }
            }
    }
}
