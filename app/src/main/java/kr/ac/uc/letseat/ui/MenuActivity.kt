package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.MenuAdapter
import kr.ac.uc.letseat.models.MenuModel

class MenuActivity : AppCompatActivity() {

    private val menuList = mutableListOf<MenuModel>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // VIEW CART BUTTON — FIX
        val btnCart = findViewById<Button>(R.id.btnViewCart)
        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // restaurant ID passed from HotelListActivity
        val restaurantId = intent.getStringExtra("restaurantId") ?: return

        val recycler = findViewById<RecyclerView>(R.id.recyclerMenu)
        recycler.layoutManager = LinearLayoutManager(this)

        // attach adapter
        val adapter = MenuAdapter(menuList, restaurantId)
        recycler.adapter = adapter

        // load menu items from Firestore
        db.collection("restaurants")
            .document(restaurantId)
            .collection("menus")
            .get()
            .addOnSuccessListener { snap ->

                menuList.clear()

                for (doc in snap.documents) {
                    val m = doc.toObject(MenuModel::class.java)
                    if (m != null) menuList.add(m)
                }

                adapter.notifyDataSetChanged()
            }
    }
}
