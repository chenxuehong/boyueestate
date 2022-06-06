package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.HaveKeyUserRep
import com.kotlin.base.presenter.view.BaseView

interface HaveKeyUserView:BaseView {
    fun onGetUploadTokenResult(t: String?)
    fun onPutHouseKeySuccess(t: HaveKeyUserRep?)
}