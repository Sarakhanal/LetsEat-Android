package kr.ac.uc.letseat.owner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R

class OwnerDashboardActivity : AppCompatActivity() {

    private lateinit var tvRestaurantName: TextView
    private lateinit var tvRestaurantLocation: TextView
    private lateinit var tvRestaurantRating: TextView

    private lateinit var btnManageMenu: Button
    private lateinit var btnViewOrders: Button
    private lateinit var btnLogout: Button
    private lateinit var btnFixOwnerId: Button

    private val db = FirebaseFirestore.getInstance()
    private var ownerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_screen)

        ownerId = FirebaseAuth.getInstance().currentUser!!.uid

        tvRestaurantName = findViewById(R.id.tvRestaurantName)
        tvRestaurantLocation = findViewById(R.id.tvRestaurantLocation)
        tvRestaurantRating = findViewById(R.id.tvRestaurantRating)

        btnManageMenu = findViewById(R.id.btnManageMenu)
        btnViewOrders = findViewById(R.id.btnViewOrders)
        btnLogout = findViewById(R.id.btnLogout)
        btnFixOwnerId = findViewById(R.id.btnFixOwnerId)

        // Load restaurant info
        loadRestaurantInfo(ownerId)

        // Manage menu (coming soon)
        btnManageMenu.setOnClickListener {
            Toast.makeText(this, "Menu management coming soon", Toast.LENGTH_SHORT).show()
        }

        // View Orders
        btnViewOrders.setOnClickListener {
            startActivity(Intent(this, OwnerOrdersActivity::class.java))
        }

        // Logout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

        // Fix Owner ID (🔥 IMPORTANT)
        btnFixOwnerId.setOnClickListener {
            fixOwnerId(ownerId)
        }
    }

    private fun loadRestaurantInfo(ownerId: String) {
        db.collection("restaurants")
            .whereEqualTo("ownerId", ownerId)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val doc = result.documents[0]

                    tvRestaurantName.text = "Name: ${doc.getString("name") ?: "Not set"}"
                    tvRestaurantLocation.text = "Location: ${doc.getString("location") ?: "Not set"}"
                    tvRestaurantRating.text = "Rating: ${doc.getDouble("rating") ?: 0.0}"
                } else {
                    tvRestaurantName.text = "No restaurant linked!"
                    tvRestaurantLocation.text = ""
                    tvRestaurantRating.text = ""
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load restaurant", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fixOwnerId(ownerId: String) {
        // ✅ Change THIS to your restaurant document ID
        val restaurantDoc = "Bajeko Sekuwa"   // Example from screenshot

        db.collection("restaurants")
            .document(restaurantDoc)
            .update("ownerId", ownerId)
            .addOnSuccessListener {
                Toast.makeText(this, "OwnerID fixed!", Toast.LENGTH_LONG).show()
                loadRestaurantInfo(ownerId)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Fix failed!", Toast.LENGTH_SHORT).show()
            }
    }
}
