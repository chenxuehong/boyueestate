package com.huihe.module_home.service.impl
import com.huihe.module_home.data.protocol.*
import com.huihe.module_home.data.repository.CustomersRepository
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.convert
import io.reactivex.Observable
import javax.inject.Inject

/*
    商品 业务层 实现类
 */
class HouseServiceImpl @Inject constructor(): HouseService {

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
            villageIds).convert()
    }

    override fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        dataType: Int?
    ): Observable<HouseWrapper?> {
        return repository.getHouseList(pageNo,pageSize,dataType).convert()
    }

    override fun getVillages(latitude: Double?, longitude: Double?): Observable<AreaBeanRep?> {
        return repository.getVillages(latitude,longitude).convert()
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

    override fun getUploadToken(): Observable<String?> {
        return repository.getUploadToken().convert()
    }

    override fun postHouseImage(req: SetHouseInfoReq): Observable<String?> {
        return repository.postHouseImage(req).convert()
    }

    override fun postReferImage(req: SetHouseInfoReq): Observable<String?> {
        return repository.postReferImage(req).convert()
    }
}
