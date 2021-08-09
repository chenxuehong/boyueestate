package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.BehaviourView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class BehaviourPresenter @Inject constructor():BasePresenter<BehaviourView>(){

    @Inject
    lateinit var service: UserService
}