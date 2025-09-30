package kr.ac.uc.letseat.models

data class MenuItem(
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val quantity: Int = 1    // ✅ added field for cart support
)
