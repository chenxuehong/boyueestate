package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.data.protocol.CustomerWrapper
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.service.CustomersService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class CustomersPresenter @Inject constructor() : BasePresenter<SecondHandHouseView>() {

    @Inject
    lateinit var customersService: CustomersService

    fun getMoreCustomersList(
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
        customersService?.getMoreCustomersList(
            pageNo,pageSize,
            myHouse, hasKey,hasSole,
            myMaintain,isCirculation,entrustHouse,
            myCollect,floorageRanges,roomNumRanges)
            .execute(object : BaseSubscriber<CustomerWrapper?>(mView) {
                override fun onNext(t: CustomerWrapper?) {
                    mView.onGetHouseListResult(t?.list)
                }
            }, lifecycleProvider)

    }
}
