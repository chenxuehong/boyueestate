package com.huihe.bafuestate.injection.component

import com.huihe.bafuestate.injection.module.SplashModule
import com.huihe.bafuestate.ui.activity.SplashActivity
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import dagger.Component

/*
    模块Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(SplashModule::class))
interface SplashComponent {
    fun inject(activity: SplashActivity)
}
