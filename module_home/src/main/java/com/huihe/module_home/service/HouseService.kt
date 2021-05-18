package com.huihe.module_home.service

import com.huihe.module_home.data.protocol.*
import io.reactivex.Observable

/*
    商品 业务层 接口
 */
interface HouseService {

    /*
        获取房源列表
     */
    fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        sortReq: SortReq?,
        floorRanges: MutableList<FloorReq>?,
        roomNumRanges: String?,
        priceRanges: MutableList<PriceReq>?,
        moreReq: MoreReq?,
        villageIds: MutableList<String>?
    ): Observable<HouseWrapper?>

    fun getHouseList(
        pageNo: Int? = null,
        pageSize: Int? = null,
        dataType: Int?
    ): Observable<HouseWrapper?>

    fun getVillages(
        latitude: Double? = null,
        longitude: Double? = null
    ): Observable<MutableList<District>?>

    /**
     * 获取房源详情信息
     *
     */
    fun getHouseDetailById(
        id: String? = null
    ): Observable<HouseDetail?>

    fun getHouseDetailRelationPeople(
        id: String? = null
    ): Observable<OwnerInfo?>

    fun reqCollection(
        id: String?
    ): Observable<Any?>

    fun reqDeleteCollection(
        id: String?
    ): Observable<Any?>

    fun setHouseInfo(
        req: SetHouseInfoReq
    ): Observable<SetHouseInfoRep?>

    fun putHouseInfo(
        req: SetHouseInfoReq
    ): Observable<SetHouseInfoRep?>

    fun getUploadToken(): Observable<String?>
    fun postHouseImage( req: SetHouseInfoReq): Observable<String?>
    fun postReferImage( req: SetHouseInfoReq): Observable<String?>
    fun getCustomerProfile(id: String?):Observable<CustomerProfileInfo?>
    fun getHouseFollowList(pageNo: Int?, pageSize: Int?, houseCode: String?):Observable<FollowRep?>
    fun addFollowContent(houseId: String?, followContent: String?):Observable<FollowRep.FollowBean?>
    fun getTakeLookRecord(page:Int?,limit:Int?,code:String?):Observable<HouseTakeLookRep?>
    fun addTakeLookRecord(houseCodeList: MutableList<String>?=null, evaluate: String?=null ,code: String?=null):Observable<HouseTakeLookRep.HouseTakeLook?>
    fun getHouseLog(page: Int, size: Int, houseCode: String?):Observable<HouseLogRep?>
    fun getHousePhoneLog(page: Int, size: Int, houseCode: String?):Observable<HouseLogRep?>
    fun addHouseInfo(req: AddHouseInfoReq):Observable<SetHouseInfoRep?>
    fun getMapRoomList(req: HouseMapReq):Observable<MutableList<MapAreaRep>?>
}
