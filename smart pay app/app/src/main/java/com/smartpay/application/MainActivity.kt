package com.smartpay.application

import android.os.Bundle
import android.content.Intent
import android.view.View
import com.smartpay.application.moto.transaction.MotoTransactionActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(getString(R.string.welcome), false)
    }

    fun startSingleMotoTransaction(view: View) {
        val intent = Intent(this, MotoTransactionActivity::class.java)
        if (view.id == R.id.tv_single_moto) intent.putExtra(
            MotoTransactionActivity.MOTO_TYPE,
            getString(R.string.single_moto)
        ) else intent.putExtra(
            MotoTransactionActivity.MOTO_TYPE,
            getString(R.string.recurring_moto)
        )
        startActivity(intent)
    }
}