package kr.ac.uc.letseat.models

data class CartItem(
    val name: String = "",
    val price: Double = 0.0,
    var quantity: Int = 1
)
