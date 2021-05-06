package com.huihe.module_home.presenter

import com.huihe.module_home.presenter.view.HouseLogView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class HouseLogPresenter @Inject constructor(): BasePresenter<HouseLogView>() {
    @Inject
    lateinit var customersService: HouseService
}