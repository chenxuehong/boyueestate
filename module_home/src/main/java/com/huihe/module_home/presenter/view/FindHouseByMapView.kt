package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.MapAreaRep
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.provider.data.protocol.District

interface FindHouseByMapView : BaseView {

    fun onGetHouseMapResult(t: MutableList<MapAreaRep>?)
    fun onGetAreaBeanListResult(list: MutableList<District>?)
}
