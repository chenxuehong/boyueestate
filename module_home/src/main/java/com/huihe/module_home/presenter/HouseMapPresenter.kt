package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.HouseMapReq
import com.huihe.boyueentities.protocol.home.MapAreaRep
import com.huihe.module_home.presenter.view.FindHouseByMapView
import com.huihe.commonservice.service.house.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.huihe.boyueentities.protocol.common.District
import javax.inject.Inject

class HouseMapPresenter  @Inject constructor() : BasePresenter<FindHouseByMapView>() {

    @Inject
    lateinit var service: HouseService

    fun getMapRoomList(req:HouseMapReq){

        service.getMapRoomList(req)
            .execute(object :BaseSubscriber<MutableList<MapAreaRep>?>(mView){
                override fun onNext(t: MutableList<MapAreaRep>?) {
                    mView?.onGetHouseMapResult(t)
                }
            },lifecycleProvider)
    }

    fun getVillages(
        latitude: Double?= null,
        longitude: Double?= null
    ) {
        service?.getVillages(
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
