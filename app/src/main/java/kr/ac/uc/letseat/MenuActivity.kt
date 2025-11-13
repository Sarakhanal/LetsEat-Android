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

    private var restaurantId = ""
    private var restaurantName = ""
    private var restaurantAddress = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        recyclerView = findViewById(R.id.recyclerViewMenu)
        btnViewCart = findViewById(R.id.btnViewCart)
        db = FirebaseFirestore.getInstance()

        restaurantId = intent.getStringExtra("restaurantId") ?: ""
        restaurantName = intent.getStringExtra("restaurantName") ?: ""
        restaurantAddress = intent.getStringExtra("restaurantAddress") ?: ""

        if (restaurantId.isEmpty()) {
            Toast.makeText(this, "Restaurant error", Toast.LENGTH_SHORT).show()
            finish()
        }

        // ✅ FIXED: YOU WERE MISSING THIS
        CartManager.setCurrentRestaurant(restaurantId)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MenuAdapter(menuList) { menuItem ->
            CartManager.addToCart(
                CartItem(menuItem.name, menuItem.price, 1)
            )
            Toast.makeText(this, "${menuItem.name} added to cart", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
        loadMenuItems()

        btnViewCart.setOnClickListener {
            val i = Intent(this, CartActivity::class.java)
            i.putExtra("restaurantId", restaurantId)
            i.putExtra("restaurantName", restaurantName)
            i.putExtra("restaurantAddress", restaurantAddress)
            startActivity(i)
        }
    }

    private fun loadMenuItems() {
        db.collection("restaurants")
            .document(restaurantId)
            .collection("menus")
            .get()
            .addOnSuccessListener {
                menuList.clear()
                for (doc in it) {
                    menuList.add(
                        MenuItem(
                            doc.getString("name") ?: "",
                            doc.getDouble("price") ?: 0.0,
                            doc.getString("imageUrl") ?: ""
                        )
                    )
                }
                adapter.notifyDataSetChanged()
            }
    }
}
