package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.provider.data.protocol.District

interface SetHouseInfoView : BaseView {
    fun onGetAreaBeanListResult(list:MutableList<District>?)
    fun onSetHouseInfoSuccess(t: SetHouseInfoRep?)
}