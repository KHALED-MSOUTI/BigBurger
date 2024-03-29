package com.khmsouti.bigburger

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.khmsouti.bigburger.activity.MainActivity
import com.khmsouti.bigburger.testing.RecipeIdlingResource
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


/**
 * Instrumented test, which will execute on an Android device.
 *
 * If connection status is offline this test will go throw IllegalArgumentException error.
 * If devise is connected this test mainly will test the RecyclerView and under the hood will test
 * Retrofit2 call
 */
@RunWith(AndroidJUnit4::class)
class ViewTest {
    @get:Rule
    var mainActivityActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java
    )


    @Test
    fun receiveAndDisplayTest() {
        waitForApp(DateUtils.SECOND_IN_MILLIS * 5)
    }

    private fun waitForApp(waitingTime: Long) {
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS)

        val idlingResource = RecipeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.mainRecyclerView)).check(RecyclerViewItemCountAssertion(13))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    internal class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) throw noViewFoundException

            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter!!.itemCount, `is`(expectedCount))
        }
    }


}