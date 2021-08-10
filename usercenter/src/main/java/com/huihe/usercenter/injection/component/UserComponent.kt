package com.huihe.usercenter.injection.component

import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.ui.activity.LoginActivity
import com.huihe.usercenter.ui.activity.MainActivity
import com.huihe.usercenter.ui.fragment.*
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
    fun inject(fragment: CommunityManagerFragment)
    fun inject(fragment: DistrictFragment)
    fun inject(fragment: CorporateCultureFragment)
    fun inject(fragment: SettingFragment)
    fun inject(fragment: AboutUsFragment)
    fun inject(fragment: AddressBookFragment)
    fun inject(fragment: DeptInfoFragment)
    fun inject(fragment: MeFragment)
    fun inject(fragment: ResultCommunityFragment)
    fun inject(activity: MainActivity)
    fun inject(fragment: MineLookTaskFragment)
    fun inject(fragment: MineLookTaskDetailFragment)
    fun inject(fragment: MineLookTaskHomeFragment)
    fun inject(fragment: BehaviourFragment)
    fun inject(fragment: InsertMineLookHouseFragment)
}
