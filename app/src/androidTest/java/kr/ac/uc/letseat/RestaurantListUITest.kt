package kr.ac.uc.letseat

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import kr.ac.uc.letseat.ui.HotelListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantListUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HotelListActivity::class.java)

    @Test
    fun restaurantList_isVisible() {
        // ✅ Check RecyclerView for restaurant list is visible
        onView(withId(R.id.hotelRecyclerView))
            .check(matches(isDisplayed()))
    }
}
