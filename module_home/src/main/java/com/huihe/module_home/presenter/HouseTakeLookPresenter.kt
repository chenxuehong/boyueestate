package com.huihe.module_home.presenter

import com.huihe.module_home.presenter.view.HouseTakeLookView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class HouseTakeLookPresenter @Inject constructor(): BasePresenter<HouseTakeLookView>() {
    @Inject
    lateinit var customersService: HouseService
}