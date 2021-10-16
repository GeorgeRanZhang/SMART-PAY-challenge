package com.smartpay.application.moto.transaction

import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.smartpay.application.BaseActivity
import com.smartpay.application.R
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import kotlin.math.round
import com.smartpay.application.util.ViewModelInjector
import java.lang.StringBuilder

class MotoTransactionActivity : BaseActivity() {
    private lateinit var etTransaction: EditText
    private lateinit var etPan: EditText
    private lateinit var etExpireDate: EditText
    private lateinit var etCVV: EditText
    private lateinit var tvContinue: TextView
    private lateinit var tvSelectType1: TextView
    private lateinit var tvSelectType2: TextView
    private lateinit var llYes: LinearLayout
    private lateinit var ivYes: ImageView
    private lateinit var llNo: LinearLayout
    private lateinit var ivNo: ImageView

    val regexExcludeNumber = Regex("[^0-9*]")
    val regexIncludeNumber = Regex("[0-9]")
    private val mViewModel: MotoTransactionViewModel by viewModels {
        ViewModelInjector.provideViewModelFactory(this.application, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moto_transaction)

        setToolbar(getString(R.string.moto_transaction), true)
        initView()
        initViewModel()
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
        tvContinue = findViewById(R.id.tv_continue)
        tvSelectType1 = findViewById(R.id.tv_select_type1)
        tvSelectType2 = findViewById(R.id.tv_select_type2)
        llYes =  findViewById(R.id.ll_yes)
        llNo = findViewById(R.id.ll_no)
        ivYes = findViewById(R.id.iv_yes)
        ivNo = findViewById(R.id.iv_no)
        tvSelectType1.setOnClickListener {
            tvSelectType2.isVisible = !tvSelectType2.isVisible
        }
        tvSelectType2.setOnClickListener{
            tvSelectType2.isVisible = false
            mViewModel.setIsSingleMoto(!mViewModel.getIsSingleMoto())
        }
        etExpireDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                etExpireDate.text.let {
                    if (it.length == 2 && it.toString().toInt() > 12)
                        etExpireDate.setText(R.string.month)
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
                if (!s.toString().contains(getString(R.string.dollar)) && s.toString()
                        .isNotEmpty()
                ) {
                    etTransaction.setText(
                        StringBuilder(s).insert(0, getString(R.string.dollar)).toString()
                    )
                }
                etTransaction.setSelection(etTransaction.text.length)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        etTransaction.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                etTransaction.text.let { transaction ->
                    if (transaction.toString().contains(getString(R.string.dollar))) {
                        transaction.substring(1, transaction.length).toDouble().let { it ->
                            var result: Double = String.format("%.2f", it).toDouble()
                            if (result > 9999999999.99) result = 9999999999.99
                            etTransaction.setText(
                                String.format(
                                    getString(R.string.dollar_two_decimal),
                                    result
                                )
                            )
                            mViewModel.setTransaction(
                                regexExcludeNumber.replace(
                                    String.format(
                                        "%.2f",
                                        result
                                    ), ""
                                )
                            )
                        }
                    }
                }
                checkShouldHideKeyboard(v)
            } else {
                etTransaction.text.toString().let {
                    if (it.contains(".00")) {
                        etTransaction.setText(it.substring(0, it.length - 3))
                    }
                }

            }
        }
        etExpireDate.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                checkShouldHideKeyboard(v)
            }
        }
        etCVV.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                checkShouldHideKeyboard(v)
            }
        }
        etPan.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                checkShouldHideKeyboard(v)
            }
        }
    }

    private fun checkShouldHideKeyboard(view: View){
        if(!etExpireDate.hasFocus() && !etCVV.hasFocus() && !etPan.hasFocus() && !etTransaction.hasFocus()){
            hideKeyboard(view)
        }
    }

    private fun initViewModel() {
        mViewModel.apply {
            isSingleMoto.observe(this@MotoTransactionActivity, Observer {
                changeThemeByMOTOType(it)
            })
        }
    }

    private fun changeThemeByMOTOType(isSingleMoto: Boolean) {
        val textColor: Int
        if (isSingleMoto) {
            textColor = R.color.colorBlack
            llYes.background =
                ContextCompat.getDrawable(this, R.drawable.white_bg_stoke_15_corner)
            llNo.background =
                ContextCompat.getDrawable(this, R.drawable.white_bg_4_corner)
            ivYes.isVisible = true
            ivNo.isVisible = false
            mViewModel.setIsStoredOnFile(true)
            tvContinue.let{
                it.background = ContextCompat.getDrawable(this, R.drawable.gradient_bg_yellow_green)
                it.setTextColor(ContextCompat.getColor(this,R.color.colorWhite))
            }
            tvSelectType1.let{
                it.background = ContextCompat.getDrawable(this, R.drawable.single_moto_bg)
                it.setText(R.string.single_moto)
            }
            tvSelectType2.setText(R.string.recurring_moto)
        } else {
            textColor = R.color.colorRecurringMoto
            tvContinue.let{
                it.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite))
                it.setTextColor(ContextCompat.getColor(this,textColor))
            }
            tvSelectType1.let{
                it.background = ContextCompat.getDrawable(this, R.drawable.recurring_moto_bg)
                it.setText(R.string.recurring_moto)
            }
            tvSelectType2.setText(R.string.single_moto)
        }
        setHeaderColor(textColor)
        findViewById<LinearLayout>(R.id.ll_stored_credential).isVisible = !isSingleMoto
        ContextCompat.getColor(this,textColor).let {
            findViewById<TextView>(R.id.tv_pan_title).setTextColor(it)
            findViewById<TextView>(R.id.tv_expire_title).setTextColor(it)
            findViewById<TextView>(R.id.tv_cvv_title).setTextColor(it)
            findViewById<TextView>(R.id.tv_select_type_title).setTextColor(it)
            etTransaction.setTextColor(it)
        }

    }

    companion object {
        const val IS_SINGLE_MOTO_TYPE = "IS_SINGLE_MOTO_TYPE"
    }
}