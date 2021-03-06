package com.huihe.commonservice.data.house.repository

import com.huihe.commonservice.data.house.api.HouseApi
import com.huihe.boyueentities.protocol.home.*
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.huihe.commonservice.data.upload.UploadApi
import com.huihe.boyueentities.protocol.common.District
import io.reactivex.Observable
import javax.inject.Inject

class HouseRepository @Inject constructor() {

    fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        sortReq: SortReq?,
        floorRanges: MutableList<FloorReq>?,
        priceRanges: MutableList<PriceReq>?,
        moreReq: MoreReq?,
        villageIds: MutableList<String>?,
        searchReq: SearchReq?
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
                    villageIds,
                    searchReq?.villageName,
                    searchReq?.ownerTel,
                    searchReq?.houseCode,
                    searchReq?.building,
                    searchReq?.hNum,
                    searchReq?.schoolIds,
                    searchReq?.createUsers,
                    searchReq?.imageUsers,
                    searchReq?.maintainUsers,
                    searchReq?.bargainPriceUsers,
                    searchReq?.blockUsers,
                    searchReq?.haveKeyUsers,
                    searchReq?.soleUsers,
                    searchReq?.entrustUsers
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

    fun getVillages(latitude: Double?, longitude: Double?): Observable<BaseResp<MutableList<District>?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getVillages()
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

    fun addHouseTakeLookRecord(req:AddTakeLookRecordReq?): Observable<BaseResp<Any?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .addHouseTakeLookRecord(req)
    }

    fun getHouseLog(page: Int, size: Int, houseCode: String?): Observable<BaseResp<HouseLogRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseLog(page,size,houseCode)
    }

    fun getHousePhoneLog(page: Int, size: Int, houseCode: String?): Observable<BaseResp<HouseLogRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHousePhoneLog(page,size,houseCode)
    }

    fun addHouseInfo(req: AddHouseInfoReq): Observable<BaseResp<SetHouseInfoRep?>>  {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .addHouseInfo(req)
    }

    fun getMapRoomList(req: HouseMapReq): Observable<BaseResp<MutableList<MapAreaRep>?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getMapRoomList(req)
    }

    fun putHouseEntrust(req: EntrustUserReq): Observable<BaseResp<EntrustUserRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .putHouseEntrust(req)
    }

    fun getHouseEntrust(houseId: String): Observable<BaseResp<EntrustUserRep?>>  {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseEntrust(houseId)
    }

    fun putHouseKey(req: HaveKeyUserReq): Observable<BaseResp<HaveKeyUserRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .putHouseKey(req)
    }

    fun pathHouseCreateUser(id: String?, createUser: String?): Observable<BaseResp<HouseCreateUserRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .pathHouseCreateUser(HouseCreateUserReq(id,createUser))
    }

    fun putCapping(req: CappingReq): Observable<BaseResp<CappingRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .putCapping(req)
    }

    fun postSole(req: SoleUserReq?): Observable<BaseResp<SoleUserRep?>> {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .postSole(req)
    }

    fun getHouseMobile(houseId: String?): Observable<BaseResp<String?>>  {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .getHouseMobile(houseId)
    }

    fun preCheckHouse(req: AddHouseInfoReq): Observable<BaseResp<String?>>  {
        return RetrofitFactory.instance.create(HouseApi::class.java)
            .preCheckHouse(req)
    }
}
