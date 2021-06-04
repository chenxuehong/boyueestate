package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.SettingPresenter
import com.huihe.usercenter.presenter.view.SettingView
import com.huihe.usercenter.ui.activity.AboutusActivity
import com.kotlin.base.common.AppManager
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.DataCleanManager
import com.kotlin.provider.event.CloseMainActvityEvent
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.fragment_setting.*
import org.jetbrains.anko.support.v4.startActivity
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

class SettingFragment : BaseMvpFragment<SettingPresenter>(), SettingView {

    var mLDialog: LDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_setting, container!!)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mIvAboutUs.onClick {
            startActivity<AboutusActivity>()
        }
        mIvClearCache.onClick {
            DataCleanManager.clearAllCache(context)
            initData()
        }
        tvLogout.onClick {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        mLDialog = LDialog.init(childFragmentManager)
            .setLayoutRes(R.layout.layout_logout_dialog)
            .setBackgroundDrawableRes(R.drawable.common_white_radius_bg)
            .setGravity(Gravity.CENTER)
            .setWidthScale(0.75f)
            .setViewHandlerListener(object : ViewHandlerListener() {
                override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                    holder.getView<TextView>(R.id.dialog_tip_ok).onClick {
                        dialog.dismiss()
                        mPresenter.logout()
                    }
                    holder.getView<TextView>(R.id.dialog_tip_cancel).onClick {
                        dialog.dismiss()

                    }
                }
            }).show()
    }

    private fun initData() {
        mIvClearCache.setContent(DataCleanManager.getTotalCacheSize(context))
    }

    override fun onDestroy() {
        try {
            mLDialog?.dismiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    override fun onLogout() {
        UserPrefsUtils.putUserInfo(null)
        AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, "")
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
        activity?.finish()
        Bus.send(CloseMainActvityEvent())
    }
}
