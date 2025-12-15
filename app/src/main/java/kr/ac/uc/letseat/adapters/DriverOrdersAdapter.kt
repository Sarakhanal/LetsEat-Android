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

class DriverOrdersAdapter(
    private val orderList: List<OrderModel>
) : RecyclerView.Adapter<DriverOrdersAdapter.OrderViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOrderId: TextView = itemView.findViewById(R.id.txtDriverOrderId)
        val txtStatus: TextView = itemView.findViewById(R.id.txtDriverOrderStatus)
        val txtTotal: TextView = itemView.findViewById(R.id.txtDriverOrderTotal)

        val btnAccept: Button = itemView.findViewById(R.id.btnDriverAccept)
        val btnReject: Button = itemView.findViewById(R.id.btnDriverReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_driver_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]

        holder.txtOrderId.text = "Order: ${order.orderId}"
        holder.txtStatus.text = "Status: ${order.status}"
        holder.txtTotal.text = "Total Rs. ${order.totalPrice}"

        // ACCEPT BUTTON
        holder.btnAccept.setOnClickListener {
            db.collection("orders")
                .document(order.orderId)
                .update("status", "accepted_by_driver")
        }

        // REJECT BUTTON
        holder.btnReject.setOnClickListener {
            db.collection("orders")
                .document(order.orderId)
                .update("status", "rejected_by_driver")
        }
    }

    override fun getItemCount(): Int = orderList.size
}
