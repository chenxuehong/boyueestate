package com.huihe.customercenter.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.customercenter.R
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.SetCustomerInfoPresenter
import com.huihe.customercenter.presenter.view.SetCustomerInfoView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.CustomerConstant
import kotlinx.android.synthetic.main.layout_fragment_set_owner_info.*
import layout.RvPhoneAdapter
import org.jetbrains.anko.support.v4.toast

class SetCustomerInfoFragment : BaseMvpFragment<SetCustomerInfoPresenter>(), SetCustomerInfoView {

    var mRvPhoneAdapter: RvPhoneAdapter? = null
    var customerName: String? = null
    var id: String? = null
    var customerMobile: String? = null
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
        return inflater.inflate(R.layout.layout_fragment_set_owner_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString(CustomerConstant.KEY_CUSTOMER_ID)
        customerName = arguments?.getString(CustomerConstant.KEY_CUSTOMER_NAME)
        customerMobile = arguments?.getString(CustomerConstant.KEY_CUSTOMER_MOBILE)
        initView()
        initRvPhoneAdapter()
    }

    private fun initView() {
        set_owner_info_et_OwnerNick.setText(customerName ?: "")
        set_owner_info_titleBar.getRightView()?.onClick {
            var dataList = mRvPhoneAdapter?.dataList
            var telStr: String = getTelList(dataList)
            mPresenter.setCustomerStatus(
                id,
                2,
                telStr
            )
        }
        set_owner_info_tv_insert.onClick {
            mRvPhoneAdapter?.insertItem()
        }
        set_owner_info_tv_delete.onClick {
            mRvPhoneAdapter?.deleteItem()
        }
    }

    private fun getTelList(dataList: MutableList<String>?): String {
        var sb = StringBuffer()
        dataList?.forEach { item ->
            if (!TextUtils.isEmpty(item)) {
                sb.append("*").append(item)
            }
        }
        if (sb.length > 1) {
            return sb.substring(1).toString().trim()
        }
        return ""
    }

    private fun initRvPhoneAdapter() {
        mRvPhoneAdapter = RvPhoneAdapter(context)
        set_owner_info_rv_OwnerPhone.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRvPhoneAdapter
        }
        var list = getMobileList()
        if (list.size == 0) {
            list.add("")
        }
        mRvPhoneAdapter?.setData(list)
    }

    private fun getMobileList(): MutableList<String> {
        var customerMobile = customerMobile?.trim()
        if (!TextUtils.isEmpty(customerMobile)) {
            return customerMobile?.split("*")?.toMutableList() ?: mutableListOf()
        }
        return mutableListOf()
    }

    override fun onSetCustomerStatusSuccess() {
        toast("修改成功")
        activity?.finish()
    }

}
