package com.huihe.usercenter.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.AboutUsPresenter
import com.huihe.usercenter.presenter.view.AboutUsView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.WebViewActivity
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.DeviceUtils
import kotlinx.android.synthetic.main.fragment_about_us.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class AboutUsFragment : BaseMvpFragment<AboutUsPresenter>(), AboutUsView {

    var isCheckUpdate = false
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_about_us, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        val versionName = DeviceUtils.getVersionName(context)
        tvVersion.text = "当前版本: ${versionName}"
        tvServerVersion.text = versionName
        llCheckUpdate.onClick {
            checkUpdate()
        }
    }

    private fun initData() {
        isCheckUpdate = false
        mPresenter.getServerVersionInfo()
    }

    private fun checkUpdate() {
        isCheckUpdate = true
        mPresenter.getServerVersionInfo()
    }

    override fun onServerAppVersion(serverAppVersion: String) {
        var versionCode = DeviceUtils.getVersionCode(context!!)
        try {

            if (versionCode < serverAppVersion.toInt()) {
                // 有新版本
                if (isCheckUpdate) {

                    var intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("http://download.housevip.cn/rf15"))
                    startActivity(intent)
                } else {
                    tvCheckUpdateTitle.text = "有新版本"
                }

            } else {
                if (isCheckUpdate) {
                    toast("已经是最新版本!")
                } else {
                    tvCheckUpdateTitle.text = "已经是最新版本"
                }
            }
        } catch (e: Exception) {
        }

    }
}
