package kr.ac.uc.letseat.models

data class OrderModel(
    val orderId: String = "",
    val userId: String = "",
    val restaurantId: String = "",
    val items: List<OrderItemModel> = emptyList(),
    val totalPrice: Int = 0,
    val status: String = "pending",
    val timestamp: Long = 0L
)
