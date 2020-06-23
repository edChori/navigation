package com.example.android.codelabs.navigation


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun navigationTest() {
        val textView = onView(
                allOf(withId(R.id.text), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.my_nav_host_fragment),
                                                0)),
                                0),
                        isDisplayed()))
        textView.check(matches(withText("Home")))

        val materialButton = onView(
                allOf(withId(R.id.navigate_destination_button), withText("Navigate to destination"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.my_nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()))
        materialButton.perform(click())

        val materialButton2 = onView(
                allOf(withId(R.id.next_button), withText("Navigate next step"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()))
        materialButton2.perform(click())

        val materialButton3 = onView(
                allOf(withId(R.id.next_button), withText("Finish Flow"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()))
        materialButton3.perform(click())

        val textView2 = onView(
                allOf(withId(R.id.text), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.my_nav_host_fragment),
                                                0)),
                                0),
                        isDisplayed()))
        textView2.check(matches(withText("Home")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
