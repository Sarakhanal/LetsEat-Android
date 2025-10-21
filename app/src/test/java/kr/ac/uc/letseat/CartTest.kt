package kr.ac.uc.letseat

import kr.ac.uc.letseat.models.MenuItem
import org.junit.Assert.*
import org.junit.Test

class CartTest {

    @Test
    fun addingToCart_updatesQuantity() {
        val item = MenuItem(name = "Sekuwa", price = 250.0, quantity = 0)
        item.quantity += 1
        assertEquals(1, item.quantity)
    }
}
