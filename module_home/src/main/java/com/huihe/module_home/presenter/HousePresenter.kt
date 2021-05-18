package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.*
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
        pageNo: Int? = 1,
        pageSize: Int? = 30,
        sortReq: SortReq? = null,
        floorRanges :MutableList<FloorReq>?=null,
        roomNumRanges: String?=null,
        priceRanges:MutableList<PriceReq>?=null,
        moreReq: MoreReq? = null,
        villageIds:  MutableList<String>? =null
    ) {
        if (!checkNetWork()) {
            return
        }
        customersService?.getHouseList(
            pageNo, pageSize,
            sortReq,
            floorRanges,
            roomNumRanges,
            priceRanges,
            moreReq,
            villageIds
        )
            .execute(object : BaseSubscriber<HouseWrapper?>(mView) {
                override fun onNext(t: HouseWrapper?) {
                    mView.onGetHouseListResult(t?.list)
                }
            }, lifecycleProvider)

    }

    fun getHouseListByStatus(
        pageNo: Int? = 1,
        pageSize: Int? = 30,
        dataType: Int? = 0
    ) {
        if (!checkNetWork()) {
            return
        }
        customersService?.getHouseList(
            pageNo,
            pageSize,
            dataType
        )
            .execute(object : BaseSubscriber<HouseWrapper?>(mView) {
                override fun onNext(t: HouseWrapper?) {
                    mView.onGetHouseListResult(t?.list)
                }
            }, lifecycleProvider)

    }

    fun getVillages(
        latitude: Double?= null,
        longitude: Double?= null
    ) {
        if (!checkNetWork()) {
            return
        }
        customersService?.getVillages(
            latitude,
            longitude
        )
            .execute(object : BaseSubscriber<MutableList<District>?>(mView) {
                override fun onNext(t: MutableList<District>?) {
                    mView.onGetAreaBeanListResult(t)
                }
            }, lifecycleProvider)

    }
}
