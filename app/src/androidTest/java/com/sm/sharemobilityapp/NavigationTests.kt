package com.sm.sharemobilityapp

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.sm.sharemobilityapp.ui.StartFragment
import okhttp3.internal.wait
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationTests {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Test
    fun navigate_to_filter_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val starFragment = launchFragmentInContainer<StartFragment>(themeResId = R.style.Theme_ShareMobilityApp)

        starFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.filter_button))
            .perform(ViewActions.click())

        assertEquals(navController.currentDestination?.id, R.id.fragment_filter)
    }

    @Test
    fun navigate_to_map_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val starFragment = launchFragmentInContainer<StartFragment>(themeResId = R.style.Theme_ShareMobilityApp)

        starFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.map_button))
            .perform(ViewActions.click())

        assertEquals(navController.currentDestination?.id, R.id.fragment_map)
    }

    @Test
    fun navigate_to_car_rental_details_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val starFragment = launchFragmentInContainer<StartFragment>(themeResId = R.style.Theme_ShareMobilityApp)

        starFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))


        assertEquals(navController.currentDestination?.id, R.id.fragment_car_rental_details)
    }

    @Test
    fun navigate_to_login_nav_component() {
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.login), ViewMatchers.withContentDescription("Login"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val button = onView(
            Matchers.allOf(
                withId(R.id.login_login_button), ViewMatchers.withText("INLOGGEN"),
                ViewMatchers.withParent(ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))),
                ViewMatchers.isDisplayed()
            )
        )
        button.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigate_to_register_nav_component() {
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.login), ViewMatchers.withContentDescription("Login"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val materialTextView = onView(
            Matchers.allOf(
                withId(R.id.not_yet_account_button), ViewMatchers.withText("Nog geen account?"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialTextView.perform(ViewActions.scrollTo(), click())

        val textView = onView(
            Matchers.allOf(
                withId(R.id.account_reason_text),
                ViewMatchers.withText("Waar wilt u de applicatie voornamelijk voor gebruiken?"),
                ViewMatchers.withParent(ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Waar wilt u de applicatie voornamelijk voor gebruiken?")))
    }

    @Test
    fun navigate_to_rent_nav_component() {
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.login), ViewMatchers.withContentDescription("Login"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
            Matchers.allOf(
                withId(R.id.home), ViewMatchers.withContentDescription("Home"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val appCompatImageView = onView(
            Matchers.allOf(
                withId(R.id.image_slider),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("com.google.android.material.card.MaterialCardView")),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.rent_button), ViewMatchers.withText("Huren"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    17
                )
            )
        )
        materialButton.perform(ViewActions.scrollTo(), click())

        val textView = onView(
            Matchers.allOf(
                withId(R.id.rent_terms_and_conditions_title),
                ViewMatchers.withText("Algemene voorwaarden"),
                ViewMatchers.withParent(ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Algemene voorwaarden")))
    }



    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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