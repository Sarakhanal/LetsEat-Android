package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.CartItem
import kr.ac.uc.letseat.ui.CartManager

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.textCartName)
        val txtPrice: TextView = itemView.findViewById(R.id.textCartPrice)
        val txtQuantity: TextView = itemView.findViewById(R.id.textCartQuantity)
        val btnIncrease: Button = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: Button = itemView.findViewById(R.id.btnDecrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.txtName.text = item.name
        holder.txtPrice.text = "Rs. ${item.price * item.quantity}"
        holder.txtQuantity.text = item.quantity.toString()

        holder.btnIncrease.setOnClickListener {
            CartManager.updateQuantity(item.name, item.quantity + 1)
            notifyDataSetChanged()
            onCartUpdated()
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                CartManager.updateQuantity(item.name, item.quantity - 1)
            } else {
                CartManager.removeItem(item.name)
            }
            notifyDataSetChanged()
            onCartUpdated()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
