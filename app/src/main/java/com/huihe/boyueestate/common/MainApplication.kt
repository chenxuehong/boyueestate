package com.huihe.boyueestate.common

import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.boyueestate.R
import com.huihe.boyueestate.push.CustomNotification
import com.huihe.boyueestate.push.MyMobPushCallback
import com.huihe.boyueestate.push.MyMobPushReceiver
import com.huihe.usercenter.utils.UserPrefsUtils
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.event.LoginEvent
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.event.ErrorEntity
import com.kotlin.provider.event.MessageLoginEvent
import com.kotlin.provider.router.RouterPath
import com.mob.MobSDK
import com.mob.pushsdk.MobPush
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IMApplication
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class MainApplication : IMApplication() {

    private var myMobPushReceiver: MyMobPushReceiver? = null
    override fun onCreate() {
        super.onCreate()
        Bus.observe<LoginEvent>()
            .subscribe {
                delayLogin()
            }.registerInBus(this)
        Bus.observe<MessageLoginEvent>()
            .subscribe {
                imLogin(it)
            }.registerInBus(this)
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL)
        initModePush()
    }

    private fun imLogin(messageLoginEvent: MessageLoginEvent) {
        TUIKit.login(
            messageLoginEvent.userId,
            messageLoginEvent.userSign,
            object : IUIKitCallBack {
                override fun onSuccess(data: Any) {
                    Bus.send(ErrorEntity("登录成功", ErrorEntity.TYPE_LOGIN_SUCCESS))
                }

                override fun onError(module: String, errCode: Int, errMsg: String) {
                    Bus.send(ErrorEntity(module, errCode))
                }
            }
        )
    }

    private fun delayLogin() {
        Toast.makeText(this, "登录超时", Toast.LENGTH_LONG).show()
        Observable.timer(1, TimeUnit.SECONDS).subscribe(object : Observer<Long> {
            override fun onError(e: Throwable) {
            }

            override fun onNext(t: Long) {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onComplete() {
                UserPrefsUtils.putUserInfo(null)
                AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, "")
                ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
            }
        })
    }

    private fun initModePush() {
        MobSDK.init(this, "31bf832473abc", "fe9719c1e197caa5d6b7716006410414")
        MobPush.setNotifyIcon(R.drawable.splash)
        MobPush.setShowBadge(true)
        myMobPushReceiver = MyMobPushReceiver()
        MobPush.setTailorNotification(CustomNotification::class.java)
        MobPush.addPushReceiver(myMobPushReceiver)
        MobPush.getRegistrationId(MyMobPushCallback())
        MobSDK.submitPolicyGrantResult(true, null)
    }

    override fun onTerminate() {
        Bus.unregister(this)
        if (myMobPushReceiver != null) {
            MobPush.removePushReceiver(myMobPushReceiver)
        }
        super.onTerminate()
    }
}
