package com.huihe.usercenter.injection.component

import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.ui.activity.LoginActivity
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import dagger.Component

/*
    用户模块Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(UserModule::class))
interface UserComponent {
    fun inject(activity: LoginActivity)
}
