package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.usercenter.ui.fragment.CommunityManagerFragment
import com.kotlin.base.common.BaseConstant

import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.UserCenter.PATH_COMMUNITY_MANAGER)
class CommunityManagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent?.extras
        if (extras!=null){
            setFragment(CommunityManagerFragment(),extras)
        }else{
            setFragment(CommunityManagerFragment())
        }
    }
}
