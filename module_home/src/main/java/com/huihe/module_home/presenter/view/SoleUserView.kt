package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.SoleUserRep
import com.kotlin.base.presenter.view.BaseView

interface SoleUserView : BaseView {
    fun onPostSoleResult(t: SoleUserRep?)
}
