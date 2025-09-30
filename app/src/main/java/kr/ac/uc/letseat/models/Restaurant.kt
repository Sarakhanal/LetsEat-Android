package kr.ac.uc.letseat.models

data class Restaurant(
    val id: String = "",          // Firestore document ID
    val name: String = "",        // Restaurant name
    val location: String = "",    // Restaurant location
    val rating: Double = 0.0,     // Rating
    val imageUrl: String = ""     // Restaurant image URL
)
