package com.smartpay.application.util

import android.app.Application
import android.os.Bundle

object ViewModelInjector {


    fun provideViewModelFactory(
        context: Application,
        defaultArgs: Bundle? = null
    ): ViewModelFactory {
        return ViewModelFactory(context, defaultArgs)
    }
}