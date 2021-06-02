package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.LoginView
import com.huihe.usercenter.service.UserService
import com.huihe.usercenter.utils.MessageService
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.provider.data.protocol.IMUserInfo
import com.kotlin.provider.event.ErrorEntity
import com.kotlin.provider.utils.UserPrefsUtils
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {

    val TAG:String= LoginPresenter::class.java.simpleName
    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var messageService: MessageService

    /*
      登录
   */
    fun login(account: String, password: String, type: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.login(account, password, type)
            .compose(lifecycleProvider.bindToLifecycle())
            .flatMap(Function<String?, Observable<IMUserInfo?>> {
                AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, it)
                LogUtils.d(TAG,"user login->token ${it}")
                               return@Function userService.getUserInfoFormIm()
            })
            .execute(object : BaseSubscriber<IMUserInfo?>(mView) {
                override fun onNext(iMUserInfo: IMUserInfo?) {
                    super.onNext(iMUserInfo)
                    UserPrefsUtils.putUserInfo(iMUserInfo)
                    LogUtils.d(TAG,"getUserInfo ->uid: ${iMUserInfo?.uid} , userSig: ${iMUserInfo?.userSig}")
                    messageService.login(
                        iMUserInfo?.uid?:"",
                        iMUserInfo?.userSig?:"", object : MessageService.OnMessageListener {
                            override fun onLoginSuccess() {
                                LogUtils.d(TAG,"message login success")
                            }

                            override fun onLoginFail(message: String, code: Int) {
                                LogUtils.d(TAG,"message login fail: code = ${code},message = ${message}")
                                mView?.onError(message)
                            }
                        }
                    )

                }

                override fun onComplete() {
                    super.onComplete()
                    mView?.onLoginResult("login")
                }
            }, lifecycleProvider)


    }

    fun onDestory() {
        messageService?.onDestory()
    }
}
