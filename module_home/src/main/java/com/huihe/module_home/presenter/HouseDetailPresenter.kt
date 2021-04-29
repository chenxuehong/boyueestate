package com.huihe.module_home.presenter

import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.service.CustomersService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class HouseDetailPresenter @Inject constructor() : BasePresenter<HouseDetailView>(){

    @Inject
    lateinit var customersService: CustomersService


}