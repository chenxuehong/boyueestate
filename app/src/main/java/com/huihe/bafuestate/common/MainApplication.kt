package com.huihe.bafuestate.common

import android.widget.Toast
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.bafuestate.R
import com.huihe.bafuestate.push.CustomNotification
import com.huihe.bafuestate.push.MyMobPushCallback
import com.huihe.bafuestate.push.MyMobPushReceiver
import com.huihe.bafuestate.share.ShareSdkUtil
import com.kotlin.base.common.AppManager
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.event.LoginEvent
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.event.*
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.utils.UserPrefsUtils
import com.mob.MobSDK
import com.mob.pushsdk.MobPush
import com.bafuestate.message.TUIKit
import com.bafuestate.message.base.IMApplication
import com.bafuestate.message.base.IUIKitCallBack
import com.bafuestate.message.utils.ThirdPushTokenMgr
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import java.util.HashMap
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
        Bus.observe<ShareEvent>()
            .subscribe {
                showShare(it)
            }.registerInBus(this)
        Bus.observe<ChatEvent>()
            .subscribe {
                chat(it)
            }.registerInBus(this)
        Bus.observe<PushIMEvent>()
            .subscribe {
                ThirdPushTokenMgr.getInstance().setPushTokenToTIM()
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
        MobSDK.init(this, "m328e6238ef1ce", "46219d640d2c3ea61a23555409c83b78")
        MobPush.setNotifyIcon(R.drawable.splash)
        MobPush.setShowBadge(true)
        myMobPushReceiver = MyMobPushReceiver()
        MobPush.setTailorNotification(CustomNotification::class.java)
        MobPush.addPushReceiver(myMobPushReceiver)
        MobPush.getRegistrationId(MyMobPushCallback())
        MobSDK.submitPolicyGrantResult(true, null)
    }

    private fun showShare(
        share: ShareEvent
    ) {
        if (0 == share.type) {
            ShareSdkUtil.shareWechat(
                share.title,
                share.content,
                share.url,
                share.imagePath,
                share.imageUrl,
                object : PlatformActionListener {
                    override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {

                    }

                    override fun onCancel(p0: Platform?, p1: Int) {
                       runOnUiThread {
                           toast("已取消")
                       }
                    }

                    override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {

                        runOnUiThread {
                            toast(p2?.message ?: "")
                        }
                    }

                })
        } else {
            ShareSdkUtil.shareWechatMoments(share.title,
                share.content,
                share.url,
                share.imagePath,
                share.imageUrl,
                object : PlatformActionListener {
                    override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {

                    }

                    override fun onCancel(p0: Platform?, p1: Int) {

                        runOnUiThread {
                            toast("已取消")
                        }
                    }

                    override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
                        runOnUiThread {
                            toast(p2?.message ?: "")
                        }
                    }

                })
        }

    }

    private fun chat(it: ChatEvent?) {
        TUIKit.startChat(AppManager.instance.currentActivity(), it?.id ?: "", it?.chatName ?: "")
    }

    override fun onTerminate() {
        Bus.unregister(this)
        if (myMobPushReceiver != null) {
            MobPush.removePushReceiver(myMobPushReceiver)
        }
        super.onTerminate()
    }
}
