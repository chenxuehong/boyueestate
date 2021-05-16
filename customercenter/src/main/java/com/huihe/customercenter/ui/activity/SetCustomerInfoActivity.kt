package com.huihe.customercenter.ui.activity

import android.os.Bundle
import com.huihe.customercenter.ui.fragment.SetCustomerInfoFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.CustomerConstant

class SetCustomerInfoActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var customerId = extras.getString(CustomerConstant.KEY_CUSTOMER_ID)
        var customerName = extras.getString(CustomerConstant.KEY_CUSTOMER_NAME)
        var customerMobile = extras.getString(CustomerConstant.KEY_CUSTOMER_MOBILE)
        var fragment = SetCustomerInfoFragment()
        val args = Bundle()
        args.putString(CustomerConstant.KEY_CUSTOMER_ID,customerId)
        args.putString(CustomerConstant.KEY_CUSTOMER_NAME,customerName)
        args.putString(CustomerConstant.KEY_CUSTOMER_MOBILE,customerMobile)
        setFragment(fragment,args)
    }
}
