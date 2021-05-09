package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.AreaBean
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.kotlin.base.presenter.view.BaseView

interface SetHouseInfoView : BaseView {
    fun onGetAreaBeanListResult(list:MutableList<AreaBean>?)
    fun onSetHouseInfoSuccess(t: SetHouseInfoRep?)
}