package com.kotlin.goods.service.impl

import com.huihe.module_home.data.protocol.Customer
import com.huihe.module_home.data.repository.CustomersRepository
import com.kotlin.base.ext.convert
import com.kotlin.goods.service.CustomersService
import io.reactivex.Observable
import javax.inject.Inject

/*
    商品 业务层 实现类
 */
class CustomersServiceImpl @Inject constructor(): CustomersService {

    @Inject
    lateinit var repository: CustomersRepository

    override fun getMoreCustomersList(dataType: Int): Observable<MutableList<Customer>?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMoreCustomersList(
        myHouse: Int,
        hasKey: Int,
        hasSole: Int,
        myMaintain: Int,
        isCirculation: Int,
        entrustHouse: Int,
        myCollect: Int,
        floorageRanges: Map<String, String>,
        roomNumRanges: Map<String, String>
    ): Observable<MutableList<Customer>?> {
        return repository.getMoreCustomersList(
            myHouse, hasKey,hasSole,
            myMaintain,isCirculation,entrustHouse,
            myCollect,floorageRanges,roomNumRanges).convert()
    }

}
