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
    private val onClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hotel, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]

        holder.name.text = restaurant.name
        holder.location.text = restaurant.location
        holder.rating.text = "⭐ ${restaurant.rating}"

        Glide.with(holder.itemView.context)
            .load(restaurant.imageUrl)
            .placeholder(R.drawable.placeholder_food)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onClick(restaurant)
        }
    }

    override fun getItemCount(): Int = restaurantList.size

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageRestaurant)
        val name: TextView = itemView.findViewById(R.id.txtRestaurantName)
        val location: TextView = itemView.findViewById(R.id.txtRestaurantLocation)
        val rating: TextView = itemView.findViewById(R.id.txtRestaurantRating)
    }
}
