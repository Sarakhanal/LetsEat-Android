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

    private val db = FirebaseFirestore.getInstance()
    private val list = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_list)

        val recycler = findViewById<RecyclerView>(R.id.recyclerRestaurants)
        recycler.layoutManager = LinearLayoutManager(this)

        db.collection("restaurants")
            .get()
            .addOnSuccessListener { snap ->
                list.clear()
                for (doc in snap.documents) {
                    val rest = doc.toObject(Restaurant::class.java)
                    if (rest != null) {
                        rest.id = doc.id   // ⭐ FIXED — SET RESTAURANT ID
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
