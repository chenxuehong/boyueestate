package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.huihe.usercenter.R
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widgets.HeaderBar
import com.kotlin.provider.router.RouterPath

class SearchHouseListActivity : BaseTitleActivity(){

    private val mHouseFragment by lazy {
        ARouter.getInstance()
            .build(RouterPath.HomeCenter.PATH_HOUSE_FRAGMENT)
            .navigation() as BaseFragment
    }
    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.house_list))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        setFragment(mHouseFragment, intent.extras)
    }


}
