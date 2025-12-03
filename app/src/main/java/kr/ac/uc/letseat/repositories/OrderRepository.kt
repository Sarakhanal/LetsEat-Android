package kr.ac.uc.letseat.repositories

import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.models.OrderItemModel
import kr.ac.uc.letseat.models.OrderModel

object OrderRepository {

    private val db = FirebaseFirestore.getInstance()

    fun placeOrder(order: OrderModel) {

        val docRef = db.collection("orders").document(order.orderId)

        docRef.set(order)
    }

    fun updateStatus(orderId: String, newStatus: String) {
        db.collection("orders")
            .document(orderId)
            .update("status", newStatus)
    }
}
