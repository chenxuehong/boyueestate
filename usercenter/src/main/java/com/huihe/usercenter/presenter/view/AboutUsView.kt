package com.huihe.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.huihe.boyueentities.protocol.common.ServerVersionInfo

interface AboutUsView : BaseView{
    fun onServerAppVersion(serverAppVersion: ServerVersionInfo?)
}
