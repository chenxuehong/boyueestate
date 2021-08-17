package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.huihe.usercenter.R
import com.huihe.usercenter.ui.fragment.LookTaskAuditFragment

import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar

class LookTaskAuditActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.LookTaskAudit))
    }

    override fun initView(baseTitleContentView: FrameLayout) {

        setFragment(LookTaskAuditFragment(),intent.extras)
    }

}
