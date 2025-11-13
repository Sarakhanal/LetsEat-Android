package kr.ac.uc.letseat.driver

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.DriverOrder

class DriverHomeActivity : AppCompatActivity() {

    private lateinit var rvDriverOrders: RecyclerView
    private lateinit var adapter: DriverOrderAdapter
    private val orderList = mutableListOf<DriverOrder>()
    private val db = FirebaseFirestore.getInstance()

    private var driverId: String = ""   // ✅ receives UID from login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_home)

        // ✅ get driver ID
        driverId = intent.getStringExtra("driverId") ?: ""

        // ✅ RecyclerView setup
        rvDriverOrders = findViewById(R.id.rvDriverOrders)
        rvDriverOrders.layoutManager = LinearLayoutManager(this)

        // ✅ Attach adapter WITH driverId
        adapter = DriverOrderAdapter(this, orderList, driverId)
        rvDriverOrders.adapter = adapter

        // ✅ Load Firestore orders
        loadOrders()
    }

    private fun loadOrders() {
        db.collection("orders")
            .whereEqualTo("status", "readyForPickup")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(this, "Error loading orders", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                orderList.clear()

                for (doc in value!!) {
                    val order = DriverOrder(
                        orderId = doc.id,
                        restaurantName = doc.getString("restaurantName") ?: "",
                        customerName = doc.getString("customerName") ?: "",
                        pickupLocation = doc.getString("restaurantAddress") ?: "",
                        dropLocation = doc.getString("customerAddress") ?: "",
                        status = doc.getString("status") ?: ""
                    )
                    orderList.add(order)
                }

                adapter.notifyDataSetChanged()
            }
    }
}
