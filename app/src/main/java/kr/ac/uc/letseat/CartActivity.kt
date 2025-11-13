package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.CartAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var txtTotal: TextView
    private lateinit var btnClear: Button
    private lateinit var btnCheckout: Button

    private lateinit var adapter: CartAdapter

    private var restaurantId = ""
    private var restaurantName = ""
    private var restaurantAddress = ""

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerViewCart)
        txtTotal = findViewById(R.id.txtTotal)
        btnClear = findViewById(R.id.btnClear)
        btnCheckout = findViewById(R.id.btnCheckout)

        restaurantId = intent.getStringExtra("restaurantId") ?: ""
        restaurantName = intent.getStringExtra("restaurantName") ?: "Unknown Restaurant"
        restaurantAddress = intent.getStringExtra("restaurantAddress") ?: "Unknown Address"

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(CartManager.getCartItems()) { updateTotal() }
        recyclerView.adapter = adapter

        updateTotal()

        btnClear.setOnClickListener {
            CartManager.clearCartForRestaurant()
            adapter.notifyDataSetChanged()
            updateTotal()
        }

        // ✅ GUARANTEED WORKING CHECKOUT
        btnCheckout.setOnClickListener {
            placeOrder()
        }
    }

    private fun updateTotal() {
        val total = CartManager.getCartItems().sumOf { it.price * it.quantity }
        txtTotal.text = "Total: Rs. ${String.format("%.0f", total)}"
    }

    private fun placeOrder() {

        val itemsList = CartManager.getCartItems().map {
            mapOf(
                "name" to it.name,
                "price" to it.price,
                "quantity" to it.quantity
            )
        }

        val orderData = hashMapOf(
            "restaurantId" to restaurantId,
            "restaurantName" to restaurantName,
            "restaurantAddress" to restaurantAddress,

            // ✅ SAFEST DEFAULT CUSTOMER INFO
            "customerName" to "Customer",
            "customerAddress" to "Unknown",

            "items" to itemsList,
            "totalPrice" to CartManager.getCartItems().sumOf { it.price * it.quantity },
            "status" to "readyForPickup",
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("orders")
            .add(orderData)
            .addOnSuccessListener {
                Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show()

                CartManager.clearCartForRestaurant()
                adapter.notifyDataSetChanged()
                updateTotal()

                startActivity(Intent(this, OrderStatusActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}
