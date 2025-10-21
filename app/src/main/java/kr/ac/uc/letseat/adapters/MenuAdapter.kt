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
import kr.ac.uc.letseat.models.MenuItem

class MenuAdapter(
    private val menuList: List<MenuItem>,
    private val onAddToCartClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textMenuName)
        val price: TextView = view.findViewById(R.id.textMenuPrice)
        val image: ImageView = view.findViewById(R.id.imageMenu)
        val btnAdd: Button = view.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]
        holder.name.text = item.name
        holder.price.text = "Rs. ${item.price}"

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.placeholder_food)
            .into(holder.image)

        holder.btnAdd.setOnClickListener {
            onAddToCartClick(item)
        }
    }

    override fun getItemCount(): Int = menuList.size
}
