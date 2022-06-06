package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.SchoolDistrictRep
import com.huihe.usercenter.presenter.view.DistrictView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import javax.inject.Inject

class DistrictPresenter @Inject constructor() : BasePresenter<DistrictView>(){

    @Inject
    lateinit var service: UserService

    fun getSchoolDistrictList(page:Int=1,limit:Int = 30) {
        if (!checkNetWork()){
            return
        }
        service.getSchoolDistrictList(page,limit)
            .execute(object :BaseSubscriber<SchoolDistrictRep?>(mView){

                override fun onNext(t: SchoolDistrictRep?) {
                    super.onNext(t)
                    mView?.onSchoolDistrictList(t?.list)
                }
            },lifecycleProvider)
    }
}
