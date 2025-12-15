package kr.ac.uc.letseat.models
data class OrderItemModel(
    val id: String = "",
    val imageURL: String = "",
    val name: String = "",
    val price: Int = 0,
    val quantity: Int = 0
)
