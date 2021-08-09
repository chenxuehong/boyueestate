package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.huihe.usercenter.ui.fragment.BehaviourFragment
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar
import com.kotlin.provider.constant.UserConstant

/**
 * @desc 门店数据/部门数据/员工数据
 */
class BehaviourActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        var title = intent.extras?.getString(UserConstant.KEY_TITLE)?:""
        baseTitleHeaderBar.setTitle(title)
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        setFragment(BehaviourFragment(), intent.extras)
    }


}