package com.huihe.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.huihe.boyueentities.protocol.common.District

interface CommunityManagerView :BaseView {
    fun onGetAreaBeanListResult(list: MutableList<District>?)
}
