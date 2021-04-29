package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.HouseWrapper
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class HousePresenter @Inject constructor() : BasePresenter<SecondHandHouseView>() {

    @Inject
    lateinit var customersService: HouseService

    fun getHouseList(
        pageNo: Int?=null,
        pageSize: Int?=null,
        myHouse: Int?=null,
        hasKey: Int?=null,
        hasSole: Int?=null,
        myMaintain: Int?=null,
        isCirculation: Int?=null,
        entrustHouse: Int?=null,
        myCollect: Int?=null,
        floorageRanges: Map<String, String>?=null,
        roomNumRanges: Map<String, String>?=null
    ) {
        if (!checkNetWork()) {
            return
        }
        customersService?.getHouseList(
            pageNo,pageSize,
            myHouse, hasKey,hasSole,
            myMaintain,isCirculation,entrustHouse,
            myCollect,floorageRanges,roomNumRanges)
            .execute(object : BaseSubscriber<HouseWrapper?>(mView) {
                override fun onNext(t: HouseWrapper?) {
                    mView.onGetHouseListResult(t?.list)
                }
            }, lifecycleProvider)

    }
    fun getHouseListByStatus(
        pageNo: Int?=null,
        pageSize: Int?=null,
        dataType: Int?=null
    ) {
        if (!checkNetWork()) {
            return
        }
        customersService?.getHouseList(
            pageNo,
            pageSize,
            dataType)
            .execute(object : BaseSubscriber<HouseWrapper?>(mView) {
                override fun onNext(t: HouseWrapper?) {
                    mView.onGetHouseListResult(t?.list)
                }
            }, lifecycleProvider)

    }
}
