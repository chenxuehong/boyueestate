package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MineLookHouseFollowPresenter
import com.huihe.usercenter.presenter.view.MineLookHouseFollowView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.UserConstant
import kotlinx.android.synthetic.main.fragment_mine_looktask_follow.*
import kotlinx.android.synthetic.main.layout_mine_looktask_item.view.*
import org.jetbrains.anko.support.v4.toast

class MineLookHouseFollowFragment :
    BaseMvpFragment<MineLookHouseFollowPresenter>(), MineLookHouseFollowView {

    var takeLookId:String? = null
    var customerCode:String? = null

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_mine_looktask_follow, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takeLookId = arguments?.getString(UserConstant.KEY_ID)
        customerCode = arguments?.getString(UserConstant.KEY_CUSTOMER_CODE)
        initView()
    }

    private fun initView() {
        val colorText = "<font color = \"#FF0000\">*</font> ${resources.getString(R.string.summary_follow)}"
        tvFollowTitle.text =  Html.fromHtml(colorText)
        tvAddFollowContent.onClick {
            if (checkInput()){
//                mPresenter?.lookTaskFollow
            }
        }
    }

    private fun checkInput(): Boolean {
        if (etFollowContent.text.toString().isNullOrEmpty()){
            toast(resources.getString(R.string.summary_follow_input))
            return false
        }
        return true
    }
}