package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.SoleUserRep
import com.kotlin.base.presenter.view.BaseView

interface SoleUserView : BaseView {
    fun onPostSoleResult(t: SoleUserRep?)
}
