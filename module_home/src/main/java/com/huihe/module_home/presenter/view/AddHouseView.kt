package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.SetHouseInfoRep
import com.kotlin.base.presenter.view.BaseView

interface AddHouseView : BaseView {
    fun onAddHouseInfoSuccess(t: SetHouseInfoRep?)
}
