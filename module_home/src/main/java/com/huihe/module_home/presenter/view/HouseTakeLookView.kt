package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.HouseTakeLookRep
import com.kotlin.base.presenter.view.BaseView

interface HouseTakeLookView : BaseView {
    fun onTakeLookRecordResult(t: HouseTakeLookRep?)
}