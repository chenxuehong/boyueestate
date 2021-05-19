package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.provider.data.protocol.District

interface AddHouseView : BaseView {
    fun onAddHouseInfoSuccess(t: SetHouseInfoRep?)
    fun onGetAreaBeanListResult(list: MutableList<District>?)
}
