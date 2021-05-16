package com.huihe.customercenter.ui.activity

import android.os.Bundle
import com.huihe.customercenter.ui.fragment.AddCustomerFragment
import com.kotlin.base.ui.activity.BaseActivity

class AddCustomerActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(AddCustomerFragment())
    }
}
