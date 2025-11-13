package kr.ac.uc.letseat.owner

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.DriverOrder

class OwnerOrdersActivity : AppCompatActivity() {

    private lateinit var rvOrders: RecyclerView
    private lateinit var adapter: OwnerOrdersAdapter
    private val orderList = mutableListOf<DriverOrder>()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_orders)

        rvOrders = findViewById(R.id.rvOwnerOrders)
        rvOrders.layoutManager = LinearLayoutManager(this)

        adapter = OwnerOrdersAdapter(this, orderList)
        rvOrders.adapter = adapter

        loadOrders()
    }

    private fun loadOrders() {
        val ownerId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("orders")
            .whereEqualTo("restaurantOwnerId", ownerId)
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
