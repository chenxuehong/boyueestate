package com.huihe.customercenter.injection.component

import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.ui.fragment.*
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
    fun inject(fragment: CustomerDetailFragment)
    fun inject(fragment: CustomerFollowFragment)
    fun inject(fragment: AddCustomerFollowFragment)
    fun inject(fragment: CustomerLogFragment)
    fun inject(fragment: CustomerTelLogFragment)
    fun inject(fragment: CustomerInfoEditFragment)
    fun inject(fragment: SetCustomerInfoFragment)
    fun inject(fragment: AddCustomerFragment)

}
