package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.SetPushRep
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.provider.data.protocol.ServerVersionInfo

interface MainView :BaseView {
    fun onPushSuccess(t: SetPushRep?)
    fun onServerAppVersion(t: ServerVersionInfo?)
}