package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huihe.usercenter.ui.fragment.AddressBookFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.router.RouterPath

@Route(path = RouterPath.UserCenter.PATH_ADDRESSBOOK)
class AddressBookActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          var extras = intent?.extras
        if (extras!=null){
            setFragment(AddressBookFragment(),extras)
        }else{
            setFragment(AddressBookFragment())
        }
    }
}
