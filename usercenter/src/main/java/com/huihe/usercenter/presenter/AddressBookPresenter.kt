package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.DeptUserRep
import com.huihe.usercenter.presenter.view.AddressBookView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class AddressBookPresenter @Inject constructor(): BasePresenter<AddressBookView>(){

    @Inject
    lateinit var service: UserService

    fun getDeptUsers() {
        if (!checkNetWork()) {
            return
        }
        service.getDeptUsers()
            .execute(object :BaseSubscriber<MutableList<DeptUserRep>?>(mView){

                override fun onNext(t: MutableList<DeptUserRep>?) {
                    super.onNext(t)
                    mView?.onDeptUsersResult(t)
                }
            },lifecycleProvider)
    }
}
