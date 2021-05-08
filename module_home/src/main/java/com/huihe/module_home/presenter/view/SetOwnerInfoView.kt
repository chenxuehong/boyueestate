package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.kotlin.base.presenter.view.BaseView

interface SetOwnerInfoView : BaseView {
    fun onHouseInfo(t: SetHouseInfoRep?)
}