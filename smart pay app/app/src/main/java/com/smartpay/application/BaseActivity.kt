package com.smartpay.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.smartpay.application.R
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setToolbar(title: String?, isBackVisible: Boolean) {
        val mToolbar = findViewById<Toolbar>(R.id.tb_header)
        setSupportActionBar(mToolbar)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(isBackVisible)
        mToolbar.visibility = View.VISIBLE
        val tvHeader = findViewById<TextView>(R.id.tv_header)
        tvHeader.text = title
    }
}