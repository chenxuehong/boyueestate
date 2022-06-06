package com.huihe.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.huihe.boyueentities.protocol.common.District

interface ResultCommunityView :BaseView {
    fun onGetAreaBeanListResult(villages: MutableList<District.ZoneBean.VillageBean>?)
}