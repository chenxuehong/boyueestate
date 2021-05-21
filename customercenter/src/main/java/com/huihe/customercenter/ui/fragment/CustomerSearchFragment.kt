package com.huihe.customercenter.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.huihe.customercenter.R
import com.huihe.customercenter.data.protocol.CustomerSearchReq
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.provider.constant.CustomerConstant
import kotlinx.android.synthetic.main.fragment_customer_search.*

class CustomerSearchFragment : BaseFragment(){

    var req: CustomerSearchReq = CustomerSearchReq()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_customer_search,container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        req = CustomerSearchReq()
        customerSearchTitleBar.onClick {
            if (checkInput()){
                req.customerCode = nivcustomerCode.getContent()
                req.mobile = nivmobile.getContent()
                req.customerName = nivcustomerName.getContent()
                req.demandBeat = nivdemandBeat.getContent()
                req.remarks = nivremarks.getContent()
                var intent = Intent()
                intent.putExtra(CustomerConstant.KEY_CUSTOMER_SEARCH, Gson().toJson(req))
                activity?.setResult(0, intent)
                activity?.finish()
            }
        }
    }

    private fun checkInput(): Boolean {
        var childCount = llcustomerSearch.childCount
        for (i in 0 until childCount) {
            var childView = llcustomerSearch.getChildAt(i)
            if (childView is NecessaryTitleSelectView) {
                if (!TextUtils.isEmpty(childView.getContent())) {
                    return true
                }
            } else if (childView is NecessaryTitleInputView) {
                if (!TextUtils.isEmpty(childView.getContent())) {
                    return true
                }
            }
        }
        return false
    }
}
