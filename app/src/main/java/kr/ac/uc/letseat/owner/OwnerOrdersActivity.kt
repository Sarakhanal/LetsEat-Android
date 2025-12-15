package kr.ac.uc.letseat.owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.adapters.OwnerOrdersAdapter
import kr.ac.uc.letseat.models.OrderModel

class OwnerOrdersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_orders)

        recyclerView = findViewById(R.id.recyclerOwnerOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadOrders()
    }

    private fun loadOrders() {

        db.collection("orders")
            .orderBy("createdAt", Query.Direction.DESCENDING)   // 🔥 SORT NEWEST FIRST
            .get()
            .addOnSuccessListener { result ->

                val orders = result.toObjects(OrderModel::class.java)
                val ids = result.documents.map { it.id }          // 🔥 REAL DOC IDs

                recyclerView.adapter = OwnerOrdersAdapter(orders, ids)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}
