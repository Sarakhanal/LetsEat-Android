package kr.ac.uc.letseat.firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseManager {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
}
