package com.smartpay.application.moto.transaction

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MotoTransactionViewModel (
    private val context: Application,
    private val defaultArgs: Bundle?
) : ViewModel() {
    fun saysomething(){
        Log.d("hi","my friend")
    }

    private val expireDate = MutableLiveData<String>("")
    private val cvv = MutableLiveData<String>("")
    private val pan = MutableLiveData<String>("")
    private val transaction = MutableLiveData<String>("")
    fun setExpireDate(userInput: String) {
        expireDate.value = userInput
    }

    fun setCvv(userInput: String) {
        cvv.value = userInput
    }

    fun setPan(userInput: String) {
        pan.value = userInput
    }

    fun getPan(): String {
        return pan.value ?: ""
    }

    fun setTransaction(userInput: String){
        transaction.value = userInput
    }

    fun getTransaction(): String {
        return transaction.value ?: ""
    }
}