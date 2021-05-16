package com.huihe.usercenter.presenter.view

import com.huihe.usercenter.data.protocol.SchoolDistrictRep
import com.kotlin.base.presenter.view.BaseView

interface DistrictView : BaseView {
    fun onSchoolDistrictList(list: MutableList<SchoolDistrictRep.SchoolDistrict>?)
}
