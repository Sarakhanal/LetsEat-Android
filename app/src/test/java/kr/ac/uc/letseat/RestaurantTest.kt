package kr.ac.uc.letseat

import kr.ac.uc.letseat.models.Restaurant
import org.junit.Assert.*
import org.junit.Test

class RestaurantTest {

    @Test
    fun restaurant_hasNameAndLocation() {
        val restaurant = Restaurant(name = "Bajeko Sekuwa", location = "Nepalgunj")
        assertEquals("Bajeko Sekuwa", restaurant.name)
        assertEquals("Nepalgunj", restaurant.location)
    }
}
