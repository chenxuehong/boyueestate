package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.MapAreaRep
import com.kotlin.base.presenter.view.BaseView
import com.huihe.boyueentities.protocol.common.District

interface FindHouseByMapView : BaseView {

    fun onGetHouseMapResult(t: MutableList<MapAreaRep>?)
    fun onGetAreaBeanListResult(list: MutableList<District>?)
}
