package kr.ac.uc.letseat.models

data class MenuItem(
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    var quantity: Int = 1
)
