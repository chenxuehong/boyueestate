package com.huihe.customercenter.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.customercenter.R
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.AddCustomerFollowPresenter
import com.huihe.customercenter.presenter.view.AddCustomerFollowView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.CustomerConstant
import kotlinx.android.synthetic.main.fragment_customer_add_follow.*
import org.jetbrains.anko.support.v4.toast

class AddCustomerFollowFragment : BaseMvpFragment<AddCustomerFollowPresenter>(),
    AddCustomerFollowView {

    var customerCode:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_customer_add_follow, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerCode = arguments?.getString(CustomerConstant.KEY_CUSTOMER_CODE)
        initView()
    }

    private fun initView() {
        tvAddFollowContent.onClick {
            if (checkInput()) {
                mPresenter?.addFollowContent(
                    customerCode,
                    etHouseFollowContent.text.toString().trim()
                )
            }
        }
    }

    private fun checkInput(): Boolean {
        if (TextUtils.isEmpty(etHouseFollowContent.text.toString().trim())) {
            toast(resources.getString(R.string.follow_content_input))
            return false
        }
        return true
    }

    override fun onAddFollowSuccess() {
        val intent = Intent()
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }
}
