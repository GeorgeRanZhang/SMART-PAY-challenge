package com.smartpay.application

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.smartpay.application.R
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import android.app.Activity
import android.view.inputmethod.InputMethodManager


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

    fun setHeaderColor(color: Int) {
        findViewById<TextView>(R.id.tv_header).let {
            it.setTextColor(ContextCompat.getColor(this, color))
        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}