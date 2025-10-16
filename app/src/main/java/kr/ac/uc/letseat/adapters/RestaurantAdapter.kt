package kr.ac.uc.letseat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.models.Restaurant

class RestaurantAdapter(
    private val restaurantList: List<Restaurant>,
    private val onClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtRestaurantName)
        val location: TextView = view.findViewById(R.id.txtRestaurantLocation)
        val rating: TextView = view.findViewById(R.id.txtRestaurantRating)
    }

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

        holder.itemView.setOnClickListener { onClick(restaurant) }
    }

    override fun getItemCount(): Int = restaurantList.size
}
