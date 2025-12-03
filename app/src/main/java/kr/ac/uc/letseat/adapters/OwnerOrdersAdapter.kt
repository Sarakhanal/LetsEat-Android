package kr.ac.uc.letseat.owner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.OrderModel

class OwnerOrdersAdapter(
    private val orderList: List<OrderModel>
) : RecyclerView.Adapter<OwnerOrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOrderId: TextView = itemView.findViewById(R.id.txtOrderId)
        val txtStatus: TextView = itemView.findViewById(R.id.txtStatus)
        val txtTotalPrice: TextView = itemView.findViewById(R.id.txtTotalPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_owner_order, parent, false)
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
