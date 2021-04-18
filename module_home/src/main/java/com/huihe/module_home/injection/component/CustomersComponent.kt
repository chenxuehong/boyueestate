package com.kotlin.goods.injection.component

import com.huihe.module_home.ui.fragment.CustomersFragment
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.goods.injection.module.CustomersModule
import dagger.Component

/*
    商品Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CustomersModule::class))
interface CustomersComponent {
    fun inject(fragment: CustomersFragment)
}
