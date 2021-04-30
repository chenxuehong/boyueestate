package com.huihe.module_home.service.impl
import com.huihe.module_home.data.protocol.House
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.HouseWrapper
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
        myHouse: Int?,
        hasKey: Int?,
        hasSole: Int?,
        myMaintain: Int?,
        isCirculation: Int?,
        entrustHouse: Int?,
        myCollect: Int?,
        floorageRanges: Map<String, String>?,
        roomNumRanges: Map<String, String>?
    ): Observable<HouseWrapper?> {
        return repository.getHouseList(
            pageNo,pageSize,
            myHouse, hasKey,hasSole,
            myMaintain,isCirculation,entrustHouse,
            myCollect,floorageRanges,roomNumRanges).convert()
    }

    override fun getHouseList(
        pageNo: Int?,
        pageSize: Int?,
        dataType: Int?
    ): Observable<HouseWrapper?> {
        return repository.getHouseList(pageNo,pageSize,dataType).convert()
    }

    override fun getHouseDetailById(id: String?): Observable<HouseDetail?> {
        return repository.getHouseDetailById(id).convert()
    }

}
