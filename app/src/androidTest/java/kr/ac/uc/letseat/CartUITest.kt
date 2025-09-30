package kr.ac.uc.letseat

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import kr.ac.uc.letseat.ui.CartActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(CartActivity::class.java)

    @Test
    fun cartRecyclerView_isVisible() {
        // ✅ Check RecyclerView in cart is visible
        onView(withId(R.id.cartRecyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkoutButton_isClickable() {
        // ✅ Check "Proceed to Checkout" button is visible and clickable
        onView(withId(R.id.btnCheckout))
            .check(matches(isDisplayed()))
            .perform(click())
    }
}
