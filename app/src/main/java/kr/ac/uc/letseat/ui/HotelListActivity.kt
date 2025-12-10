package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.RestaurantAdapter
import kr.ac.uc.letseat.models.Restaurant

class HotelListActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val list = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_list)

        val userLocation = intent.getStringExtra("location")

        val title = findViewById<TextView>(R.id.txtLocationTitle)
        title.text = "Restaurants near $userLocation"

        val recycler = findViewById<RecyclerView>(R.id.recyclerRestaurants)
        recycler.layoutManager = LinearLayoutManager(this)

        // First try to load restaurants for the selected location
        db.collection("restaurants")
            .whereEqualTo("location", userLocation)
            .get()
            .addOnSuccessListener { snap ->

                list.clear()

                // ⭐ If no restaurants found for location → load all restaurants
                if (snap.isEmpty) {

                    db.collection("restaurants")
                        .get()
                        .addOnSuccessListener { allSnap ->

                            list.clear()
                            for (doc in allSnap.documents) {
                                val rest = doc.toObject(Restaurant::class.java)
                                if (rest != null) {
                                    rest.id = doc.id
                                    list.add(rest)
                                }
                            }

                            recycler.adapter = RestaurantAdapter(list) { restaurant ->
                                val i = Intent(this, MenuActivity::class.java)
                                i.putExtra("restaurantId", restaurant.id)
                                startActivity(i)
                            }
                        }

                } else {
                    // ⭐ Found restaurants for the selected location
                    for (doc in snap.documents) {
                        val rest = doc.toObject(Restaurant::class.java)
                        if (rest != null) {
                            rest.id = doc.id
                            list.add(rest)
                        }
                    }

                    recycler.adapter = RestaurantAdapter(list) { restaurant ->
                        val i = Intent(this, MenuActivity::class.java)
                        i.putExtra("restaurantId", restaurant.id)
                        startActivity(i)
                    }
                }
            }
    }
}
