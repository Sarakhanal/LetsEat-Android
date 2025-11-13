package kr.ac.uc.letseat.driver

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

class DriverOrderAdapter(
    private val context: Context,
    private val orders: MutableList<DriverOrder>,
    private val driverId: String
) : RecyclerView.Adapter<DriverOrderAdapter.DriverOrderViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverOrderViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_driver_order, parent, false)
        return DriverOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverOrderViewHolder, position: Int) {
        val order = orders[position]

        holder.tvRestaurantName.text = "Restaurant: ${order.restaurantName}"
        holder.tvCustomerName.text = "Customer: ${order.customerName}"
        holder.tvPickupLocation.text = "Pickup: ${order.pickupLocation}"
        holder.tvDropLocation.text = "Drop-off: ${order.dropLocation}"

        holder.btnAccept.setOnClickListener {
            // ✅ Check if orderId exists before updating
            if (order.orderId.isBlank()) {
                Toast.makeText(context, "Error: Invalid order ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            holder.btnAccept.isEnabled = false

            db.collection("orders")
                .document(order.orderId)
                .update(
                    mapOf(
                        "status" to "accepted",
                        "driverId" to driverId
                    )
                )
                .addOnSuccessListener {
                    Toast.makeText(context, "Order accepted!", Toast.LENGTH_SHORT).show()

                    // Remove from list safely
                    if (position in orders.indices) {
                        orders.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, orders.size)
                    }
                }
                .addOnFailureListener { e ->
                    holder.btnAccept.isEnabled = true
                    Toast.makeText(context, "Update failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun getItemCount(): Int = orders.size

    class DriverOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRestaurantName: TextView = itemView.findViewById(R.id.tvRestaurantName)
        val tvCustomerName: TextView = itemView.findViewById(R.id.tvCustomerName)
        val tvPickupLocation: TextView = itemView.findViewById(R.id.tvPickupLocation)
        val tvDropLocation: TextView = itemView.findViewById(R.id.tvDropLocation)
        val btnAccept: Button = itemView.findViewById(R.id.btnAccept)
    }
}
