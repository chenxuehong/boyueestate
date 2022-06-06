package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.MineLookHouseFollowReq
import com.huihe.usercenter.presenter.view.MineLookHouseFollowView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class MineLookHouseFollowPresenter @Inject constructor(): BasePresenter<MineLookHouseFollowView>(){

    @Inject
    lateinit var service: UserService

    fun lookTaskFollow(req: MineLookHouseFollowReq) {
        service.lookTaskFollow(req)
            .execute(object :BaseSubscriber<Any>(mView){
                override fun onNext(t: Any) {
                    super.onNext(t)
                    mView?.onLookHouseFollowSuccess()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView?.onLookHouseFollowSuccess()
                    }
                }
            },lifecycleProvider)
    }

}