package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
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

        recyclerView = findViewById(R.id.recyclerViewHotel)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RestaurantAdapter(restaurantList) { restaurant ->
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("restaurantId", restaurant.id)  // ✅ Pass the ID properly
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        loadRestaurants()
    }

    private fun loadRestaurants() {
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { documents ->
                restaurantList.clear()
                for (doc in documents) {
                    val restaurant = doc.toObject(Restaurant::class.java)
                    restaurant.id = doc.id // ✅ store Firestore doc id
                    restaurantList.add(restaurant)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
