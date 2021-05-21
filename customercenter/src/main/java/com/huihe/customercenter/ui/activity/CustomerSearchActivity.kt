package com.huihe.customercenter.ui.activity

import android.os.Bundle
import com.huihe.customercenter.ui.fragment.CustomerSearchFragment
import com.kotlin.base.ui.activity.BaseActivity

class CustomerSearchActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(CustomerSearchFragment())
    }
}
