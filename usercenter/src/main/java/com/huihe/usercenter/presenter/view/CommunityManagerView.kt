package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.AreaBeanRep
import com.kotlin.base.presenter.view.BaseView

interface CommunityManagerView :BaseView {
    fun onGetAreaBeanListResult(list: MutableList<AreaBeanRep.AreaBean>?)
}
