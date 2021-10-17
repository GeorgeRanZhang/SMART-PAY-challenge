package com.smartpay.application

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.smartpay.application.common.Constants
import com.smartpay.application.moto.transaction.MotoTransactionViewModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MotoTransactionViewModelTest {

    private lateinit var addEditTaskViewModel: MotoTransactionViewModel
    lateinit var mContext: Application

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setupViewModel() {
        mContext = ApplicationProvider.getApplicationContext()
        var bundle = Bundle()
        bundle.putBoolean(Constants.IS_SINGLE_MOTO_TYPE, true)
        addEditTaskViewModel = MotoTransactionViewModel(ApplicationProvider.getApplicationContext(),bundle)
    }

    @Test
    fun isSingleText() {
        Assert.assertEquals(addEditTaskViewModel.getIsSingleMoto(), true)
    }

}