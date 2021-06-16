package com.huihe.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.provider.data.protocol.ServerVersionInfo

interface AboutUsView : BaseView{
    fun onServerAppVersion(serverAppVersion: ServerVersionInfo?)
}
