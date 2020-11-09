package com.example.catfacts.catfact

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.catfacts.AndroidTestActivity
import com.example.catfacts.AndroidTestViewModel
import com.example.catfacts.R
import com.example.catfacts.TestDependencyInjection
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatFactFragmentTest {

    private val viewModel: AndroidTestViewModel = TestDependencyInjection.catFactViewModel

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(AndroidTestActivity::class.java)

    private val resources: Resources
        get() = activityRule.activity.resources

    private val mockwebserver = MockWebServer()

    @Before
    fun before() {
        val fragment = CatFactFragment()
        activityRule.runOnUiThread {
            activityRule.activity.supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commitNow()
        }
    }

    @Test
    fun initialState() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        assertEquals(viewModel.observableState.value, initialState)
    }

    @Test
    fun getFactButtonClickedAction() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.getFactButton)).perform(click())

        viewModel.testAction.assertValues(CatFactAction.GetFactButtonClicked)
    }

    @Test
    fun loadingState() {
        val state = CatFactState(activity = true)
        viewModel.testState.onNext(state)

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.cat_fact_press_the_button))))
        onView(withId(R.id.getFactButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun errorState() {
        val state = CatFactState(displayError = true)
        viewModel.testState.onNext(state)

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.cat_fact_press_the_button))))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
        onView(withId(R.id.errorView)).check(matches(isDisplayed()))
    }

    @Test
    fun factLoadedState() {
        val fact = "Even cat facts need to be tested"
        val state = CatFactState(fact = fact)
        viewModel.testState.onNext(state)

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(fact)))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
    }
}
