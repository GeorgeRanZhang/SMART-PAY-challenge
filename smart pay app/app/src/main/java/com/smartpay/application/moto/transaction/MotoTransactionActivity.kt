package com.smartpay.application.moto.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.smartpay.application.BaseActivity
import com.smartpay.application.R
import androidx.activity.viewModels
import androidx.core.view.isVisible
import kotlin.math.round
import com.smartpay.application.util.ViewModelInjector
import java.lang.StringBuilder

class MotoTransactionActivity : BaseActivity() {
    private lateinit var etTransaction: EditText
    private lateinit var etPan: EditText
    private lateinit var etExpireDate: EditText
    private lateinit var etCVV: EditText
    val regexExcludeNumber = Regex("[^0-9*]")
    val regexIncludeNumber = Regex("[0-9]")
    private val mViewModel: MotoTransactionViewModel by viewModels {
        ViewModelInjector.provideViewModelFactory(this.application, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moto_transaction)
        setToolbar(getString(R.string.moto_transaction), true)
        mViewModel.saysomething()
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        etExpireDate = findViewById(R.id.et_expire_date)
        etCVV = findViewById(R.id.et_cvv)
        etPan = findViewById(R.id.et_pan)
        etTransaction = findViewById(R.id.et_transaction)
        etExpireDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                etExpireDate.text.let {
                    if (it.length == 2 && it.toString()
                            .toInt() > 12
                    ) etExpireDate.setText(R.string.month)
                    if (it.length == 3 && !it.contains("/")) {
                        etExpireDate.setText(
                            StringBuilder(it.toString()).insert(it.length - 1, "/").toString()
                        )
                    }
                    etExpireDate.setSelection(etExpireDate.text.length)
                }
            }

            override fun afterTextChanged(s: Editable) {
                regexExcludeNumber.replace(s.toString(), "").let {
                    mViewModel.setExpireDate(it)
                }
            }
        })

        etCVV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                mViewModel.setExpireDate(s.toString())
            }
        })

        etPan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var storedPan = mViewModel.getPan()
                if (s.isEmpty()) {
                    mViewModel.setPan("")
                    return
                }
                regexExcludeNumber.replace(s.toString(), "").let {
                    if (storedPan.length != it.length) {
                        if (it.length < storedPan.length && before == 1) {
                            storedPan = storedPan.substring(0, storedPan.length - 1)
                        }
                        if (s.length > storedPan.length && count == 1) {
                            storedPan =
                                StringBuilder(storedPan).insert(storedPan.length, s[s.lastIndex])
                                    .toString()
                        }
                        mViewModel.setPan(storedPan)
                        var formattedText = storedPan
                        if (formattedText.length > 8) {
                            formattedText = formattedText.substring(0, 4) +
                                    formattedText.substring(4, formattedText.length - 4)
                                        .replace(regexIncludeNumber, "*") +
                                    formattedText.substring(formattedText.length - 4)
                        }
                        formattedText = formattedText.replace(" ", "").chunked(4).joinToString(" ")
                        if (formattedText[formattedText.lastIndex] == ' ')
                            formattedText = formattedText.substring(0, formattedText.length - 1)
                        etPan.setText(formattedText)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })


        etTransaction.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString() == getString(R.string.dollar)) {
                    mViewModel.setTransaction("")
                    etTransaction.setText("")
                    return
                }
                if (!s.toString().contains(getString(R.string.dollar)) && s.toString().isNotEmpty()){
                    etTransaction.setText(StringBuilder(s).insert(0, getString(R.string.dollar)).toString())
                }
                etTransaction.setSelection(etTransaction.text.length)
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        etTransaction.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                etTransaction.text.let{ transaction ->
                    if(transaction.toString().contains(getString(R.string.dollar))){
                        transaction.substring(1,transaction.length).toDouble().let{ it ->
                            var result:Double = String.format("%.2f", it).toDouble()
                            if (result > 9999999999.99) result = 9999999999.99
                            etTransaction.setText(String.format(getString(R.string.dollar_two_decimal), result))
                            mViewModel.setTransaction(regexExcludeNumber.replace( String.format("%.2f", result), ""))
                        }
                    }
                }
            } else {
                etTransaction.text.toString().let{
                    if(it.contains(".00")){
                        etTransaction.setText(it.substring(0,it.length-3))
                    }
                }

            }
        }
    }


    companion object {
        const val MOTO_TYPE = "MOTO_TYPE"
    }
}