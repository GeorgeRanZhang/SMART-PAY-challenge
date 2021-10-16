package com.smartpay.application

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.smartpay.application.moto.transaction.MotoTransactionActivity.Companion.IS_SINGLE_MOTO_TYPE
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    var mainActivity: IntentsTestRule<MainActivity> =
        IntentsTestRule(MainActivity::class.java)
    lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
        val intent = Intent(mContext, MainActivity::class.java)
        mainActivity.launchActivity(intent)
    }

    @Test
    fun checkUI() {
        //Check if MainActivity open correctly
        Espresso.onView(ViewMatchers.withText(R.string.single_moto))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.recurring_moto))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkClick() {
        //Check if MainActivity open correctly
        Espresso.onView(ViewMatchers.withText(R.string.single_moto)).perform(ViewActions.click())
        Intents.intended(
            IntentMatchers.hasExtras(
                AllOf.allOf(
                    BundleMatchers.hasEntry(IsEqual.equalTo(IS_SINGLE_MOTO_TYPE), IsEqual.equalTo(true)),
                )
            )
        )
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withText(R.string.moto_transaction)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.single_moto)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withText(R.string.recurring_moto)).perform(ViewActions.click())
        Intents.intended(
            IntentMatchers.hasExtras(
                AllOf.allOf(
                    BundleMatchers.hasEntry(IsEqual.equalTo(IS_SINGLE_MOTO_TYPE), IsEqual.equalTo(false)),
                )
            )
        )
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withText(R.string.moto_transaction)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.recurring_moto)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}