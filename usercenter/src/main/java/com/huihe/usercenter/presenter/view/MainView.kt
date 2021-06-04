package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.SetPushRep
import com.kotlin.base.presenter.view.BaseView

interface MainView :BaseView {
    fun onPushSuccess(t: SetPushRep?)
}