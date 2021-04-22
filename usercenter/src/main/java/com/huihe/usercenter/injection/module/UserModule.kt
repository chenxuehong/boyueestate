package com.huihe.usercenter.injection.module

import com.huihe.usercenter.service.UserService
import com.huihe.usercenter.service.iml.UserServiceImpl
import dagger.Module
import dagger.Provides

/*
    用户模块Module
 */
@Module
class UserModule {

    @Provides
    fun provideUserService(userService: UserServiceImpl): UserService {
        return userService
    }

}

