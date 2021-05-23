package com.huihe.usercenter.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.darsh.multipleimageselect.helpers.Constants
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.UserInfo
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MePresenter
import com.huihe.usercenter.presenter.view.MeView
import com.huihe.usercenter.ui.activity.*
import com.huihe.usercenter.ui.widget.MeItemView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.loadHeadUrl
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.router.RouterPath
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MeFragment : BaseMvpFragment<MePresenter>(), MeView {

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
        return initInflater(context!!, R.layout.fragment_me, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        var childCount = llMeManager.childCount
        for (i in 0 until childCount) {
            var childAt = llMeManager.getChildAt(i)
            if (childAt is MeItemView) {
                childAt.onClick {
                    onItemClicked(childAt.titleContent)
                }
            }
        }
        flMeScan.onClick {
            val intent = Intent(context, CaptureActivity::class.java)
            startActivityForResult(intent, Constants.REQUEST_CODE)
        }
    }

    private fun initData() {
        mPresenter?.getUserInfo()
    }

    override fun onUserInfo(t: UserInfo?) {
        ivMeHead.loadHeadUrl(t?.avatarUrl ?: "")
        tvMeUserName.text = t?.userName ?: ""
        tvMeDeptName.text = t?.deptName ?: ""
    }

    private fun onItemClicked(titleContent: String?) {
        when (titleContent) {
            resources.getString(R.string.area_manager) -> {
                startActivity<CommunityManagerActivity>()
            }
            resources.getString(R.string.district_manager) -> {
                startActivity<DistrictActivity>()
            }
            resources.getString(R.string.address_book) -> {
                startActivity<AddressBookActivity>()
            }
            resources.getString(R.string.corporate_culture) -> {
                startActivity<CorporateCultureActivity>()
            }
            resources.getString(R.string.setting) -> {
                startActivity<SettingActivity>()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                var bundle: Bundle? = data.extras ?: return
                if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    var result = bundle.getString(CodeUtils.RESULT_STRING);
//                    toast("解析结果:${result}")
                    result?.apply {
                        ARouter.getInstance()
                            .build(RouterPath.HomeCenter.PATH_HOUSE_DETIL)
                            .withString(HomeConstant.KEY_HOUSE_ID, this)
                            .navigation()
                    }
                } else if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    toast("解析二维码失败")
                }
            }
        }
    }
}
