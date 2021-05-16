package com.huihe.customercenter.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.huihe.customercenter.R
import com.huihe.customercenter.ui.adapter.CustomerLogFragmentAdapter
import com.huihe.customercenter.ui.fragment.CustomerLogFragment
import com.huihe.customercenter.ui.fragment.CustomerTelLogFragment
import com.kotlin.base.ui.activity.BaseTitleActivity
import com.kotlin.base.widgets.HeaderBar
import com.kotlin.provider.constant.CustomerConstant
import kotlinx.android.synthetic.main.activity_customer_log.view.*

class CustomerLogActivity : BaseTitleActivity() {

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {
        baseTitleHeaderBar.setTitle(resources.getString(R.string.log))
    }

    override fun initView(baseTitleContentView: FrameLayout) {
        val titles = mutableListOf<String>(
            resources.getString(R.string.log),
            resources.getString(R.string.telLog)
        )
        var code = intent.extras.getString(CustomerConstant.KEY_CUSTOMER_CODE)
        val view = View.inflate(this, R.layout.activity_customer_log, null)
        val fragments = getFragments(code)
        view.customerLogViewpager.adapter =
            CustomerLogFragmentAdapter(supportFragmentManager, titles, fragments)
        view.customerLogTabLayout.setupWithViewPager(view.customerLogViewpager)
        baseTitleContentView.addView(view)
    }

    private fun getFragments(code: String): MutableList<out Fragment> {
        val arg1 = Bundle()
        arg1.putString(CustomerConstant.KEY_CUSTOMER_CODE, code)
        val arg2 = Bundle()
        arg2.putString(CustomerConstant.KEY_CUSTOMER_CODE, code)

        var customerLogFragment = CustomerLogFragment()
        customerLogFragment.arguments = arg1
        var customerTelLogFragment = CustomerTelLogFragment()
        customerTelLogFragment.arguments = arg2
        return mutableListOf(customerLogFragment, customerTelLogFragment)
    }

}
