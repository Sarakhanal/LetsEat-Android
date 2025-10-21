package kr.ac.uc.letseat.ui

import kr.ac.uc.letseat.models.CartItem

object CartManager {

    // 🔸 Map to store carts per restaurant (restaurantId → list of CartItems)
    private val cartsByRestaurant = mutableMapOf<String, MutableList<CartItem>>()

    // 🔸 Tracks which restaurant the user is currently ordering from
    private var currentRestaurantId: String? = null

    /**
     * Set the current restaurant context.
     * Called when the user opens a restaurant's menu.
     */
    fun setCurrentRestaurant(restaurantId: String) {
        currentRestaurantId = restaurantId
        if (!cartsByRestaurant.containsKey(restaurantId)) {
            cartsByRestaurant[restaurantId] = mutableListOf()
        }
    }

    /**
     * Add an item to the current restaurant's cart.
     */
    fun addToCart(item: CartItem) {
        val restaurantId = currentRestaurantId ?: return
        val cart = cartsByRestaurant.getOrPut(restaurantId) { mutableListOf() }

        val existingItem = cart.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            cart.add(item)
        }
    }

    /**
     * Get the list of items for the current restaurant.
     */
    fun getCartItems(): MutableList<CartItem> {
        val restaurantId = currentRestaurantId ?: return mutableListOf()
        return cartsByRestaurant[restaurantId] ?: mutableListOf()
    }

    /**
     * Update the quantity of a specific item in the current cart.
     */
    fun updateQuantity(name: String, newQuantity: Int) {
        val restaurantId = currentRestaurantId ?: return
        val cart = cartsByRestaurant[restaurantId] ?: return
        val item = cart.find { it.name == name }
        if (item != null) {
            item.quantity = newQuantity
        }
    }

    /**
     * Remove a specific item from the current restaurant’s cart.
     */
    fun removeItem(name: String) {
        val restaurantId = currentRestaurantId ?: return
        val cart = cartsByRestaurant[restaurantId] ?: return
        cart.removeAll { it.name == name }
    }

    /**
     * Clear the current restaurant’s cart only.
     */
    fun clearCartForRestaurant() {
        val restaurantId = currentRestaurantId ?: return
        cartsByRestaurant[restaurantId]?.clear()
    }

    /**
     * Completely clear all carts (used when logging out or logging in again).
     */
    fun clearAllCarts() {
        cartsByRestaurant.clear()
    }
}
