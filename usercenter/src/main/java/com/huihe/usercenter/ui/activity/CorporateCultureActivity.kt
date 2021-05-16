package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.CorporateCultureFragment
import com.kotlin.base.ui.activity.BaseActivity

class CorporateCultureActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(CorporateCultureFragment())
    }
}
