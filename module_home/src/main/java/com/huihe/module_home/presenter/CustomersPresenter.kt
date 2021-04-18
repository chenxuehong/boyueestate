package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.service.CustomersService
import javax.inject.Inject

class CustomersPresenter @Inject constructor() : BasePresenter<SecondHandHouseView>() {

    @Inject
    lateinit var goodsService: CustomersService

    fun getMoreCustomersList(
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
        mView.showLoading()
        goodsService.getMoreCustomersList(
            myHouse, hasKey,hasSole,
            myMaintain,isCirculation,entrustHouse,
            myCollect,floorageRanges,roomNumRanges)
            .execute(object : BaseSubscriber<MutableList<Customer>?>(mView) {
                override fun onNext(t: MutableList<Customer>?) {
                    if (t?.size!! >0){
                        mView.onGetHouseListResult(t)
                    }else{
                        mView.onDataIsNull()
                    }
                }
            }, lifecycleProvider)

    }
}
