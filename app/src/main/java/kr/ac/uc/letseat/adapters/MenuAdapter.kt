package kr.ac.uc.letseat.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.CartItem
import kr.ac.uc.letseat.models.MenuModel

class MenuAdapter(
    private val list: List<MenuModel>,
    private val restaurantId: String
) : RecyclerView.Adapter<MenuAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgItem)
        val name: TextView = v.findViewById(R.id.txtName)
        val price: TextView = v.findViewById(R.id.txtPrice)
        val btnAdd: Button = v.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]

        holder.name.text = item.name
        holder.price.text = "Rs. ${item.price}"

        Glide.with(holder.itemView.context)
            .load(item.imageUrl.ifEmpty { R.mipmap.ic_launcher })
            .into(holder.img)

        holder.btnAdd.setOnClickListener {
            Log.d("ADD_CLICK", "Clicked: ${item.name}")

            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val db = FirebaseFirestore.getInstance()

            val docId = item.id.ifEmpty { "menu_${System.currentTimeMillis()}" }

            val cartItem = CartItem(
                menuId = item.id,
                name = item.name,
                price = item.price,
                quantity = 1,
                imageUrl = item.imageUrl,
                restaurantId = restaurantId       // ← FIXED
            )

            db.collection("users")
                .document(userId)
                .collection("cart")
                .document(docId)
                .set(cartItem, SetOptions.merge())
        }
    }

    override fun getItemCount() = list.size
}
