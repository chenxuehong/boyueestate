package com.huihe.customercenter.ui.activity

import android.os.Bundle
import com.huihe.customercenter.ui.fragment.CustomerFollowFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.CustomerConstant

class CustomerFollowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var customerCode = extras.getString(CustomerConstant.KEY_CUSTOMER_CODE)
        var fragment = CustomerFollowFragment()
        val args = Bundle()
        args.putString(CustomerConstant.KEY_CUSTOMER_CODE, customerCode)
        setFragment(fragment, args)
    }
}
