package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.LookHouseAccompanyFollowReq
import com.huihe.usercenter.presenter.view.AccompanyFollowView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class AccompanyFollowPresenter @Inject constructor() : BasePresenter<AccompanyFollowView>(){

    @Inject
    lateinit var service: UserService

    fun lookHouseAccompanyFollow(req: LookHouseAccompanyFollowReq) {
        mView?.showLoading()
        service.lookHouseAccompanyFollow(req)
            .execute(object :BaseSubscriber<Any>(mView){
                override fun onNext(t: Any) {
                    super.onNext(t)
                    mView?.onLookHouseAccompanyFollowSuccess()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView?.onLookHouseAccompanyFollowSuccess()
                    }
                }
            },lifecycleProvider)
    }
}
