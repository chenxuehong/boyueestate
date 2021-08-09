package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.MineLookTaskDetailView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.presenter.BasePresenter
import javax.inject.Inject

class MineLookTaskDetailPresenter @Inject constructor()  :BasePresenter<MineLookTaskDetailView>(){

    @Inject
    lateinit var service: UserService

    fun getLookTaskDetail(takeLookId: String?) {
        service.getLookTaskDetail(takeLookId)
    }

}