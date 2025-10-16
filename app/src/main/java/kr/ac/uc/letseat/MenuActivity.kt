package kr.ac.uc.letseat.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.MenuAdapter
import kr.ac.uc.letseat.models.MenuItem

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private val menuList = mutableListOf<MenuItem>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        recyclerView = findViewById(R.id.recyclerViewMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)

        menuAdapter = MenuAdapter(menuList)
        recyclerView.adapter = menuAdapter

        val restaurantName = intent.getStringExtra("restaurantName")

        if (restaurantName != null) {
            loadMenus(restaurantName)
        } else {
            Toast.makeText(this, "No restaurant selected", Toast.LENGTH_SHORT).show()
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
                menuAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load menus", Toast.LENGTH_SHORT).show()
            }
    }
}
