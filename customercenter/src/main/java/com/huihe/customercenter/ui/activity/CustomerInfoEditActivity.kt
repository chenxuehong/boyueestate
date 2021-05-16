package com.huihe.customercenter.ui.activity

import android.os.Bundle
import com.huihe.customercenter.ui.fragment.CustomerInfoEditFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.CustomerConstant

class CustomerInfoEditActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var data = intent.extras.getString(CustomerConstant.KEY_CUSTOMER_INFO)
        var arg = Bundle()
        arg.putString(CustomerConstant.KEY_CUSTOMER_INFO, data)
        setFragment(CustomerInfoEditFragment(), arg)
    }
}
