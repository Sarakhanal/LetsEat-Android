package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.CartAdapter
import kr.ac.uc.letseat.models.MenuItem

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private val cartList = mutableListOf<MenuItem>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CartAdapter(cartList)
        recyclerView.adapter = adapter

        // ✅ Load Cart Items
        loadCart()

        // ✅ Checkout button
        val checkoutBtn: Button = findViewById(R.id.btnCheckout)
        checkoutBtn.setOnClickListener {
            if (cartList.isNotEmpty()) {
                val intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCart() {
        db.collection("users").document("demoUser").collection("cart")
            .get()
            .addOnSuccessListener { result ->
                cartList.clear()
                for (doc in result) {
                    val name = doc.getString("name") ?: "Unknown"
                    val price = doc.getDouble("price") ?: 0.0
                    val imageUrl = doc.getString("imageUrl") ?: ""
                    val qty = doc.getLong("quantity")?.toInt() ?: 1

                    val item = MenuItem(
                        name = name,
                        price = price,
                        imageUrl = imageUrl,
                        quantity = qty
                    )
                    cartList.add(item)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load cart", Toast.LENGTH_SHORT).show()
            }
    }
}
