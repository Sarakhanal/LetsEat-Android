package kr.ac.uc.letseat.models

data class OrderItemModel(
    val menuId: String = "",
    val name: String = "",
    val price: Int = 0,         // ← FIXED (was Double)
    val quantity: Int = 1,
    val imageUrl: String = ""
)
