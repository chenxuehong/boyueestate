package com.huihe.module_home.data.repository

import com.huihe.module_home.data.api.HouseApi
import com.huihe.module_home.data.protocol.*
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import javax.inject.Inject

class CustomersRepository @Inject constructor() {

    fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        sortReq: SortReq?,
        floorRanges: MutableList<FloorReq>?,
        priceRanges: MutableList<PriceReq>?,
        moreReq: MoreReq?,
        villageIds: MutableList<String>?
    ): Observable<BaseResp<HouseWrapper?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseList(
                GetHouseListReq(
                    pageNo, pageSize,
                    sortReq?.floorOrder,
                    sortReq?.latestFollowTimeOrder,
                    sortReq?.defaultOrder,
                    sortReq?.createTimeOrder,
                    sortReq?.buildingOrder,
                    sortReq?.floorageOrder,
                    sortReq?.priceOrder,
                    floorRanges,
                    priceRanges,
                    moreReq?.hFlag,
                    moreReq?.days,
                    moreReq?.myHouse,
                    moreReq?.hasKey,
                    moreReq?.hasSole,
                    moreReq?.myMaintain,
                    moreReq?.isCirculation,
                    moreReq?.entrustHouse,
                    moreReq?.myCollect,
                    moreReq?.floorageRanges,
                    moreReq?.roomNumRanges,
                    villageIds
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

    fun getHouseDetailById(id: String?): Observable<BaseResp<HouseDetail?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseDetailById(
                id
            )
    }

    fun getVillages(latitude: Double?, longitude: Double?): Observable<BaseResp<AreaBeanWrapper?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getVillages(
                latitude,
                longitude
            )
    }

    fun getHouseDetailRelationPeople(id: String?): Observable<BaseResp<OwnerInfo?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseDetailRelationPeople(
               id
            )
    }
}
