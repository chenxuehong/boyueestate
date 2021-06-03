package com.huihe.module_home.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.FollowRep
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.AddFollowPresenter
import com.huihe.module_home.presenter.view.AddFollowView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_add_follow.*
import org.jetbrains.anko.support.v4.toast

class AddFollowFragment : BaseMvpFragment<AddFollowPresenter>(), AddFollowView {

    var houseId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_add_follow, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseId = arguments?.getString(HomeConstant.KEY_HOUSE_ID)
        initView()
    }

    private fun initView() {
        tvAddFollowContent.onClick {
            if (checkInput()) {
                mPresenter?.addFollowContent(
                    houseId,
                    etHouseFollowContent.text.toString().trim()
                )
            }
        }
    }

    private fun checkInput(): Boolean {
        if (TextUtils.isEmpty(etHouseFollowContent.text.toString().trim())) {
            toast(resources.getString(R.string.follow_content_input))
            return false
        }
        return true
    }

    override fun onAddFollowResult(t: FollowRep.FollowBean?) {
        val intent = Intent()
        Bus.send(t!!)
        activity?.setResult(Activity.RESULT_OK, intent)
        intent.putExtra(HomeConstant.KEY_FOLLOW_BEAN, Gson().toJson(t))
        activity?.finish()
    }
}
