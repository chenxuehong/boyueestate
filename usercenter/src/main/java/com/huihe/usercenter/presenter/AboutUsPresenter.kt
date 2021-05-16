package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.AboutUsView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class AboutUsPresenter @Inject constructor() : BasePresenter<AboutUsView>(){

    @Inject
    lateinit var service: UserService

}
