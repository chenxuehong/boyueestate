package com.kotlin.base.ui.activity

import android.os.Bundle
import android.widget.FrameLayout

import com.kotlin.base.R
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widgets.HeaderBar
import kotlinx.android.synthetic.main.activity_base_title.*

abstract class BaseTitleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_title)
        initTitle(baseTitleHeaderBar)
        initView(baseTitleContentView)
    }

    abstract fun initView(baseTitleContentView: FrameLayout)

    abstract fun initTitle(baseTitleHeaderBar: HeaderBar)

    override fun setFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().replace(
            baseTitleContentView.id,
            fragment
        ).commitAllowingStateLoss()
    }

    override fun setFragment(fragment:BaseFragment,args: Bundle){
        fragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            baseTitleContentView.id,
            fragment
        ).commitAllowingStateLoss()
    }
}
