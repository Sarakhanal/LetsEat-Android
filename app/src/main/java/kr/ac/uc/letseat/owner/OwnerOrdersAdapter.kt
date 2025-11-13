package kr.ac.uc.letseat.owner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.DriverOrder

class OwnerOrdersAdapter(
    private val context: Context,
    private val orders: MutableList<DriverOrder>
) : RecyclerView.Adapter<OwnerOrdersAdapter.ViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_owner_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]

        holder.tvOrderId.text = "Order ID: ${order.orderId}"
        holder.tvCustomer.text = "Customer: ${order.customerName}"
        holder.tvStatus.text = "Status: ${order.status}"

        when (order.status) {
            "pending" -> {
                holder.btnAccept.visibility = View.VISIBLE
                holder.btnMarkReady.visibility = View.GONE
                holder.btnComplete.visibility = View.GONE
            }

            "preparing" -> {
                holder.btnAccept.visibility = View.GONE
                holder.btnMarkReady.visibility = View.VISIBLE
                holder.btnComplete.visibility = View.GONE
            }

            "readyForPickup" -> {
                holder.btnAccept.visibility = View.GONE
                holder.btnMarkReady.visibility = View.GONE
                holder.btnComplete.visibility = View.VISIBLE
            }

            else -> {
                holder.btnAccept.visibility = View.GONE
                holder.btnMarkReady.visibility = View.GONE
                holder.btnComplete.visibility = View.GONE
            }
        }

        holder.btnAccept.setOnClickListener {
            updateStatus(order, "preparing")
        }

        holder.btnMarkReady.setOnClickListener {
            updateStatus(order, "readyForPickup")
        }

        holder.btnComplete.setOnClickListener {
            updateStatus(order, "completed")
        }
    }

    override fun getItemCount(): Int = orders.size

    private fun updateStatus(order: DriverOrder, status: String) {
        db.collection("orders")
            .document(order.orderId)
            .update("status", status)
            .addOnSuccessListener {
                Toast.makeText(context, "Status updated!", Toast.LENGTH_SHORT).show()
            }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrderId: TextView = view.findViewById(R.id.tvOrderId)
        val tvCustomer: TextView = view.findViewById(R.id.tvCustomer)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val btnAccept: Button = view.findViewById(R.id.btnAcceptOrder)
        val btnMarkReady: Button = view.findViewById(R.id.btnMarkReady)
        val btnComplete: Button = view.findViewById(R.id.btnComplete)
    }
}
