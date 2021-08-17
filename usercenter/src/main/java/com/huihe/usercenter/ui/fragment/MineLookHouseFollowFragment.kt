package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.MineLookHouseFollowReq
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MineLookHouseFollowPresenter
import com.huihe.usercenter.presenter.view.MineLookHouseFollowView
import com.huihe.usercenter.ui.activity.MineLookTaskDetailActivity
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.LookTaskEvent
import com.kotlin.provider.event.MeRefreshEvent
import kotlinx.android.synthetic.main.fragment_mine_looktask_follow.*
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
                mPresenter?.lookTaskFollow(MineLookHouseFollowReq(customerCode,takeLookId,etFollowContent.text.toString()))
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

    override fun onLookHouseFollowSuccess() {
        Bus.send(LookTaskEvent(null))
        Bus.send(MeRefreshEvent())
        AppManager.instance.finishActivity(MineLookTaskDetailActivity::class.java)
        AppManager.instance.finishActivity(activity!!)
    }
}