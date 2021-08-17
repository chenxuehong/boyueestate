package com.huihe.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView

interface SignView:BaseView {
    fun onGetUploadTokenResult(t: String?)
    fun onSignSuccess()
}
