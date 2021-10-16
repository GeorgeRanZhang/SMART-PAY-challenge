package com.smartpay.application

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smartpay.application.moto.transaction.MotoTransactionActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MotoTransactionActivityTest {
    var motoTransactionActivity: IntentsTestRule<MotoTransactionActivity> =
        IntentsTestRule(MotoTransactionActivity::class.java)
    lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
        val intent = Intent(mContext, MotoTransactionActivity::class.java).apply {
            putExtra(MotoTransactionActivity.IS_SINGLE_MOTO_TYPE, true)
        }

        motoTransactionActivity.launchActivity(intent)
    }

    @Test
    fun checkUI() {
        //Check if MainActivity open correctly
        Espresso.onView(ViewMatchers.withText(R.string.moto_transaction))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.enter_card_pan))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.expire_date))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.cvv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.select_moto))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText(R.string.single_moto))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withHint(R.string.no_money))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withHint(R.string.card_pan_hint))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withHint(R.string.expire_date_hint))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withHint(R.string.cvv_hint))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun changeMotoTypeTest() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_select_type2))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.ll_stored_credential))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.tv_select_type1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.tv_select_type2))
            .check(matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.ll_stored_credential))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.moto_transaction))
            .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.colorRecurringMoto)))
        Espresso.onView(ViewMatchers.withText(R.string.enter_card_pan))
            .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.colorRecurringMoto)))
        Espresso.onView(ViewMatchers.withText(R.string.expire_date))
            .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.colorRecurringMoto)))
        Espresso.onView(ViewMatchers.withText(R.string.cvv))
            .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.colorRecurringMoto)))
        Espresso.onView(ViewMatchers.withText(R.string.select_moto))
            .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.colorRecurringMoto)))
    }

    @Test
    fun changeIsStoredTest() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_select_type1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.tv_select_type2))
            .check(matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.ll_stored_credential))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.iv_yes))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.iv_no))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.ll_no)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.iv_no))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.iv_yes))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

}