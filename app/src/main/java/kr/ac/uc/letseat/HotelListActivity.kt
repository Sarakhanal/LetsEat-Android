package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.RestaurantAdapter
import kr.ac.uc.letseat.models.Restaurant

class HotelListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantAdapter
    private val restaurantList = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_list)

        recyclerView = findViewById(R.id.hotelRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RestaurantAdapter(restaurantList) { restaurant ->
            // when a restaurant is clicked
            if (restaurant.id.isNotEmpty()) {
                val intent = Intent(this, MenuListActivity::class.java)
                intent.putExtra("restaurantId", restaurant.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid restaurant ID", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter

        loadRestaurants()
    }

    private fun loadRestaurants() {
        val db = FirebaseFirestore.getInstance()
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->
                restaurantList.clear()
                for (document in result) {
                    val restaurant = document.toObject(Restaurant::class.java).copy(id = document.id)
                    restaurantList.add(restaurant)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error loading restaurants: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
