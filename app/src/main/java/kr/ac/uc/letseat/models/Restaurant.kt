package kr.ac.uc.letseat.models

data class Restaurant(
    var id: String = "",
    var name: String = "",
    var location: String = "", // matches Firestore "location"
    var imageUrl: String = "",
    var rating: Double = 0.0
)
