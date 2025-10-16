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
import kr.ac.uc.letseat.models.MenuItem

class MenuListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MenuAdapter
    private lateinit var btnViewCart: Button
    private val menuList = mutableListOf<MenuItem>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        recyclerView = findViewById(R.id.menuRecyclerView)
        btnViewCart = findViewById(R.id.btnViewCart)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MenuAdapter(menuList)
        recyclerView.adapter = adapter

        val restaurantName = intent.getStringExtra("restaurantName")
        if (restaurantName != null) {
            loadMenus(restaurantName)
        } else {
            Toast.makeText(this, "No restaurant selected", Toast.LENGTH_SHORT).show()
        }

        // ✅ When "View Cart" button is clicked
        btnViewCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadMenus(restaurantName: String) {
        db.collection("restaurants")
            .document(restaurantName)
            .collection("menus")
            .get()
            .addOnSuccessListener { result ->
                menuList.clear()
                for (doc in result) {
                    val name = doc.getString("name") ?: "Unknown"
                    val price = doc.getDouble("price") ?: 0.0
                    val imageUrl = doc.getString("imageUrl") ?: ""

                    menuList.add(
                        MenuItem(
                            name = name,
                            price = price,
                            imageUrl = imageUrl,
                            quantity = 1
                        )
                    )
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load menus", Toast.LENGTH_SHORT).show()
            }
    }
}
