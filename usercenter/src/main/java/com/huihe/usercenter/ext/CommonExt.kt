package com.huihe.usercenter.ext

import android.widget.Button
import com.huihe.usercenter.ui.widget.EditInputView
import com.kotlin.base.widgets.DefaultTextWatcher

/*
    扩展Button可用性
 */
fun Button.enable(et: EditInputView, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}
