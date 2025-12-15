package kr.ac.uc.letseat.models

data class MenuModel(
    var id: String = "",
    var name: String = "",
    var price: Int = 0,          // ← FIXED (must be INT)
    var imageUrl: String = ""
)
