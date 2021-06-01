package com.huihe.customercenter.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.qqtheme.framework.picker.SinglePicker
import com.eightbitlab.rxbus.Bus
import com.huihe.customercenter.R
import com.huihe.customercenter.data.protocol.AddCustomerReq
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.AddCustomerPresenter
import com.huihe.customercenter.presenter.view.AddCustomerView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.provider.event.AddCustomerEvent
import kotlinx.android.synthetic.main.layout_fragment_add_customer.*
import org.jetbrains.anko.support.v4.toast

class AddCustomerFragment : BaseMvpFragment<AddCustomerPresenter>(),AddCustomerView {

    lateinit var req: AddCustomerReq
    var mUserTypePicker:SinglePicker<String>?=null
    var mCustomerTypePicker:SinglePicker<String>?=null

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_fragment_add_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        req = AddCustomerReq()
        initView()
    }

    private fun initView() {
        nsvCustomerType.onClick {
            selectCustomerType()
        }
        nsvUserType.onClick {
            selectUserType()
        }
        tvAddCustomer.onClick {
            if (checkInput()){
                req.customerName = nsvCustomerName.getContent()
                req.mobile = nsvMobile.getContent()
                req.demandBeat = nsvDemandBeat.getContent()
                req.demandBeat = nsvDemandBudget.getContent()
                req.demandFloor = nsvDemandFloor.getContent()
                req.remarks = etRewarks.text.toString().trim()

                mPresenter.addCustomer(req)
            }
        }
    }

    private fun selectCustomerType() {
        // 出售 0, 出租 1,租售 2
        mCustomerTypePicker = SinglePicker(
            activity, mutableListOf<String>(
                "买卖",
                "租赁"
            )
        )
        mCustomerTypePicker?.setCanceledOnTouchOutside(true)
        mCustomerTypePicker?.selectedIndex = 1
        mCustomerTypePicker?.setCycleDisable(true)
        mCustomerTypePicker?.setOnItemPickListener { index, item ->
            var customerType = 0
            when (item) {
                "买卖" -> {
                    customerType = 1
                    nsvCustomerType.setContent("买卖")
                }
                "租赁" -> {
                    customerType = 2
                    nsvCustomerType.setContent("租赁")
                }
            }
            req?.customerType = customerType

        }
        mCustomerTypePicker?.show()
    }

    private fun selectUserType() {
        // 出售 0, 出租 1,租售 2
        mUserTypePicker = SinglePicker(
            activity, mutableListOf<String>(
                "公客",
                "私客"
            )
        )
        mUserTypePicker?.setCanceledOnTouchOutside(true)
        mUserTypePicker?.selectedIndex = 1
        mUserTypePicker?.setCycleDisable(true)
        mUserTypePicker?.setOnItemPickListener { index, item ->
            var userType = 0
            when (item) {
                "公客" -> {
                    userType = 1
                    nsvUserType.setContent("公客")
                }
                "私客" -> {
                    userType = 2
                    nsvUserType.setContent("私客")
                }
            }
            req?.userType = userType
        }
        mUserTypePicker?.show()
    }

    private fun checkInput(): Boolean {
        var childCount = llAddCustomer.childCount
        for (i in 0 until childCount) {
            var childView = llAddCustomer.getChildAt(i)
            if (childView is NecessaryTitleSelectView) {
                if (childView.isNecessary && TextUtils.isEmpty(childView.getContent())) {
                    var tipContentText = childView.getTipContentText()
                    toast(tipContentText)
                    return false
                }
            } else if (childView is NecessaryTitleInputView) {
                if (childView.isNecessary && TextUtils.isEmpty(childView.getContent())) {
                    var tipContentText = childView.getHintContent()
                    toast(tipContentText)
                    return false
                }
            }
        }
        return true
    }

    override fun onDestroy() {
        try {
            mCustomerTypePicker?.dismiss()
            mUserTypePicker?.dismiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    override fun onAddSuccess() {
        Bus.send(AddCustomerEvent())
        activity?.finish()
    }
}
