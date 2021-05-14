package com.huihe.customercenter.injection.module


import androidx.annotation.IntDef
import com.huihe.customercenter.service.CustomerService
import com.huihe.customercenter.service.iml.CustomerServiceIml
import dagger.Module
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/*
    客源Module
 */
@Module
class CustomersModule {

    @Provides
    fun provideCustomerService(houseService: CustomerServiceIml): CustomerService {
        return houseService
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        SearchType.CreateUserType,
        SearchType.StatusType,
        SearchType.MoreType,
        SearchType.SortType
    )

    annotation class SearchType {
        companion object {
            const val  CreateUserType = 1
            const val  StatusType = 2
            const val  MoreType = 3
            const val  SortType = 4
        }
    }
}
