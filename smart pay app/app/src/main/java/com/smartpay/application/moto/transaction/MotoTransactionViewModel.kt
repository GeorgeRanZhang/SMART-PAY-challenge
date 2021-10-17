package com.smartpay.application.moto.transaction

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartpay.application.data.Tlv
import com.smartpay.application.common.Constants.Companion.IS_SINGLE_MOTO_TYPE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_PAN
import com.smartpay.application.util.TLVUtils.initHexStringByTLV
//import com.smartpay.application.util.TlvUtils.builderTlvList
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.smartpay.application.common.Constants
import com.smartpay.application.common.Constants.Companion.ETX
import com.smartpay.application.common.Constants.Companion.REQUEST_DATA
import com.smartpay.application.common.Constants.Companion.STX
import com.smartpay.application.common.Constants.Companion.TLV_REQUEST_PROCESS
import com.smartpay.application.common.Constants.Companion.TLV_TAG_CREDENTIAL_DESCRIPTOR

import com.smartpay.application.common.Constants.Companion.TLV_TAG_CVV
import com.smartpay.application.common.Constants.Companion.TLV_TAG_EXPIRY_DATE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_PROCESSING_CODE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_SERVICE_ENTRY_MODE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_SYSTEM_TRACK_NUMBER
import com.smartpay.application.common.Constants.Companion.TLV_TAG_TRANSACTION
import com.smartpay.application.common.Constants.Companion.TLV_VALUE_CVV_NOT_PRESENT
import com.smartpay.application.common.Constants.Companion.TLV_VALUE_MANUAL_ENTRY_WITH_CVV
import com.smartpay.application.common.Constants.Companion.TLV_VALUE_MANUAL_ENTRY_WITHOUT_CVV
import com.smartpay.application.common.Constants.Companion.TLV_VALUE_NEW_CARD
import com.smartpay.application.common.Constants.Companion.TLV_VALUE_PROCESSING_CODE
import com.smartpay.application.common.Constants.Companion.TLV_VALUE_STORED_CARD


class MotoTransactionViewModel(
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

    fun getExpireDate(): String {
        return expireDate.value ?: ""
    }

    fun setCvv(userInput: String) {
        cvv.value = userInput
    }

    fun getCvv(): String {
        return cvv.value ?: ""
    }

    fun setPan(userInput: String) {
        pan.value = userInput
    }

    fun getPan(): String {
        return pan.value ?: ""
    }

    fun setTransaction(userInput: String) {
        transaction.value = userInput
    }

    fun getTransaction(): String {
        return transaction.value ?: ""
    }

    fun setIsSingleMoto(isSingleMoto: Boolean) {
        _isSingleMoto.value = isSingleMoto
    }

    fun getIsSingleMoto(): Boolean {
        return _isSingleMoto.value ?: true
    }

    fun setIsStoredOnFile(isStored: Boolean) {
        isStoredOnFile.value = isStored
    }

    fun getIsStoredOnFile(): Boolean {
        return isStoredOnFile.value ?: true
    }

    fun toastError(errorText: String) {
        Toast.makeText(
            context,
            errorText,
            Toast.LENGTH_SHORT
        ).let { toast ->
            toast.show()
        }
    }

    fun buildTLVList() {

        val tlvList: MutableList<Tlv> = ArrayList()
        //Pan
        getPan().let {
            if (it.isNotEmpty()) tlvList.add(Tlv(TLV_TAG_PAN, it.length, it))
            else {
                toastError("Please enter account number")
                return
            }
        }
        //Processing code
        tlvList.add(
            Tlv(
                TLV_TAG_PROCESSING_CODE,
                TLV_VALUE_PROCESSING_CODE.length,
                TLV_VALUE_PROCESSING_CODE
            )
        )
        //Transaction
        getTransaction().let {
            if (it.isEmpty() || it == "0") {
                toastError("Please enter money")
                return
            }
            tlvList.add(Tlv(TLV_TAG_TRANSACTION, it.length, it))
        }
        //System Trace Audit
        (0..999999).random().let {
            tlvList.add(Tlv(TLV_TAG_SYSTEM_TRACK_NUMBER, 6, String.format("%06d", it)))
        }
        //Expire Date
        getExpireDate().let {
            if (it.length == 4) tlvList.add(Tlv(TLV_TAG_EXPIRY_DATE, it.length, it))
            else {
                toastError("Please enter Expire Date")
                return
            }
        }
        //Point of Service Entry mode && CVV

        getCvv().let {
            when {
                (it.length >= 3) -> {
                    tlvList.add(Tlv(TLV_TAG_SERVICE_ENTRY_MODE, 3, TLV_VALUE_MANUAL_ENTRY_WITH_CVV))
                    tlvList.add(Tlv(TLV_TAG_CVV, it.length, it))
                }
                it.isNotEmpty() -> {
                    toastError("Please enter correct cvv")
                    return
                }
                else -> {
                    tlvList.add(
                        Tlv(
                            TLV_TAG_SERVICE_ENTRY_MODE,
                            3,
                            TLV_VALUE_MANUAL_ENTRY_WITHOUT_CVV
                        )
                    )
                    tlvList.add(Tlv(TLV_TAG_CVV, 3, TLV_VALUE_CVV_NOT_PRESENT))
                    //TODO: Need to rework different case of cvv present
                }
            }
        }
        getIsSingleMoto().let {
            if (!it) {
                val insert = if (getIsStoredOnFile()) "" + STX + TLV_VALUE_STORED_CARD + ETX
                else "" + STX + TLV_VALUE_NEW_CARD + ETX
                tlvList.add(Tlv(TLV_TAG_CREDENTIAL_DESCRIPTOR, insert.length, insert))
            }
        }
        val result = initHexStringByTLV(tlvList)
        val intent = Intent(TLV_REQUEST_PROCESS)
        intent.putExtra(REQUEST_DATA,result)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    init {
        if (defaultArgs != null) {
            if (defaultArgs.getSerializable(IS_SINGLE_MOTO_TYPE) != null) {
                _isSingleMoto.value = defaultArgs.getSerializable(IS_SINGLE_MOTO_TYPE) as Boolean?
            }
        }
    }
}