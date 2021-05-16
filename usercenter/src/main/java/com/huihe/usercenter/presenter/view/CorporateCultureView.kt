package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.CorporateCultureRep
import com.kotlin.base.presenter.view.BaseView

interface CorporateCultureView : BaseView {
    fun onMobTechList(list: MutableList<CorporateCultureRep.CorporateCulture>?)
}
