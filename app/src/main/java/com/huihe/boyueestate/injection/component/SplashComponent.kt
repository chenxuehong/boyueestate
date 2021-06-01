package com.huihe.boyueestate.injection.component

import com.huihe.boyueestate.injection.module.SplashModule
import com.huihe.boyueestate.ui.activity.SplashActivity
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
