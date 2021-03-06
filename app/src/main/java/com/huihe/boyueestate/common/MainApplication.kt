package com.huihe.boyueestate.common

import android.content.Context
import android.widget.Toast
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.boyueestate.R
import com.huihe.boyueestate.push.CustomNotification
import com.huihe.boyueestate.push.MyMobPushCallback
import com.huihe.boyueestate.push.MyMobPushReceiver
import com.huihe.boyueestate.share.ShareSdkUtil
import com.huihe.boyueestate.utils.HotfixManager
import com.kotlin.base.common.AppManager
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.event.LogoutEvent
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.event.*
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.utils.UserPrefsUtils
import com.mob.MobSDK
import com.mob.pushsdk.MobPush
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IMApplication
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import com.tencent.qcloud.tim.uikit.utils.ThirdPushTokenMgr
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import java.util.*
import java.util.concurrent.TimeUnit


class MainApplication : IMApplication() {

    private var myMobPushReceiver: MyMobPushReceiver? = null
    override fun onCreate() {
        super.onCreate()
        Bus.observe<LogoutEvent>()
            .subscribe {
                delayLogin()
            }.registerInBus(this)
        Bus.observe<MessageLoginEvent>()
            .subscribe {
                imLogin()
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
        //?????????SDK????????????????????????context???????????????ApplicationContext
        SDKInitializer.initialize(this);
        //???4.3.0??????????????????SDK????????????????????????????????????????????????????????????????????????????????????????????????.
        //??????BD09LL???GCJ02????????????????????????BD09LL?????????
        SDKInitializer.setCoordType(CoordType.BD09LL)
        initModePush()
        try {
//            HotfixManager.getInstance(this).init(BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG)
        } catch (throwable: Throwable) {
            //
        }
    }

    private fun imLogin() {
        var userInfo = UserPrefsUtils.getUserInfo()
        if (userInfo!=null) {
            TUIKit.login(
                userInfo.uid,
                userInfo.userSig,
                object : IUIKitCallBack {
                    override fun onSuccess(data: Any) {
                        Bus.send(ErrorEntity("????????????", ErrorEntity.TYPE_LOGIN_SUCCESS))
                    }

                    override fun onError(module: String, errCode: Int, errMsg: String) {
                        Bus.send(ErrorEntity(module, errCode))
                    }
                }
            )
        }
    }

    private fun delayLogin() {
        Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show()
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
                AppPrefsUtils.putString(BaseConstant.KEY_LEVELS,"")
                ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
            }
        })
    }

    private fun initModePush() {
        MobSDK.init(this, "3270be52b22ee", "872ca12218b61bc3b588d795fc52b3da")
        MobPush.setNotifyIcon(R.drawable.app_icon)
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
                           toast("?????????")
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
                            toast("?????????")
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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        try {
            HotfixManager.installDependency()
        } catch (throwable: Throwable) {
            //
        }
    }

    override fun onTerminate() {
        Bus.unregister(this)
        if (myMobPushReceiver != null) {
            MobPush.removePushReceiver(myMobPushReceiver)
        }
        try {
            HotfixManager.getInstance(this).unInit()
        } catch (throwable: Throwable) {
            //
        }
        super.onTerminate()
    }
}
