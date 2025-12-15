package kr.ac.uc.letseat.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    var menuId: String = "",
    var name: String = "",
    var price: Int = 0,
    var quantity: Int = 1,
    var imageUrl: String = "",
    var restaurantId: String = ""   // ← REQUIRED
) : Parcelable
