package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.House
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.provider.data.protocol.District

interface SecondHandHouseView : BaseView {

    fun onGetHouseListResult(houses:MutableList<House>?)
    fun onGetAreaBeanListResult(areaBeans:MutableList<District>?)
}
