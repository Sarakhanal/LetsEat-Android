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

        if (cartItems.isEmpty()) {
            Log.e("ORDER", "Cart empty, cannot place order")
            return
        }

        val restaurantId = cartItems[0].restaurantId

        val orderId = db.collection("orders").document().id

        val orderItems = cartItems.map {
            OrderItemModel(
                menuId = it.menuId,
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                imageUrl = it.imageUrl
            )
        }

        val order = OrderModel(
            orderId = orderId,
            userId = userId,
            restaurantId = restaurantId,
            items = orderItems,
            totalPrice = total,
            status = "pending",
            timestamp = System.currentTimeMillis()
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
