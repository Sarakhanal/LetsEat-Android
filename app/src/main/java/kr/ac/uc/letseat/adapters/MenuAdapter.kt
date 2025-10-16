package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.MenuItem

class MenuAdapter(private val menuList: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]

        holder.name.text = item.name
        holder.price.text = "Rs. ${item.price}"

        if (item.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.placeholder_food)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder_food)
        }

        holder.addButton.setOnClickListener {
            val cartItem = hashMapOf(
                "name" to item.name,
                "price" to item.price,
                "quantity" to 1,
                "imageUrl" to item.imageUrl
            )

            db.collection("cart").add(cartItem)
                .addOnSuccessListener {
                    holder.addButton.text = "Added"
                    holder.addButton.isEnabled = false
                }
        }
    }

    override fun getItemCount(): Int = menuList.size

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.menuName)
        val price: TextView = itemView.findViewById(R.id.menuPrice)
        val image: ImageView = itemView.findViewById(R.id.menuImage)
        val addButton: Button = itemView.findViewById(R.id.addButton)
    }
}
