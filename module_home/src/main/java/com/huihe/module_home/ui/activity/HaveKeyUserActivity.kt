package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.HaveKeyUserFragment
import com.kotlin.base.ui.activity.BaseActivity

class HaveKeyUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        if (extras != null) {
            setFragment(HaveKeyUserFragment(), extras)
        } else {
            setFragment(HaveKeyUserFragment())
        }
    }
}