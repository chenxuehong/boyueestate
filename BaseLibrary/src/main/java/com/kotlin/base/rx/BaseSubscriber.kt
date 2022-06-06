package com.kotlin.base.rx

import com.eightbitlab.rxbus.Bus
import com.kotlin.base.event.LogoutEvent
import com.kotlin.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/*
    Rx订阅者默认实现
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Observer<T> {

    override fun onSubscribe(p0: Disposable) {
    }

    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        when (e) {
            is BaseException -> when (e.msg) {
                "登录超时" -> {
                    Bus.send(LogoutEvent())
                }
                else -> {
                    baseView.onError(e.msg)
                }
            }
            is DataNullException -> baseView.onDataIsNull()
            else -> baseView.onError("网络异常")
        }
    }
}
