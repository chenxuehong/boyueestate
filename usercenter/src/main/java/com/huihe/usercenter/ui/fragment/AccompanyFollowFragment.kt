package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.LookHouseAccompanyFollowReq
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.AccompanyFollowPresenter
import com.huihe.usercenter.presenter.view.AccompanyFollowView
import com.huihe.usercenter.ui.activity.MineLookTaskDetailActivity
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.LookTaskEvent
import com.kotlin.provider.event.MeRefreshEvent
import com.kotlin.provider.event.RefreshLookTaskDetailEvent
import kotlinx.android.synthetic.main.fragment_accompany_follow.*
import org.jetbrains.anko.support.v4.toast

class AccompanyFollowFragment : BaseMvpFragment<AccompanyFollowPresenter>(), AccompanyFollowView{

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
        return initInflater(context!!, R.layout.fragment_accompany_follow, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takeLookId = arguments?.getString(UserConstant.KEY_ID)
        customerCode = arguments?.getString(UserConstant.KEY_CUSTOMER_CODE)
        initView()
    }

    private fun initView() {
        val colorText = "<font color = \"#FF0000\">*</font> ${resources.getString(R.string.looktask_AccompanyFollow)}"
        tvAccompanyTitle.text =  Html.fromHtml(colorText)
        tvAddAccompanyContent.onClick {
            if (checkInput()){
                mPresenter.lookHouseAccompanyFollow(LookHouseAccompanyFollowReq(customerCode,etAccompanyContent.text.toString(),takeLookId))
            }
        }
    }

    private fun checkInput(): Boolean {
        if (etAccompanyContent.text.toString().isNullOrEmpty()){
            toast(resources.getString(R.string.looktask_AccompanyFollow_input))
            return false
        }
        return true
    }

    override fun onLookHouseAccompanyFollowSuccess() {
        Bus.send(LookTaskEvent(null))
        Bus.send(MeRefreshEvent())
        AppManager.instance.finishActivity(MineLookTaskDetailActivity::class.java)
        AppManager.instance.finishActivity(activity!!)
    }

}
