package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.CartItem

class CartAdapter(
    private val list: MutableList<CartItem>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgCart)
        val name: TextView = v.findViewById(R.id.txtCartName)
        val price: TextView = v.findViewById(R.id.txtCartPrice)
        val quantity: TextView = v.findViewById(R.id.txtQuantity)
        val btnPlus: Button = v.findViewById(R.id.btnPlus)
        val btnMinus: Button = v.findViewById(R.id.btnMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]

        holder.name.text = item.name
        holder.price.text = "Rs. ${item.price}"
        holder.quantity.text = item.quantity.toString()

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.placeholder_food)
            .error(R.drawable.placeholder_food)
            .into(holder.img)

        // ADD BUTTON
        holder.btnPlus.setOnClickListener {
            item.quantity++
            holder.quantity.text = item.quantity.toString()
            onCartChanged()
        }

        // REMOVE BUTTON
        holder.btnMinus.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                holder.quantity.text = item.quantity.toString()
                onCartChanged()
            }
        }
    }

    override fun getItemCount() = list.size
}
