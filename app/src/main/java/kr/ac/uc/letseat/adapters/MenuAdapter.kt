package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.MenuItem

class MenuAdapter(
    private val menuList: List<MenuItem>,
    private val onAddClicked: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val db = FirebaseFirestore.getInstance()
    private val userId = "demoUser" // later replace with FirebaseAuth user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]
        holder.name.text = item.name
        holder.price.text = "Rs. ${item.price}"

        if (item.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.image)
        }

        holder.addButton.setOnClickListener {
            addToCart(item, holder)
        }
    }

    override fun getItemCount(): Int = menuList.size

    private fun addToCart(item: MenuItem, holder: MenuViewHolder) {
        val cartRef = db.collection("users").document(userId).collection("cart")
        val docRef = cartRef.document(item.name)

        // Always upsert (merge), no pre-check
        val cartItem = hashMapOf(
            "name" to item.name,
            "price" to item.price,
            "imageUrl" to item.imageUrl
        )

        docRef.get().addOnSuccessListener { snapshot ->
            val currentQty = snapshot.getLong("quantity")?.toInt() ?: 0
            val newQty = currentQty + 1
            val update = cartItem + ("quantity" to newQty)

            docRef.set(update, SetOptions.merge())
                .addOnSuccessListener {
                    Toast.makeText(holder.itemView.context, "${item.name} → quantity $newQty", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(holder.itemView.context, "Failed to add ${item.name}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.menuName)
        val price: TextView = itemView.findViewById(R.id.menuPrice)
        val image: ImageView = itemView.findViewById(R.id.menuImage)
        val addButton: Button = itemView.findViewById(R.id.btnAdd)
    }
}
