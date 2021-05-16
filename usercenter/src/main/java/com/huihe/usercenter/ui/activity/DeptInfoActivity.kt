package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.DeptInfoFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.UserConstant

class DeptInfoActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = Bundle()
        args.putString(UserConstant.KEY_USER_ID,
            intent.extras.getString(UserConstant.KEY_USER_ID))
        setFragment(DeptInfoFragment(),args)
    }
}
