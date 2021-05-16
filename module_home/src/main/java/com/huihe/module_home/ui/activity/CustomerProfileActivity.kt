package com.huihe.module_home.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.module_home.ui.fragment.CustomerProfileFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.HomeCenter.PATH_CUSTOMER_PROFILE)
class CustomerProfileActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var id = extras.getString(HomeConstant.KEY_HOUSE_ID)
        var fragment = CustomerProfileFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_HOUSE_ID,id)
        setFragment(fragment,args)
    }
}
