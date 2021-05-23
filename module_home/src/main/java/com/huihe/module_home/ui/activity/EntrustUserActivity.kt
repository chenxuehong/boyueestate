package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.EntrustUserFragment
import com.kotlin.base.ui.activity.BaseActivity

class EntrustUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        if (extras != null) {
            setFragment(EntrustUserFragment(),extras)
        } else {
            setFragment(EntrustUserFragment())
        }
    }
}