package kr.ac.uc.letseat.models

data class Restaurant(
    var id: String = "",          // CHANGE val → var  ✔️
    val name: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val rating: Double = 4.5,
    val ownerId: String = "",
    val email: String = ""
)
