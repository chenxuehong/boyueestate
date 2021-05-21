package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.huihe.module_home.ui.fragment.SearchHouseFragment
import com.kotlin.base.ui.activity.BaseActivity

class SearchHouseActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(SearchHouseFragment())
    }
}
