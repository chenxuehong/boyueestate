package com.huihe.usercenter.ui.activity

import android.os.Bundle
import com.huihe.usercenter.ui.fragment.AddressBookFragment
import com.kotlin.base.ui.activity.BaseActivity

class AddressBookActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(AddressBookFragment())
    }
}
