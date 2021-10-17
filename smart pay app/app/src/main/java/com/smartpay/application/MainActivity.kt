package com.smartpay.application

import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.smartpay.application.common.Constants
import com.smartpay.application.common.Constants.Companion.TLV_REQUEST_PROCESS
import com.smartpay.application.fake.payment.switch.FakeRequestReceiver
import com.smartpay.application.moto.transaction.MotoTransactionActivity
import java.security.AccessController.getContext

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(getString(R.string.welcome), false)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(FakeRequestReceiver(), IntentFilter(TLV_REQUEST_PROCESS));
    }

    fun startMotoTransaction(view: View) {
        val intent = Intent(this, MotoTransactionActivity::class.java)
        if (view.id == R.id.tv_single_moto) intent.putExtra(
            Constants.IS_SINGLE_MOTO_TYPE,
            true
        ) else intent.putExtra(
            Constants.IS_SINGLE_MOTO_TYPE,
            false
        )
        startActivity(intent)
    }
}