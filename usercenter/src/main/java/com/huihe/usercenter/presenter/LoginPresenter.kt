package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.LoginView
import com.huihe.usercenter.service.UserService
import com.huihe.usercenter.utils.MessageService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.provider.event.ErrorEntity
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {

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
            .flatMap(Function<String?, Observable<ErrorEntity?>> {
                var messageContent: ErrorEntity? = null
                messageService.login(
                    account,
                    it, object : MessageService.OnMessageListener {
                        override fun onLoginSuccess() {
                            messageContent = ErrorEntity("登录成功", ErrorEntity.TYPE_LOGIN_SUCCESS)
                            messageContent?.token = it
                        }

                        override fun onLoginFail(message: String, code: Int) {
                            messageContent = ErrorEntity(message, code)
                        }
                    })
                return@Function Observable.fromArray(messageContent)
            })
            .execute(object : BaseSubscriber<ErrorEntity?>(mView) {
                override fun onNext(t: ErrorEntity?) {
                    super.onNext(t)
                    if (t?.code == ErrorEntity.TYPE_LOGIN_SUCCESS) {
                        mView?.onLoginResult(t?.token ?: "")
                    } else {
                        mView?.onError(t?.error ?: "")
                    }
                }
            }, lifecycleProvider)
    }
}
