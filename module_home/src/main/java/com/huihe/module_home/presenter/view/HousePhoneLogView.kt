package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.HouseLog
import com.kotlin.base.presenter.view.BaseView

interface HousePhoneLogView : BaseView {
    fun getLogListResult(list: MutableList<HouseLog>?, totalCount: Int?)
}
