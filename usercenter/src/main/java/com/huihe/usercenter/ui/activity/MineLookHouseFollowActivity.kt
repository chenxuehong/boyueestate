package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.huihe.usercenter.R
import com.huihe.usercenter.ui.fragment.InsertMineLookHouseFragment
import com.huihe.usercenter.ui.fragment.MineLookHouseFollowFragment
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar

class MineLookHouseFollowActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.looktask_follow))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        setFragment(MineLookHouseFollowFragment(),intent.extras)
    }
}