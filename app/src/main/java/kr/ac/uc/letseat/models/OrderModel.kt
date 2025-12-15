package kr.ac.uc.letseat.models

data class OrderModel(
    val orderId: String = "",
    val customerId: String = "",
    val customerName: String = "",
    val customerAddress: String = "",

    val driverId: String = "",
    val driverName: String = "",

    val restaurantId: String = "",
    val restaurantName: String = "",
    val restaurantAddress: String = "",

    val items: List<OrderItemModel> = emptyList(),

    val totalPrice: Int = 0,
    val status: String = "",
    val createdAt: Long = 0L
)
