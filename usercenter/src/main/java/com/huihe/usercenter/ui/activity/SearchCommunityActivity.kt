package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.adapter.SearchCommunityFragment
import com.kotlin.base.ui.activity.BaseActivity

class SearchCommunityActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        if (extras != null) {
            setFragment(SearchCommunityFragment(),extras)
        } else {
            setFragment(SearchCommunityFragment())
        }
    }
}
