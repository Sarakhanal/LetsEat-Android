package kr.ac.uc.letseat.driver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.OrderModel

class DriverOrdersAdapter(
    private val orderList: List<OrderModel>
) : RecyclerView.Adapter<DriverOrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOrderId: TextView = itemView.findViewById(R.id.txtDriverOrderId)
        val txtStatus: TextView = itemView.findViewById(R.id.txtDriverStatus)
        val txtTotalPrice: TextView = itemView.findViewById(R.id.txtDriverTotal)
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
        holder.txtTotalPrice.text = "Total: Rs. ${order.totalPrice}"
    }

    override fun getItemCount(): Int = orderList.size
}
