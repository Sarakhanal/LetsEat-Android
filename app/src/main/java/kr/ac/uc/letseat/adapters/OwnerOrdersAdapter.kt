package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.OrderModel

class OwnerOrdersAdapter(
    private val orderList: List<OrderModel>,
    private val docIds: List<String>   // 🔥 real Firestore doc IDs
) : RecyclerView.Adapter<OwnerOrdersAdapter.OrderViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOrderId: TextView = itemView.findViewById(R.id.txtOwnerOrderId)
        val txtTotal: TextView = itemView.findViewById(R.id.txtOwnerAmount)
        val btnAccept: Button = itemView.findViewById(R.id.btnOwnerAccept)
        val btnReject: Button = itemView.findViewById(R.id.btnOwnerReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_owner_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        val docId = docIds[position]      // 🔥 safe ID

        holder.txtOrderId.text = "Order: $docId"
        holder.txtTotal.text = "Total: Rs. ${order.totalPrice}"

        holder.btnAccept.setOnClickListener {
            db.collection("orders")
                .document(docId)
                .update("status", "accepted")
        }

        holder.btnReject.setOnClickListener {
            db.collection("orders")
                .document(docId)
                .update("status", "rejected")
        }
    }

    override fun getItemCount(): Int = orderList.size
}
