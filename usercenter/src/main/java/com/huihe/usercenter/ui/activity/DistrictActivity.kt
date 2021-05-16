package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.DistrictFragment
import com.kotlin.base.ui.activity.BaseActivity

class DistrictActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(DistrictFragment())
    }
}
