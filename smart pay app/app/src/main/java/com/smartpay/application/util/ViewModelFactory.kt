package com.smartpay.application.util

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smartpay.application.moto.transaction.MotoTransactionViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val context: Application,
    private val defaultArgs: Bundle? = null
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>)= with(modelClass)  {
        when{
            isAssignableFrom(MotoTransactionViewModel::class.java) ->
                MotoTransactionViewModel(context, defaultArgs)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}