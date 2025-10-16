package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.MenuItem

class CartAdapter(
    private val cartList: MutableList<MenuItem>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtCartItemName)
        val price: TextView = view.findViewById(R.id.txtCartItemPrice)
        val quantity: TextView = view.findViewById(R.id.txtCartItemQuantity)
        val btnIncrease: Button = view.findViewById(R.id.btnIncrease)
        val btnDecrease: Button = view.findViewById(R.id.btnDecrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]

        holder.name.text = item.name
        holder.price.text = "Rs. ${item.price * item.quantity}"
        holder.quantity.text = "×${item.quantity}"

        holder.btnIncrease.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onQuantityChanged()
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                onQuantityChanged()
            } else {
                cartList.removeAt(position)
                notifyItemRemoved(position)
                onQuantityChanged()
            }
        }
    }

    override fun getItemCount(): Int = cartList.size
}
