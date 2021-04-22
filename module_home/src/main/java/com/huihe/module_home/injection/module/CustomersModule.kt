package com.huihe.module_home.injection.module
import com.huihe.module_home.service.CustomersService
import com.huihe.module_home.service.impl.CustomersServiceImpl
import dagger.Module
import dagger.Provides

/*
    商品Module
 */
@Module
class CustomersModule {

    @Provides
    fun provideCustomerservice(customersService: CustomersServiceImpl): CustomersService {
        return customersService
    }

}
