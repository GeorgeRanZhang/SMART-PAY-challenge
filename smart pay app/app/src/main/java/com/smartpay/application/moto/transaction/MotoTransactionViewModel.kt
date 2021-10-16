package com.smartpay.application.moto.transaction

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartpay.application.moto.transaction.MotoTransactionActivity.Companion.IS_SINGLE_MOTO_TYPE

class MotoTransactionViewModel (
    private val context: Application,
    private val defaultArgs: Bundle?
) : ViewModel() {

    private val expireDate = MutableLiveData<String>("")

    private val cvv = MutableLiveData<String>("")

    private val pan = MutableLiveData<String>("")

    private val transaction = MutableLiveData<String>("")

    private val _isSingleMoto = MutableLiveData<Boolean>(true)
    val isSingleMoto: LiveData<Boolean> = _isSingleMoto

    private val isStoredOnFile = MutableLiveData<Boolean>(true)

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

    fun setIsSingleMoto(isSingleMoto: Boolean){
        _isSingleMoto.value = isSingleMoto
    }

    fun getIsSingleMoto(): Boolean {
        return _isSingleMoto.value ?: true
    }

    fun setIsStoredOnFile(isStored: Boolean){
        isStoredOnFile.value = isStored
    }

    fun getIsStoredOnFile(): Boolean {
        return isStoredOnFile.value ?: true
    }

    init {
        if (defaultArgs != null) {
            if (defaultArgs.getSerializable(IS_SINGLE_MOTO_TYPE) != null) {
                _isSingleMoto.value = defaultArgs.getSerializable(IS_SINGLE_MOTO_TYPE) as Boolean?
            }
        }
    }
}