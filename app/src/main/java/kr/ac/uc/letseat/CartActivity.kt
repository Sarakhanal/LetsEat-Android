package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.CartAdapter
import kr.ac.uc.letseat.models.MenuItem

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalText: TextView
    private lateinit var checkoutButton: Button
    private lateinit var adapter: CartAdapter

    private val cartList = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalText = findViewById(R.id.txtCartTotal)
        checkoutButton = findViewById(R.id.btnCheckout)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // 🧩 Example data — replace later with real cart items
        cartList.add(MenuItem("Buff Sekuwa", 200.0, "", 1))
        cartList.add(MenuItem("Chicken Sekuwa", 180.0, "", 1))

        adapter = CartAdapter(cartList) {
            updateTotal()
        }

        recyclerView.adapter = adapter
        updateTotal()

        checkoutButton.setOnClickListener {
            val total = cartList.sumOf { it.price * it.quantity }
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("total", total)
            startActivity(intent)
        }
    }

    private fun updateTotal() {
        val total = cartList.sumOf { it.price * it.quantity }
        totalText.text = "Total: Rs. %.1f".format(total)
    }
}
