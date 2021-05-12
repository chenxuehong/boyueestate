package com.huihe.module_home.injection.component
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.fragment.*

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import dagger.Component

/*
    首页Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CustomersModule::class))
interface CustomersComponent {

    fun inject(fragment: HouseFragment)
    fun inject(fragment: HouseMapFragment)
    fun inject(fragment: HouseDetailFragment)
    fun inject(fragment: HouseFollowFragment)
    fun inject(fragment: HouseTakeLookRecordFragment)
    fun inject(fragment: SetHouseInfoFragment)
    fun inject(fragment: SetOwnerInfoFragment)
    fun inject(fragment: CustomerProfileFragment)
    fun inject(fragment: AddFollowFragment)
    fun inject(fragment: HouseTakeLookRecordInsertFragment)
    fun inject(fragment: HouseLogFragment)
    fun inject(fragment: HousePhoneLogFragment)
    fun inject(fragment: AddHouseFragment)
}
