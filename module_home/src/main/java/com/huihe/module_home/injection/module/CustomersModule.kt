package com.huihe.module_home.injection.module
import com.huihe.module_home.service.HouseService
import com.huihe.module_home.service.impl.HouseServiceImpl
import dagger.Module
import dagger.Provides

/*
    商品Module
 */
@Module
class CustomersModule {

    @Provides
    fun provideCustomerservice(houseService: HouseServiceImpl): HouseService {
        return houseService
    }

}
