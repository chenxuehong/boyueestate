package com.huihe.module_home.data.repository

import com.huihe.module_home.data.api.HouseApi
import com.huihe.module_home.data.protocol.*
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.provider.data.api.UploadApi
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

    fun getVillages(latitude: Double?, longitude: Double?): Observable<BaseResp<AreaBeanRep?>> {
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

    fun reqCollection(id: String?): Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .reqCollection(id)
    }

    fun reqDeleteCollection(id: String?): Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .reqDeleteCollection(id)
    }

    fun setHouseInfo(
        req: SetHouseInfoReq
    ): Observable<BaseResp<SetHouseInfoRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .setHouseInfo(req)
    }

    fun putHouseInfo(
        req: SetHouseInfoReq
    ): Observable<BaseResp<SetHouseInfoRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .putHouseInfo(req)
    }

    fun getUploadToken(): Observable<BaseResp<String?>> {
        return RetrofitFactory.instance.create(UploadApi::class.java)
            .getUploadToken()
    }

    fun postHouseImage(req:SetHouseInfoReq): Observable<BaseResp<String?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .postHouseImage(req)
    }

    fun postReferImage(req:SetHouseInfoReq): Observable<BaseResp<String?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .postReferImage(req)
    }

    fun getCustomerProfile(id: String?): Observable<BaseResp<CustomerProfileInfo?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getCustomerProfile(id)
    }

    fun getHouseFollowList(page: Int?, pageSize: Int?, houseCode: String?):  Observable<BaseResp<FollowRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseFollowList(page,pageSize,houseCode)
    }

    fun addFollowContent(houseId: String?, followContent: String?): Observable<BaseResp<FollowRep.FollowBean?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .addFollowContent(FollowReq(houseId,followContent))
    }

    fun getTakeLookRecord(page: Int?, limit: Int?, code: String?):  Observable<BaseResp<HouseTakeLookRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getTakeLookRecord(page,limit,code)
    }
}
