package com.huihe.module_home.service.impl

import com.huihe.module_home.data.protocol.*
import com.huihe.module_home.data.repository.CustomersRepository
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.convert
import io.reactivex.Observable
import javax.inject.Inject

/*
    房源 业务层 实现类
 */
class HouseServiceImpl @Inject constructor() : HouseService {

    @Inject
    lateinit var repository: CustomersRepository

    override fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        sortReq: SortReq?,
        floorRanges: MutableList<FloorReq>?,
        roomNumRanges: String?,
        priceRanges: MutableList<PriceReq>?,
        moreReq: MoreReq?,
        villageIds: MutableList<String>?
    ): Observable<HouseWrapper?> {
        return repository.getHouseList(
            pageNo, pageSize,
            sortReq,
            floorRanges,
            priceRanges,
            moreReq,
            villageIds
        ).convert()
    }

    override fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        dataType: Int?
    ): Observable<HouseWrapper?> {
        return repository.getHouseList(pageNo, pageSize, dataType).convert()
    }

    override fun getVillages(latitude: Double?, longitude: Double?): Observable<AreaBeanRep?> {
        return repository.getVillages(latitude, longitude).convert()
    }

    override fun getHouseDetailById(id: String?): Observable<HouseDetail?> {
        return repository.getHouseDetailById(id).convert()
    }

    override fun getHouseDetailRelationPeople(id: String?): Observable<OwnerInfo?> {
        return repository.getHouseDetailRelationPeople(id).convert()
    }

    override fun reqCollection(id: String?): Observable<Any?> {
        return repository.reqCollection(id).convert()
    }

    override fun reqDeleteCollection(id: String?): Observable<Any?> {
        return repository.reqDeleteCollection(id).convert()
    }

    override fun setHouseInfo(
        req: SetHouseInfoReq
    ): Observable<SetHouseInfoRep?> {
        return repository.setHouseInfo(req).convert()
    }

    override fun putHouseInfo(
        req: SetHouseInfoReq
    ): Observable<SetHouseInfoRep?> {
        return repository.putHouseInfo(req).convert()
    }

    override fun getUploadToken(): Observable<String?> {
        return repository.getUploadToken().convert()
    }

    override fun postHouseImage(req: SetHouseInfoReq): Observable<String?> {
        return repository.postHouseImage(req).convert()
    }

    override fun postReferImage(req: SetHouseInfoReq): Observable<String?> {
        return repository.postReferImage(req).convert()
    }

    override fun getCustomerProfile(id: String?): Observable<CustomerProfileInfo?> {
        return repository.getCustomerProfile(id).convert()
    }

    override fun getHouseFollowList(
        pageNo: Int?,
        pageSize: Int?,
        houseCode: String?
    ): Observable<FollowRep?> {
        return repository.getHouseFollowList(pageNo,pageSize,houseCode).convert()
    }

    override fun addFollowContent(
        houseId: String?,
        followContent: String?
    ): Observable<FollowRep.FollowBean?> {
        return repository.addFollowContent(houseId,followContent).convert()
    }

    override fun getTakeLookRecord(page: Int?, limit: Int?, code: String?) : Observable<HouseTakeLookRep?>{
        return repository.getTakeLookRecord(page,limit,code).convert()
    }

    override fun addTakeLookRecord(
        houseCodeList: MutableList<String>?,
        evaluate: String?,
        code: String?
    ): Observable<HouseTakeLookRep.HouseTakeLook?> {
        return repository.addHouseTakeLookRecord(houseCodeList,evaluate,code).convert()
    }

    override fun getHouseLog(page: Int, size: Int, houseCode: String?) :Observable<HouseLogRep?>{
        return repository.getHouseLog(page,size,houseCode).convert()
    }

    override fun getHousePhoneLog(
        page: Int,
        size: Int,
        houseCode: String?
    ): Observable<HouseLogRep?> {
        return repository.getHousePhoneLog(page,size,houseCode).convert()
    }

    override fun addHouseInfo(req: AddHouseInfoReq):Observable<SetHouseInfoRep?> {
        return repository.addHouseInfo(req).convert()
    }

    override fun getMapRoomList(req: HouseMapReq): Observable<MutableList<MapAreaRep>?> {
        return repository.getMapRoomList(req).convert()
    }

}
