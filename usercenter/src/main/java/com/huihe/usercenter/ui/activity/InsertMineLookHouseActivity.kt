package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.huihe.usercenter.R
import com.huihe.usercenter.ui.fragment.InsertMineLookHouseFragment
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar

class InsertMineLookHouseActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.insert_lookHouse_title))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        setFragment(InsertMineLookHouseFragment(),intent.extras)
    }

}