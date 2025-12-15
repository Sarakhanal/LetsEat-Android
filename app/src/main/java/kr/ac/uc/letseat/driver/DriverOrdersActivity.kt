package kr.ac.uc.letseat.driver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.DriverOrdersAdapter
import kr.ac.uc.letseat.models.OrderModel

class DriverOrdersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_orders)

        recyclerView = findViewById(R.id.recyclerDriverOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadOrders()
    }

    private fun loadOrders() {
        db.collection("orders")
            .orderBy("createdAt", Query.Direction.DESCENDING) // 🔥 NEWEST FIRST
            .get()
            .addOnSuccessListener { result ->
                val orders = result.toObjects(OrderModel::class.java)
                recyclerView.adapter = DriverOrdersAdapter(orders) // ✔ only 1 argument
            }
            .addOnFailureListener { it.printStackTrace() }
    }
}
