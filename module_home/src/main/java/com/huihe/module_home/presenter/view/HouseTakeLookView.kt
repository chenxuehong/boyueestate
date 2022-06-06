package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.HouseTakeLookRep
import com.kotlin.base.presenter.view.BaseView

interface HouseTakeLookView : BaseView {
    fun onTakeLookRecordResult(t: HouseTakeLookRep?)
}