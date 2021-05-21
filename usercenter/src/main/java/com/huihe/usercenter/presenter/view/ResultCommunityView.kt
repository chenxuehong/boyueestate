package com.huihe.usercenter.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.provider.data.protocol.District

interface ResultCommunityView :BaseView {
    fun onGetAreaBeanListResult(villages: MutableList<District.ZoneBean.VillageBean>?)
}