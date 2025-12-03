package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.Restaurant

class RestaurantAdapter(
    private val list: List<Restaurant>,
    private val onClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgHotel)
        val name: TextView = v.findViewById(R.id.txtHotelName)
        val location: TextView = v.findViewById(R.id.txtHotelLocation)
        val rating: TextView = v.findViewById(R.id.txtHotelRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hotel, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val restaurant = list[position]

        holder.name.text = restaurant.name
        holder.location.text = restaurant.location
        holder.rating.text = "⭐ ${restaurant.rating}"

        Glide.with(holder.img)
            .load(restaurant.imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            onClick(restaurant)
        }
    }

    override fun getItemCount() = list.size
}
