package com.kotlin.goods.injection.module

import com.kotlin.goods.service.CustomersService
import com.kotlin.goods.service.impl.CustomersServiceImpl
import dagger.Module
import dagger.Provides

/*
    商品Module
 */
@Module
class CustomersModule {

    @Provides
    fun provideCustomerservice(goodsService: CustomersServiceImpl): CustomersService {
        return goodsService
    }

}
