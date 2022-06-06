package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.MineLookTaskRep
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.view.MineLookTaskView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import com.kotlin.base.utils.AppPrefsUtils
import io.reactivex.Observable

import javax.inject.Inject
import io.reactivex.functions.Function

class MineLookTaskPresenter @Inject constructor(): BasePresenter<MineLookTaskView>() {

    @Inject
    lateinit var service: UserService

    fun getLookTaskList(status: Int,type: Int?,pageNo: Int,pageSize: Int) {
        var isAdministrators = AppPrefsUtils.getString(BaseConstant.KEY_LEVELS)
        if (isAdministrators.isNullOrEmpty()) {
            service?.getUserLevels()
                .flatMap(Function<Int?, Observable<MineLookTaskRep?>> {
                    AppPrefsUtils.putString(BaseConstant.KEY_LEVELS,"${if (it != null && it <= 3) UserModule.UserLevels.Administrators else UserModule.UserLevels.Staff}")
                    return@Function if (it != null && it > 3) {
                        service.getLookTaskStaffList(status,type,pageNo,pageSize)
                    } else {
                        service.getLookTaskAdministratorsList(status,type,pageNo,pageSize)
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
        } else {
            if ("$isAdministrators" == UserModule.UserLevels.Administrators.toString()) {
                service.getLookTaskAdministratorsList(status,type,pageNo,pageSize)
            } else {
                service.getLookTaskStaffList(status,type,pageNo,pageSize)
            }.execute(object : BaseSubscriber<MineLookTaskRep?>(mView) {
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

}