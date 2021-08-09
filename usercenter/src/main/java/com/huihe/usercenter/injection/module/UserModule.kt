package com.huihe.usercenter.injection.module

import androidx.annotation.IntDef
import com.huihe.usercenter.service.UserService
import com.huihe.usercenter.service.iml.UserServiceImpl
import dagger.Module
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/*
    用户模块Module
 */
@Module
class UserModule {

    @Provides
    fun provideUserService(userService: UserServiceImpl): UserService {
        return userService
    }
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        UserLevels.Staff,
        UserLevels.Administrators
    )

    annotation class UserLevels {
        companion object {
            const val  Staff = 1
            const val  Administrators = 2
        }
    }
}

