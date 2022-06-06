package com.huihe.customercenter.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.CustomerDetailRep
import com.huihe.boyueentities.protocol.customer.SetCustomersReq
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.CustomerInfoEditPresenter
import com.huihe.customercenter.presenter.view.CustomerInfoEditView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.base.widgets.picker.WheelPicker.picker.SinglePicker
import com.kotlin.provider.constant.CustomerConstant
import kotlinx.android.synthetic.main.fragment_customer_info_edit.*
import org.jetbrains.anko.support.v4.toast

class CustomerInfoEditFragment : BaseMvpFragment<CustomerInfoEditPresenter>(),
    CustomerInfoEditView {

    var mCustomerDetailRep: CustomerDetailRep? = null
    var mSetCustomersReq: SetCustomersReq? = null
    var mUserTypePicker: SinglePicker<String>? = null
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
        return initInflater(context!!, R.layout.fragment_customer_info_edit, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        var json = arguments?.getString(CustomerConstant.KEY_CUSTOMER_INFO) ?: ""
        mCustomerDetailRep = Gson().fromJson<CustomerDetailRep>(json, CustomerDetailRep::class.java)
        setCustomerInfoTitleBar.setTitle(mCustomerDetailRep?.customerCode ?: "")
        mSetCustomersReq = SetCustomersReq(
            mCustomerDetailRep?.id,
            mCustomerDetailRep?.deptId,
            mCustomerDetailRep?.status,
            mCustomerDetailRep?.customerName,
            mCustomerDetailRep?.userType,
            mCustomerDetailRep?.source,
            mCustomerDetailRep?.demandFloor,
            mCustomerDetailRep?.demandHouseType,
            mCustomerDetailRep?.demandArea,
            mCustomerDetailRep?.demandBeat,
            mCustomerDetailRep?.isSubstitution,
            mCustomerDetailRep?.demandBudget,
            mCustomerDetailRep?.viewHouseDate,
            mCustomerDetailRep?.isFirstHouse,
            mCustomerDetailRep?.successDate,
            mCustomerDetailRep?.isStyle,
            mCustomerDetailRep?.remarks,
            mCustomerDetailRep?.demandPrice,
            mCustomerDetailRep?.customerType,
            mCustomerDetailRep?.customerLeaver,
            mCustomerDetailRep?.customerCode
        )
        binData()
        nsvUserType.onClick {
            selectUserType()
        }
        tvEditCustomerInfoUpdate.onClick {
            if (checkInput()) {
                mSetCustomersReq?.customerName = nsvCustomerName.getContent()
                mSetCustomersReq?.source = nsvSource.getContent()
                mSetCustomersReq?.demandFloor = nsvDemandFloor.getContent()
                mSetCustomersReq?.demandHouseType = nsvDemandHouseType.getContent()
                mSetCustomersReq?.demandArea = nsvDemandArea.getContent()
                mSetCustomersReq?.demandBeat = nsvDemandBeat.getContent()
                mSetCustomersReq?.isSubstitution = nsvIsSubstitution.getContent()
                mSetCustomersReq?.demandBudget = nsvDemandBudget.getContent()
                mSetCustomersReq?.viewHouseDate = nsvViewHouseDate.getContent()
                mSetCustomersReq?.isFirstHouse = nsvIsFirstHouse.getContent()
                mSetCustomersReq?.successDate = nsvSuccessDate.getContent()
                mSetCustomersReq?.isStyle = nsvIsStyle.getContent()
                mSetCustomersReq?.remarks = etEditCustomerInfoRewarks.text.toString().trim()
                mPresenter.setCustomerInfo(mSetCustomersReq!!)
            }
        }
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
            mSetCustomersReq?.userType = userType

        }
        mUserTypePicker?.show()
    }

    private fun binData() {
        nsvCustomerName.setContent(mSetCustomersReq?.customerName ?: "")
        // 1-私客；2-公客
        var userType = mSetCustomersReq?.userType ?: 1
        nsvUserType.setContent(
            if (userType == 1) {
                "私客"
            } else {
                "公客"
            }
        )
        nsvSource.setContent(mSetCustomersReq?.source ?: "")
        nsvDemandFloor.setContent(mSetCustomersReq?.demandFloor ?: "")
        nsvDemandHouseType.setContent(mSetCustomersReq?.demandHouseType ?: "")
        nsvDemandArea.setContent(mSetCustomersReq?.demandArea ?: "")
        nsvDemandBeat.setContent(mSetCustomersReq?.demandBeat ?: "")
        nsvIsSubstitution.setContent(mSetCustomersReq?.isSubstitution ?: "")
        nsvDemandBudget.setContent(mSetCustomersReq?.demandBudget ?: "")
        nsvViewHouseDate.setContent(mSetCustomersReq?.viewHouseDate ?: "")
        nsvIsFirstHouse.setContent(mSetCustomersReq?.isFirstHouse ?: "")
        nsvSuccessDate.setContent(mSetCustomersReq?.successDate ?: "")
        nsvIsStyle.setContent(mSetCustomersReq?.isStyle ?: "")
        etEditCustomerInfoRewarks.setText(mSetCustomersReq?.remarks ?: "")
    }

    private fun checkInput(): Boolean {
        var childCount = setCustomerInfoLL.childCount
        for (i in 0 until childCount) {
            var childView = setCustomerInfoLL.getChildAt(i)
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
            mUserTypePicker?.dismiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    override fun onSetCustomerInfoSuccess() {
        toast("修改成功")
        activity?.finish()
    }
}
