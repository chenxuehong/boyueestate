package com.huihe.boyueestate.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.huihe.boyueestate.R
import com.huihe.boyueestate.injection.component.DaggerSplashComponent
import com.huihe.boyueestate.injection.module.SplashModule
import com.huihe.boyueestate.presenter.SplashPresenter
import com.huihe.boyueestate.presenter.view.SplashView
import com.huihe.boyueestate.utils.HotfixManager
import com.huihe.usercenter.ui.activity.MainActivity
import com.huihe.usercenter.utils.MessageService
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.GlideUtils
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SplashActivity : BaseMvpActivity<SplashPresenter>(),
    SplashView {

    @Inject
    lateinit var messageService: MessageService
    override fun injectComponent() {
        DaggerSplashComponent.builder().activityComponent(mActivityComponent).splashModule(
            SplashModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // 热更新
        HotfixManager.getInstance(applicationContext).checkHotfix()
        mPresenter?.getSplashBanner()
        afterLogin {
            messageService.login(null)
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_MAIN).navigation()
        }
        delayFinish();
    }

    private fun delayFinish() {
        ivSplashBanner.postDelayed({
            finish()
        }, 3000)
    }

    override fun onBanner(banner: String) {
        GlideUtils.loadImage2(this,banner,ivSplashBanner)
    }

}
