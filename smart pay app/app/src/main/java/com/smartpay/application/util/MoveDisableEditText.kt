package com.smartpay.application.util

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText

class MoveDisableEditText : AppCompatEditText {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
    }

    public override fun onSelectionChanged(start: Int, end: Int) {
        val text: CharSequence? = text
        if (text != null) {
            if (start != text.length || end != text.length) {
                setSelection(text.length, text.length)
                return
            }
        }
        super.onSelectionChanged(start, end)
    }
}