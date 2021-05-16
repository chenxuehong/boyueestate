package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.UserInfo
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MePresenter
import com.huihe.usercenter.presenter.view.MeView
import com.huihe.usercenter.ui.activity.*
import com.huihe.usercenter.ui.widget.MeItemView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity

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
    }

    private fun initData() {
        mPresenter?.getUserInfo()
    }

    override fun onUserInfo(t: UserInfo?) {
        ivMeHead.loadUrl(t?.avatarUrl?:"",R.drawable.is_empty)
        tvMeUserName.text = t?.userName?:""
        tvMeDeptName.text = t?.deptName?:""
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
}
