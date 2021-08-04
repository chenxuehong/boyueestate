package com.huihe.usercenter.presenter

import com.huihe.usercenter.data.protocol.MineLookTaskRep
import com.huihe.usercenter.presenter.view.MineLookTaskView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import io.reactivex.Observable

import javax.inject.Inject
import io.reactivex.functions.Function

class MineLookTaskPresenter @Inject constructor(): BasePresenter<MineLookTaskView>() {

    @Inject
    lateinit var service: UserService

    fun getLookTaskList(status: Int,pageNo: Int,pageSize: Int) {
        service?.getUserLevels()
            .flatMap(Function<Int?, Observable<MineLookTaskRep?>> {

                return@Function if (it != null && it > 3) {
                    service.getLookTaskStaffList(status,pageNo,pageSize)
                } else {
                    service.getLookTaskAdministratorsList(status,pageNo,pageSize)
                }
            }).execute(object : BaseSubscriber<MineLookTaskRep?>(mView) {
                override fun onNext(t: MineLookTaskRep?) {
                    super.onNext(t)
                    mView?.onLookTaskList(
                        t?.list
                    )
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException) {
                        mView?.onLookTaskList(mutableListOf())
                    }
                }
            }, lifecycleProvider)
    }

}