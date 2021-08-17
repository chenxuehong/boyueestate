package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.huihe.usercenter.R
import com.huihe.usercenter.ui.fragment.AccompanyFollowFragment
import com.kotlin.base.common.BaseApplication

import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar

/**
 * @desc 陪看跟进
 */
class AccompanyFollowActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.looktask_AccompanyFollow))
    }

    override fun initView(baseTitleContentView: FrameLayout) {

        setFragment(AccompanyFollowFragment(),intent.extras)
    }
}
