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
import kr.ac.uc.letseat.models.MenuItem
import kr.ac.uc.letseat.adapters.MenuAdapter

class MenuListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MenuAdapter
    private val menuList = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        recyclerView = findViewById(R.id.menuRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MenuAdapter(menuList) { menuItem ->
            Toast.makeText(this, "${menuItem.name} added to cart", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // ✅ View Cart Button
        val cartButton: Button = findViewById(R.id.btnCart)
        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // ✅ Get restaurantId from intent
        val restaurantId = intent.getStringExtra("restaurantId")
        if (restaurantId.isNullOrEmpty()) {
            Toast.makeText(this, "No restaurant selected", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Load menus
        loadMenus(restaurantId)
    }

    private fun loadMenus(restaurantId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("restaurants")
            .document(restaurantId)
            .collection("menus")
            .get()
            .addOnSuccessListener { documents ->
                menuList.clear()
                for (doc in documents) {
                    val menu = doc.toObject(MenuItem::class.java)
                    menuList.add(menu)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load menus", Toast.LENGTH_SHORT).show()
            }
    }
}
