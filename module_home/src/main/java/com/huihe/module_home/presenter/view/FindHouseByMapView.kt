package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.District
import com.huihe.module_home.data.protocol.MapAreaRep
import com.kotlin.base.presenter.view.BaseView

interface FindHouseByMapView : BaseView {

    fun onGetHouseMapResult(t: MutableList<MapAreaRep>?)
    fun onGetAreaBeanListResult(list: MutableList<District>?)
}
