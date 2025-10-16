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
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_list)

        recyclerView = findViewById(R.id.hotelRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RestaurantAdapter(restaurantList) { restaurant ->
            if (restaurant.id.isNotEmpty()) {
                val intent = Intent(this, MenuListActivity::class.java)
                intent.putExtra("restaurantId", restaurant.id)
                intent.putExtra("restaurantName", restaurant.name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid restaurant ID", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.adapter = adapter
        loadRestaurants()
    }

    private fun loadRestaurants() {
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->
                restaurantList.clear()
                for (doc in result) {
                    val id = doc.id
                    val name = doc.getString("name") ?: "Unknown"
                    val location = doc.getString("location") ?: "Unknown"
                    val rating = doc.getDouble("rating") ?: 0.0

                    restaurantList.add(Restaurant(id, name, location, rating))
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load restaurants", Toast.LENGTH_SHORT).show()
            }
    }
}
