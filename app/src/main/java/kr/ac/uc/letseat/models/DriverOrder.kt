package kr.ac.uc.letseat.models

data class DriverOrder(
    val orderId: String = "",
    val restaurantName: String = "",
    val customerName: String = "",
    val pickupLocation: String = "",
    val dropLocation: String = "",
    val status: String = ""
)
