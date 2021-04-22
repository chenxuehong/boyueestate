package com.huihe.module_home.service.impl
import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.data.protocol.CustomerWrapper
import com.huihe.module_home.data.repository.CustomersRepository
import com.huihe.module_home.service.CustomersService
import com.kotlin.base.ext.convert
import io.reactivex.Observable
import javax.inject.Inject

/*
    商品 业务层 实现类
 */
class CustomersServiceImpl @Inject constructor(): CustomersService {

    @Inject
    lateinit var repository: CustomersRepository

    override fun getMoreCustomersList(
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
    ): Observable<CustomerWrapper?> {
        return repository.getMoreCustomersList(
            pageNo,pageSize,
            myHouse, hasKey,hasSole,
            myMaintain,isCirculation,entrustHouse,
            myCollect,floorageRanges,roomNumRanges).convert()
    }

}
