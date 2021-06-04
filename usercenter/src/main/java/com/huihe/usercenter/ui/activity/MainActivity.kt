package com.huihe.usercenter.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.provider.event.CloseMainActvityEvent
import com.kotlin.provider.event.MessageBadgeEvent
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

@Route(path = RouterPath.UserCenter.PATH_MAIN)
class MainActivity : BaseMvpActivity<MainPresenter>(),MainView {

    val TAG:String = MainActivity::javaClass.name
    private var pressTime: Long = 0
    //Fragment 栈管理
    private val mStack = Stack<Fragment>()
    //主界面Fragment
    private val mHomeFragment by lazy {
        ARouter.getInstance()
            .build(RouterPath.HomeCenter.PATH_HOME)
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
        manager.add(R.id.mContaier, mCustomerFragment)
        manager.add(R.id.mContaier, mMsgFragment)
        manager.add(R.id.mContaier, mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
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
        Bus.observe<CloseMainActvityEvent>()
            .subscribe { t: CloseMainActvityEvent ->
                run {
                    changeFragment(0)
                }
            }.registerInBus(this)
    }

    private fun initData() {
        var userInfo = UserPrefsUtils.getUserInfo()
        mPresenter.setPushInfo(
            userInfo?.uid,
            AppPrefsUtils.getString(BaseConstant.KEY_SP_REGISTRATIONID)
        )
    }

    override fun onPushSuccess(t: SetPushRep?) {
        LogUtils.i(TAG,"onPushSuccess")
    }

    /*
        取消Bus事件监听
     */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
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
