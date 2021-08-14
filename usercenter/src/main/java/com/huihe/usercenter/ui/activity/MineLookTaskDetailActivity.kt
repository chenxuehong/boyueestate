package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.huihe.usercenter.R
import com.huihe.usercenter.ui.fragment.MineLookTaskDetailFragment
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar

class MineLookTaskDetailActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.task_detail))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        setFragment(MineLookTaskDetailFragment(),intent.extras)
    }

}