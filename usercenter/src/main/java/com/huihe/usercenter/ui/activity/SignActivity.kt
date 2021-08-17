package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.huihe.usercenter.R
import com.huihe.usercenter.ui.fragment.SignFragment
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar

class SignActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.sign))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        setFragment(SignFragment(),intent.extras)
    }
}
