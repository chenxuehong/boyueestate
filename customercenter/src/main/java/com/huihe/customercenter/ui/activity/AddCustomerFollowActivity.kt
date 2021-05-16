package com.huihe.customercenter.ui.activity

import android.os.Bundle
import com.huihe.customercenter.ui.fragment.AddCustomerFollowFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.CustomerConstant

class AddCustomerFollowActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var code = extras.getString(CustomerConstant.KEY_CUSTOMER_CODE)
        var fragment = AddCustomerFollowFragment()
        val args = Bundle()
        args.putString(CustomerConstant.KEY_CUSTOMER_CODE,code)
        setFragment(fragment,args)
    }
}
