package com.huihe.module_home.presenter

import com.huihe.module_home.presenter.view.HouseTakeLookRecordInsertView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class HouseTakeLookRecordInsertPresenter @Inject constructor(): BasePresenter<HouseTakeLookRecordInsertView>(){

    @Inject
    lateinit var service: HouseService
}
