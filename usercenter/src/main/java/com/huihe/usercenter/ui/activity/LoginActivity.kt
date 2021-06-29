package com.huihe.usercenter.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.huihe.usercenter.R
import com.huihe.usercenter.ext.enable
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.LoginPresenter
import com.huihe.usercenter.presenter.view.LoginView
import com.kotlin.base.common.AppManager
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.BuildConfig
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    var mCodeLDialog:LDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    override fun injectComponent() {

        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    private fun initView() {

        mloginBtn.enable(mMobileEt) { isBtnEnable() }
        mloginBtn.enable(mPwdEt) { isBtnEnable() }
        mloginBtn.onClick(this)
    }

    /*
          判断按钮是否可用
       */
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mloginBtn -> {
                mPresenter.login(mMobileEt.text.toString().trim(), mPwdEt.text.toString().trim(),"mobile")
            }
        }
    }

    override fun onBackPressed() {
        AppManager.instance.exitApp(this)
    }

    override fun onLoginResult(result: String) {
        showEnterCode()
    }

    private fun showEnterCode() {
        mCodeLDialog = LDialog.init(supportFragmentManager)
            .setLayoutRes(R.layout.layout_input_code_dialog)
            .setBackgroundDrawableRes(R.drawable.common_white_radius_bg)
            .setGravity(Gravity.CENTER)
            .setWidthScale(0.75f)
            .setViewHandlerListener(object : ViewHandlerListener() {
                override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                    var etCode = holder.getView<TextView>(R.id.etCode)
                    holder.getView<TextView>(R.id.tvConfirm).onClick {
                        if (etCode.text.trim() != BuildConfig.CompanyCode){
                            toast("请输入正确的公司编码!")
                            return@onClick
                        }
                        dialog.dismiss()
                        mloginBtn.postDelayed(Runnable {
                            toast("登录成功")
                            ARouter.getInstance().build(RouterPath.UserCenter.PATH_MAIN).navigation()
                        },500)
                    }
                }
            }).show()

    }

    override fun onDestroy() {
        try {
            mPresenter?.onDestory()
            mCodeLDialog?.dismiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
