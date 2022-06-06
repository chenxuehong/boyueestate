package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.LookTaskAuditReq
import com.huihe.usercenter.presenter.view.LookTaskAuditView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class LookTaskAuditPresenter @Inject constructor(): BasePresenter<LookTaskAuditView>(){

    @Inject
    lateinit var service: UserService

    fun putLookTaskAudit(req: LookTaskAuditReq?) {
        mView?.showLoading()
        service.putLookTaskAudit(req)
            .execute(object :BaseSubscriber<Any>(mView){
                override fun onNext(t: Any) {
                    super.onNext(t)
                    mView?.onLookTaskAuditSuccess()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView?.onLookTaskAuditSuccess()
                    }
                }
            },lifecycleProvider)
    }
}
