package com.huihe.boyueestate.injection.module

import com.huihe.commonservice.service.user.UserService
import com.huihe.commonservice.service.user.iml.UserServiceImpl
import dagger.Module
import dagger.Provides

/*
    用户模块Module
 */
@Module
class SplashModule {

    @Provides
    fun provideUserService(userService: UserServiceImpl): UserService {
        return userService
    }

}

