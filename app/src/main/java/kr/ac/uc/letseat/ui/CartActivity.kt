package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.CartAdapter
import kr.ac.uc.letseat.models.CartItem

class CartActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val cartList = mutableListOf<CartItem>()

    private lateinit var txtCartTotal: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var btnCheckout: Button
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        txtCartTotal = findViewById(R.id.txtCartTotal)
        recycler = findViewById(R.id.recyclerCart)
        btnCheckout = findViewById(R.id.btnCheckout)

        recycler.layoutManager = LinearLayoutManager(this)

        // ADAPTER WITH onCartChanged CALLBACK
        adapter = CartAdapter(cartList) {
            updateTotal()
        }
        recycler.adapter = adapter

        loadCart()

        btnCheckout.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putParcelableArrayListExtra("cartItems", ArrayList(cartList))
            intent.putExtra("total", calculateTotal())
            startActivity(intent)
        }
    }

    private fun loadCart() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("users")
            .document(userId)
            .collection("cart")
            .get()
            .addOnSuccessListener { snap ->
                cartList.clear()
                for (doc in snap.documents) {
                    val item = doc.toObject(CartItem::class.java)
                    if (item != null) {
                        cartList.add(item)
                    }
                }
                adapter.notifyDataSetChanged()
                updateTotal()
            }
            .addOnFailureListener { e ->
                Log.e("CART", "Error loading cart: ${e.message}")
            }
    }

    private fun updateTotal() {
        txtCartTotal.text = "Total: Rs. ${calculateTotal()}"
    }

    private fun calculateTotal(): Int {
        var total = 0
        for (item in cartList) {
            total += item.price * item.quantity
        }
        return total
    }
}
