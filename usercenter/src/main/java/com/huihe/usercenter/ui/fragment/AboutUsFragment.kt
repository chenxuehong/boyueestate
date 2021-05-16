package com.huihe.usercenter.ui.fragment

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
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.DeviceUtils
import kotlinx.android.synthetic.main.fragment_about_us.*

class AboutUsFragment : BaseMvpFragment<AboutUsPresenter>(), AboutUsView {

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
        return initInflater(context!!, R.layout.fragment_about_us,container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val versionName = DeviceUtils.getVersionName(context)
        tvVersion.text = "当前版本: ${versionName}"
        tvVersio2.text = versionName
        llCheckUpdate.onClick {

        }
    }
}
