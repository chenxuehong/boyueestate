package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.House
import com.kotlin.base.presenter.view.BaseView
import com.huihe.boyueentities.protocol.common.District

interface SecondHandHouseView : BaseView {

    fun onGetHouseListResult(houses:MutableList<House>?)
    fun onGetAreaBeanListResult(areaBeans:MutableList<District>?)
}
