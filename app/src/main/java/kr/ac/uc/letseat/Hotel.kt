package kr.ac.uc.letseat.ui

data class Hotel(
    val name: String = "",
    val location: String = "",
    val rating: Double = 0.0,
    val imageUrl: String = "" // optional, if you want hotel images
)
