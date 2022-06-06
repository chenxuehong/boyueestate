package com.huihe.usercenter.presenter

import com.huihe.boyueentities.protocol.user.SignReq
import com.huihe.usercenter.presenter.view.SignView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import javax.inject.Inject

class SignPresenter @Inject constructor(): BasePresenter<SignView>(){

    @Inject
    lateinit var service: UserService
    fun getUploadToken() {
        mView?.showLoading("正在上传...")
        service.getUploadToken()
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onGetUploadTokenResult(t)
                }
            }, lifecycleProvider)
    }

    fun sign(req: SignReq?, isTakeLook: Boolean) {
        if (isTakeLook){
            service.takeSign(req)
        }else{
            service.accompanySign(req)
        }.execute(object:BaseSubscriber<Any>(mView){

            override fun onNext(t: Any) {
                super.onNext(t)
                mView?.onSignSuccess()
            }
            override fun onError(e: Throwable) {
                super.onError(e)
                if (e is DataNullException){
                    mView?.onSignSuccess()
                }
            }
        },lifecycleProvider)

    }

}
