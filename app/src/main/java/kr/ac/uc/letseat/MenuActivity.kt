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
import kr.ac.uc.letseat.adapters.MenuAdapter
import kr.ac.uc.letseat.models.CartItem
import kr.ac.uc.letseat.models.MenuItem

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnViewCart: Button
    private lateinit var adapter: MenuAdapter
    private val menuList = mutableListOf<MenuItem>()
    private lateinit var db: FirebaseFirestore
    private var restaurantId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        recyclerView = findViewById(R.id.recyclerViewMenu)
        btnViewCart = findViewById(R.id.btnViewCart)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MenuAdapter(menuList) { menuItem ->
            val cartItem = CartItem(
                name = menuItem.name,
                price = menuItem.price,
                quantity = 1
            )
            CartManager.addToCart(cartItem)
            Toast.makeText(this, "${menuItem.name} added to cart", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        db = FirebaseFirestore.getInstance()
        restaurantId = intent.getStringExtra("restaurantId")

        if (restaurantId != null) {
            CartManager.setCurrentRestaurant(restaurantId!!)
            loadMenuItems(restaurantId!!)
        } else {
            Toast.makeText(this, "No restaurant selected", Toast.LENGTH_SHORT).show()
        }

        // ✅ View Cart button click
        btnViewCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadMenuItems(restaurantId: String) {
        db.collection("restaurants")
            .document(restaurantId)
            .collection("menus")
            .get()
            .addOnSuccessListener { result ->
                menuList.clear()
                for (document in result) {
                    val name = document.getString("name") ?: ""
                    val price = document.getDouble("price") ?: 0.0
                    val imageUrl = document.getString("imageUrl") ?: ""

                    menuList.add(MenuItem(name, price, imageUrl))
                }

                if (menuList.isEmpty()) {
                    Toast.makeText(this, "No menu items found", Toast.LENGTH_SHORT).show()
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load menu: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
