package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.AddHouseFragment
import com.kotlin.base.ui.activity.BaseActivity

class AddHouseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fragment = AddHouseFragment()
        setFragment(fragment)
    }
}
