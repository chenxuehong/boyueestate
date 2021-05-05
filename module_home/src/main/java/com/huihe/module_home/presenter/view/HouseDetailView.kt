package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.OwnerInfo
import com.kotlin.base.presenter.view.BaseView

interface HouseDetailView :BaseView{

    fun onGetHouseDetailResult(houseDetail: HouseDetail?)
    fun onGetOwnerResult(ownerInfo: OwnerInfo?)
}