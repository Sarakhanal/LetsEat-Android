package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.CartAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var txtTotal: TextView
    private lateinit var btnClear: Button
    private lateinit var btnCheckout: Button
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerViewCart)
        txtTotal = findViewById(R.id.txtTotal)
        btnClear = findViewById(R.id.btnClear)
        btnCheckout = findViewById(R.id.btnCheckout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(CartManager.getCartItems()) {
            updateTotal()
        }
        recyclerView.adapter = adapter

        updateTotal()

        btnClear.setOnClickListener {
            CartManager.clearCartForRestaurant()
            adapter.notifyDataSetChanged()
            updateTotal()
            Toast.makeText(this, "Cart cleared", Toast.LENGTH_SHORT).show()
        }

        btnCheckout.setOnClickListener {
            val intent = Intent(this, OrderStatusActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateTotal() {
        val total = CartManager.getCartItems().sumOf { it.price * it.quantity }
        // ✅ Convert to Rs. (already converted earlier in MenuActivity)
        txtTotal.text = "Total: Rs. ${String.format("%.0f", total)}"
    }
}
