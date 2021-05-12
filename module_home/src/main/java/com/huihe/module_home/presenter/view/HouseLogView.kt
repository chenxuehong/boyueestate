package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.HouseLog
import com.kotlin.base.presenter.view.BaseView

interface HouseLogView : BaseView {
    fun onLogListResult(list: MutableList<HouseLog>?, totalCount: Int?)
}