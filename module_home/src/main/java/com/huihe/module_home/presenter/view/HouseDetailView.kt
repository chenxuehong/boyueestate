package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.House
import com.kotlin.base.presenter.view.BaseView

interface HouseDetailView :BaseView{

    fun onGetHouseDetailResult(house: House?)
}