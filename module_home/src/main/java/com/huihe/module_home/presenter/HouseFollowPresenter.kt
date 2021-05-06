package com.huihe.module_home.presenter

import com.huihe.module_home.presenter.view.HouseFollowView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class HouseFollowPresenter @Inject constructor(): BasePresenter<HouseFollowView>() {
    @Inject
    lateinit var customersService: HouseService
}