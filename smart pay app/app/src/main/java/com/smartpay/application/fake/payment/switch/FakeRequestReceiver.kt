package com.smartpay.application.fake.payment.switch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.smartpay.application.common.Constants
import com.smartpay.application.common.Constants.Companion.TLV_REQUEST_PROCESS
import com.smartpay.application.common.Constants.Companion.TLV_TAG_CREDENTIAL_DESCRIPTOR
import com.smartpay.application.common.Constants.Companion.TLV_TAG_EXPIRY_DATE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_LOCAL_TIME
import com.smartpay.application.common.Constants.Companion.TLV_TAG_PAN
import com.smartpay.application.common.Constants.Companion.TLV_TAG_PROCESSING_CODE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_SYSTEM_TRACK_NUMBER
import com.smartpay.application.common.Constants.Companion.TLV_TAG_TRANSACTION
import com.smartpay.application.data.Tlv
import com.smartpay.application.util.TLVUtils.initTlvListByHexString
import com.smartpay.application.common.Constants.Companion.TLV_RESPONSE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_LOCAL_DATE
import com.smartpay.application.common.Constants.Companion.TLV_TAG_RESPONSE_CODE
import com.smartpay.application.common.Constants.Companion.TLV_VALUE_RESPONSE_CODE_APPROVED
import com.smartpay.application.util.TLVUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FakeRequestReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("FakeRequestReceiver:", "received messages" + Thread.currentThread().name)
        if (intent.action != null && intent.action == TLV_REQUEST_PROCESS) {
            val data = intent.getStringExtra(Constants.REQUEST_DATA) ?: ""
            if (data.isNotEmpty()) {
                Log.d("FakeRequestReceiver:", "start to handling data $data")
                initTlvListByHexString(data).let { data ->
                    val tlvList: MutableList<Tlv> = ArrayList()
                    for (element in data) {
                        if (element.tag == TLV_TAG_PAN || element.tag == TLV_TAG_PROCESSING_CODE || element.tag == TLV_TAG_TRANSACTION || element.tag == TLV_TAG_SYSTEM_TRACK_NUMBER || element.tag == TLV_TAG_EXPIRY_DATE || element.tag == TLV_TAG_CREDENTIAL_DESCRIPTOR) {
                            tlvList.add(element)
                        }
                    }
                    val currentTime: String =
                        SimpleDateFormat("HHmmss", Locale.getDefault()).format(Date())
                    tlvList.add(Tlv(TLV_TAG_LOCAL_TIME, currentTime.length, currentTime))
                    val currentDate: String =
                        SimpleDateFormat("yyMM", Locale.getDefault()).format(Date())
                    tlvList.add(Tlv(TLV_TAG_LOCAL_DATE, currentDate.length, currentDate))
                    tlvList.add(
                        Tlv(
                            TLV_TAG_RESPONSE_CODE,
                            TLV_VALUE_RESPONSE_CODE_APPROVED.length,
                            TLV_VALUE_RESPONSE_CODE_APPROVED
                        )
                    )
                    val result = TLVUtils.initHexStringByTLV(tlvList)
                    Intent(TLV_RESPONSE).let {
                        it.putExtra(Constants.RESPONSE_DATA,result)
                        LocalBroadcastManager.getInstance(context).sendBroadcast(it);
                    }
                }
            }
        }
    }
}