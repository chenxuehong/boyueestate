package com.huihe.usercenter.ui.activity

import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.huihe.usercenter.R
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widgets.HeaderBar
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.UserCenter.PATH_SEARCHHOUSELIST_ACTIVITY)
class SearchHouseListActivity : BaseTitleActivity(){

    @JvmField
    @Autowired(name = BaseConstant.KEY_STATUS)
    var status:Int = BaseConstant.KEY_STATUS_DEFAULT

    private val mHouseFragment by lazy {
        ARouter.getInstance()
            .build(RouterPath.HomeCenter.PATH_HOUSE_FRAGMENT)
            .withInt(BaseConstant.KEY_STATUS,status)
            .navigation() as BaseFragment
    }
    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.house_list))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        setFragment(mHouseFragment, intent.extras)
    }


}
