package com.huihe.module_home.data.repository

import com.huihe.module_home.data.api.HouseApi
import com.huihe.module_home.data.protocol.HouseWrapper
import com.huihe.module_home.data.protocol.GetHouseListReq
import com.huihe.module_home.data.protocol.HouseDetail
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class CustomersRepository @Inject constructor() {

    fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        myHouse: Int?,
        hasKey: Int?,
        hasSole: Int?,
        myMaintain: Int?,
        isCirculation: Int?,
        entrustHouse: Int?,
        myCollect: Int?,
        floorageRanges: Map<String, String>?,
        roomNumRanges: Map<String, String>?
    ): Observable<BaseResp<HouseWrapper?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseList(
                GetHouseListReq(
                    pageNo,pageSize,
                    myHouse, hasKey,hasSole,
                    myMaintain,isCirculation,entrustHouse,
                    myCollect,floorageRanges,roomNumRanges
                )
            )
    }

    fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        dataType: Int?
    ): Observable<BaseResp<HouseWrapper?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseList(
                pageNo,
                pageSize,
                dataType
            )
    }

    fun getHouseDetailById(id: String?):  Observable<BaseResp<HouseDetail?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseDetailById(
                id
            )
    }
}
