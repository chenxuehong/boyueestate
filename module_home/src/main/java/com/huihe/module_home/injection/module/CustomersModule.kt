package com.huihe.module_home.injection.module

import com.huihe.module_home.service.HouseService

import androidx.annotation.IntDef
import com.huihe.module_home.service.impl.HouseServiceImpl
import dagger.Module
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/*
    首页Module
 */
@Module
class CustomersModule {

    @Provides
    fun provideCustomerservice(houseService: HouseServiceImpl): HouseService {
        return houseService
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        HouseDetailType.BANNER,
        HouseDetailType.BASIC_INFO,
        HouseDetailType.ITEM_DETAIL_INFO,
        HouseDetailType.ITEM_REWARKS_INFO,
        HouseDetailType.ITEM_OWNER_INFO,
        HouseDetailType.ITEM_PHOTO,
        HouseDetailType.ITEM_REFERURL,
        HouseDetailType.MAP,
        HouseDetailType.TAB
    )

    annotation class HouseDetailType {
        companion object {
           const val  BANNER = 1
           const val  BASIC_INFO = 2
           const val  ITEM_DETAIL_INFO = 3
           const val  ITEM_REWARKS_INFO = 4
           const val  ITEM_OWNER_INFO = 5
           const val  ITEM_PHOTO = 6
           const val  ITEM_REFERURL = 7
           const val  MAP = 8
           const val  TAB = 9
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        SearchType.AreaType,
        SearchType.FloorsType,
        SearchType.PriceType,
        SearchType.MoreType,
        SearchType.SortType
    )

    annotation class SearchType {
        companion object {
           const val  AreaType = 1
           const val  FloorsType = 2
           const val  PriceType = 3
           const val  MoreType = 4
           const val  SortType = 5
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        OwnerInfoType.createUser,
        OwnerInfoType.maintainUser,
        OwnerInfoType.imageUser,
        OwnerInfoType.entrustUser,
        OwnerInfoType.haveKeyUser,
        OwnerInfoType.soleUser,
        OwnerInfoType.bargainPriceUser,
        OwnerInfoType.blockUser,
        OwnerInfoType.openUser
    )
    annotation class OwnerInfoType {
        companion object {
            const val  createUser = 1
            const val  maintainUser = 2
            const val  imageUser = 3
            const val  entrustUser = 4
            const val  haveKeyUser = 5
            const val  soleUser = 6
            const val  bargainPriceUser = 7
            const val  blockUser = 8
            const val  openUser = 9
        }
    }
}
