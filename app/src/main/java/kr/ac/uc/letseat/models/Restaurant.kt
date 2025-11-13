package kr.ac.uc.letseat.models

data class Restaurant(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var location: String = "",
    var rating: Double = 0.0,
    var imageUrl: String = ""
)
