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
    private val restaurantList: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantImage: ImageView = itemView.findViewById(R.id.hotelImage)
        val restaurantName: TextView = itemView.findViewById(R.id.hotelName_text)
        val restaurantLocation: TextView = itemView.findViewById(R.id.hotelLocation_text)
        val restaurantRating: TextView = itemView.findViewById(R.id.hotelRating_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]

        holder.restaurantName.text = restaurant.name
        holder.restaurantLocation.text = restaurant.location
        holder.restaurantRating.text = restaurant.rating.toString()

        Glide.with(holder.itemView.context)
            .load(restaurant.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.restaurantImage)

        holder.itemView.setOnClickListener {
            onItemClick(restaurant)
        }
    }

    override fun getItemCount(): Int = restaurantList.size
}
