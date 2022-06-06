package com.huihe.usercenter.presenter.view

import com.huihe.boyueentities.protocol.user.SetPushRep
import com.kotlin.base.presenter.view.BaseView
import com.huihe.boyueentities.protocol.common.ServerVersionInfo

interface MainView :BaseView {
    fun onPushSuccess(t: SetPushRep?)
    fun onServerAppVersion(t: ServerVersionInfo?)
}