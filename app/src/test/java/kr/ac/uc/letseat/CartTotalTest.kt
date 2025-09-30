package kr.ac.uc.letseat

import kr.ac.uc.letseat.models.MenuItem
import org.junit.Assert.*
import org.junit.Test

class CartTotalTest {

    @Test
    fun cartTotal_calculatesCorrectly() {
        val cart = listOf(
            MenuItem(name = "Sekuwa", price = 250.0, quantity = 2),
            MenuItem(name = "Momo", price = 120.0, quantity = 1)
        )
        val total = cart.sumOf { it.price * it.quantity }
        assertEquals(620.0, total, 0.0)
    }
}
