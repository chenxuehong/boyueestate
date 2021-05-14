package com.huihe.customercenter.injection.component

import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.ui.fragment.CustomerListFragment
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import dagger.Component

/*
    首页Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CustomersModule::class))
interface CustomersComponent {

    fun inject(fragment: CustomerListFragment)

}
