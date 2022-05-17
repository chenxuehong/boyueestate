package com.huihe.module_home.ui.activity

import android.os.Bundle
import android.util.LruCache
import com.huihe.module_home.ui.fragment.AddFollowFragment
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.constant.HomeConstant
import okhttp3.OkHttpClient
import okhttp3.Request

class AddFollowActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var extras = intent.extras
        var id = extras.getString(HomeConstant.KEY_HOUSE_ID)
        var fragment = AddFollowFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_HOUSE_ID,id)
        setFragment(fragment,args)
    }
}
