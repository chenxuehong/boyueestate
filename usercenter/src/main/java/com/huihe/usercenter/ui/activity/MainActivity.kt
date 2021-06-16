package com.huihe.usercenter.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.SetPushRep
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MainPresenter
import com.huihe.usercenter.presenter.view.MainView
import com.huihe.usercenter.ui.fragment.MeFragment
import com.kotlin.base.common.AppManager
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.DeviceUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.provider.data.protocol.ServerVersionInfo
import com.kotlin.provider.event.MessageBadgeEvent
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder
import java.util.*

@Route(path = RouterPath.UserCenter.PATH_MAIN)
class MainActivity : BaseMvpActivity<MainPresenter>(), MainView {

    val TAG: String = MainActivity::javaClass.name
    lateinit var mCheckUpdateDialog: LDialog
    private var pressTime: Long = 0

    //Fragment 栈管理
    private val mStack = Stack<Fragment>()

    //首页Fragment
    private val mHomeFragment by lazy {
        ARouter.getInstance()
            .build(RouterPath.HomeCenter.PATH_HOME)
            .navigation() as Fragment
    }

    //地图Fragment
    private val mHouseMapTitleFragment by lazy {
        ARouter.getInstance()
            .build(RouterPath.HomeCenter.PATH_HOUSE_MAP_FRAGMENT)
            .navigation() as Fragment
    }

    //客源Fragment
    private val mCustomerFragment by lazy {
        ARouter.getInstance()
            .build(RouterPath.CustomerCenter.PATH_CUSTOMER)
            .navigation() as Fragment
    }

    //消息Fragment
    private val mMsgFragment by lazy {
        ARouter.getInstance()
            .build(RouterPath.MessageCenter.PATH_MESSAGE)
            .navigation() as Fragment
    }

    //"我的"Fragment
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
        initBottomNav()
        changeFragment(0)
        initObserve()
        initData()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mBottomNavBar.selectTab(0)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    /*
      初始化Fragment栈管理
   */
    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContaier, mHomeFragment)
        manager.add(R.id.mContaier, mHouseMapTitleFragment)
        manager.add(R.id.mContaier, mCustomerFragment)
        manager.add(R.id.mContaier, mMsgFragment)
        manager.add(R.id.mContaier, mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mHouseMapTitleFragment)
        mStack.add(mCustomerFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }

    /*
        初始化底部导航切换事件
     */
    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
        })

        mBottomNavBar.checkMsgBadge(false)
    }

    /*
        切换Tab，切换对应的Fragment
     */
    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }

    /*
        初始化监听，消息标签是否显示
     */
    private fun initObserve() {
        Bus.observe<MessageBadgeEvent>()
            .subscribe { t: MessageBadgeEvent ->
                run {
                    mBottomNavBar.checkMsgBadge(t.isVisible)
                }
            }.registerInBus(this)
    }

    private fun initData() {
        var userInfo = UserPrefsUtils.getUserInfo()
        mPresenter.setPushInfo(
            userInfo?.uid,
            AppPrefsUtils.getString(BaseConstant.KEY_SP_REGISTRATIONID)
        )
        mPresenter.getServerVersionInfo()
    }

    override fun onPushSuccess(t: SetPushRep?) {
        LogUtils.i(TAG, "onPushSuccess")
    }

    override fun onServerAppVersion(serverAppVersion: ServerVersionInfo?) {
        var versionCode = DeviceUtils.getVersionCode(this)
        try {
            var version = serverAppVersion?.version
            if (versionCode < version?.toFloat()!!) {
                // 有新版本
                showCheckUpdateTip(serverAppVersion)
            } else {
                toast("已经是最新版本!")
            }
        } catch (e: Exception) {
        }
    }

    private fun showCheckUpdateTip(serverAppVersion: ServerVersionInfo?) {
        mCheckUpdateDialog = LDialog.init(supportFragmentManager)
            .setLayoutRes(R.layout.dialog_apk_update)
            .setBackgroundDrawableRes(R.drawable.common_white_radius_bg)
            .setGravity(Gravity.CENTER)
            .setCancelableOutside(false)
            .setWidthScale(0.75f)
            .setHeightDp(250f)
            .setViewHandlerListener(object : ViewHandlerListener() {
                override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {

                    var log = serverAppVersion?.changelog ?: "暂无更新日志"
                    holder.getView<TextView>(R.id.dialog_apk_update_tv_title).text = resources.getString(R.string.New_version_found)
                    holder.getView<TextView>(R.id.dialog_apk_update_tv_versionInfo).text = log
                    holder.getView<TextView>(R.id.dialog_apk_update_iv_close).onClick {
                        dialog.dismiss()
                    }
                    holder.getView<TextView>(R.id.dialog_apk_update_tv_update).onClick {
                        dialog.dismiss()
                        var intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(serverAppVersion?.update_url?:""))
                        startActivity(intent)
                    }
                }
            }).show()
    }

    /*
        取消Bus事件监听
     */
    override fun onDestroy() {
        super.onDestroy()
        try {
            Bus.unregister(this)
            mCheckUpdateDialog?.dismiss()
        } catch (e: Exception) {
        }
    }

    /*
        重写Back事件，双击退出
     */
    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }


}
