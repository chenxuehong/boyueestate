package com.huihe.usercenter.presenter

import android.text.TextUtils
import com.huihe.usercenter.presenter.view.LoginView
import com.huihe.commonservice.service.user.UserService
import com.huihe.usercenter.utils.MessageService
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.LogUtils
import com.huihe.boyueentities.protocol.common.IMUserInfo
import com.kotlin.base.data.net.error.RetryWithDelay
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

    private lateinit var token:String
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
            .retryWhen(RetryWithDelay(2,500))
            .flatMap(Function<String?, Observable<IMUserInfo?>> {
                token = it
                LogUtils.d(TAG,"user login->token ${it}")
                               return@Function userService.getUserInfoFormIm()
            })
            .execute(object : BaseSubscriber<IMUserInfo?>(mView) {
                override fun onNext(iMUserInfo: IMUserInfo?) {
                    super.onNext(iMUserInfo)
                    LogUtils.d(TAG,"getUserInfo ->iMUserInfo: ${iMUserInfo}")
                    if (iMUserInfo!=null
                        && !TextUtils.isEmpty(iMUserInfo?.uid)
                        && !TextUtils.isEmpty(iMUserInfo?.userSig)) {
                        AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, token)
                        UserPrefsUtils.putUserInfo(iMUserInfo)
                        LogUtils.d(TAG,"getUserInfo ->uid: ${iMUserInfo?.uid} , userSig: ${iMUserInfo?.userSig}")
                        messageService.login(object : MessageService.OnMessageListener {
                                override fun onLoginSuccess() {
                                    LogUtils.d(TAG,"message login success")
                                }

                                override fun onLoginFail(message: String, code: Int) {
                                    LogUtils.d(TAG,"message login fail: code = ${code},message = ${message}")
                                    mView?.onError(message)
                                }
                            }
                        )
                        mView?.onLoginResult("登录成功")
                    } else {
                        mView?.onError("网络异常")
                    }
                }

            }, lifecycleProvider)


    }

    fun onDestory() {
        messageService?.onDestory()
    }
}
