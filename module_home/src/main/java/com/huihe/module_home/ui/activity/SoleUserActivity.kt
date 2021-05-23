package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.SoleUserFragment
import com.kotlin.base.ui.activity.BaseActivity

class SoleUserActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        if (extras != null) {
            setFragment(SoleUserFragment(), extras)
        } else {
            setFragment(SoleUserFragment())
        }
    }
}
