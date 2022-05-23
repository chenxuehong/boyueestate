package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.LookTaskAuditReq
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.LookTaskAuditPresenter
import com.huihe.usercenter.presenter.view.LookTaskAuditView
import com.huihe.usercenter.ui.activity.MineLookTaskDetailActivity
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.widgets.picker.WheelPicker.picker.SinglePicker
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.LookTaskEvent
import com.kotlin.provider.event.MeRefreshEvent
import kotlinx.android.synthetic.main.fragment_insert_lookhouse.*
import kotlinx.android.synthetic.main.fragment_look_task_audit.*
import org.jetbrains.anko.support.v4.toast

class LookTaskAuditFragment : BaseMvpFragment<LookTaskAuditPresenter>(), LookTaskAuditView{

    var mQualifiedPicker : SinglePicker<String>?=null
    var req: LookTaskAuditReq?=null
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
        return initInflater(context!!, R.layout.fragment_look_task_audit, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takeLookId = arguments?.getString(UserConstant.KEY_ID)
        customerCode = arguments?.getString(UserConstant.KEY_CUSTOMER_CODE)
        req = LookTaskAuditReq(customerCode=customerCode,takeLookTaskId= takeLookId)
        initView()
    }

    private fun initView() {
        val colorText = "<font color = \"#FF0000\">*</font> ${resources.getString(R.string.summary_follow)}"
        tvLookTaskAuditTitle.text =  Html.fromHtml(colorText)
        nsvIsQualified.onClick {
            selectQualifiedDialog()
        }
        tvAddLookTaskAudit.onClick {
            if (checkInput()){
                req?.followUpContent = etLookTaskAuditContent.text.toString()
                mPresenter.putLookTaskAudit(req)
            }
        }
    }

    private fun checkInput(): Boolean {
        if (nsvIsQualified.isNecessary && TextUtils.isEmpty(nsvIsQualified.getContent())) {
            var tipContentText = nsvIsQualified.getTipContentText()
            toast(tipContentText)
            return false
        }
        if (etLookTaskAuditContent.text.toString().isNullOrEmpty()){
            toast(resources.getString(R.string.summary_follow_input))
            return false
        }
        return true
    }

    private fun selectQualifiedDialog() {
        // 出售 0, 出租 1,租售 2
        mQualifiedPicker =
            SinglePicker(
                activity, mutableListOf<String>(
                    "是",
                    "否"
                )
            )
        mQualifiedPicker?.setCanceledOnTouchOutside(true)
        mQualifiedPicker?.selectedIndex = 1
        mQualifiedPicker?.setCycleDisable(true)
        mQualifiedPicker?.setOnItemPickListener { index, item ->
            var qualified = 0
            when (item) {
                "是" -> {
                    qualified = 1
                    nsvIsQualified.setContent("是")
                }
                "否" -> {
                    qualified = 0
                    nsvIsQualified.setContent("否")
                }
            }
            req?.isQualified = qualified

        }
        mQualifiedPicker?.show()
    }

    override fun onLookTaskAuditSuccess() {
        Bus.send(LookTaskEvent(null))
        Bus.send(MeRefreshEvent())
        AppManager.instance.finishActivity(MineLookTaskDetailActivity::class.java)
        AppManager.instance.finishActivity(activity!!)
    }

    override fun onDestroy() {
        try {
            mQualifiedPicker?.dismiss()
        } catch (e: Exception) {
        } finally {
            mQualifiedPicker = null
        }
        super.onDestroy()
    }
}
