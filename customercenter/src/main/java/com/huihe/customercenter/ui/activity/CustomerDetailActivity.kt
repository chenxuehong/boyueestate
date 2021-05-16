package com.huihe.customercenter.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.customercenter.ui.fragment.CustomerDetailFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.CustomerConstant
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.CustomerCenter.PATH_CUSTOMER_DETAIL)
class CustomerDetailActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var id = extras.getString(CustomerConstant.KEY_CUSTOMER_ID)
        var fragment = CustomerDetailFragment()
        val args = Bundle()
        args.putString(CustomerConstant.KEY_CUSTOMER_ID,id)
        setFragment(fragment,args)
    }
}
