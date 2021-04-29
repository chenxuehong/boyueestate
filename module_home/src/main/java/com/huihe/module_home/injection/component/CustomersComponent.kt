package com.huihe.module_home.injection.component
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.fragment.CustomersFragment
import com.huihe.module_home.ui.fragment.CustomersMapFragment
import com.huihe.module_home.ui.fragment.HouseDetailFragment
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import dagger.Component

/*
    首页Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CustomersModule::class))
interface CustomersComponent {
    fun inject(fragment: CustomersFragment)
    fun inject(fragment: CustomersMapFragment)
    fun inject(fragment: HouseDetailFragment)
}
