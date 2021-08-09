package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MineLookTaskDetailPresenter
import com.huihe.usercenter.presenter.view.MineLookTaskDetailView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.UserConstant

class MineLookTaskDetailFragment :BaseMvpFragment<MineLookTaskDetailPresenter>(),MineLookTaskDetailView{

    var id:String? = null
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initInflater(context!!, R.layout.fragment_mine_looktask_detail, container!!)
        super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        id = arguments?.getString(UserConstant.KEY_ID)
    }

    private fun initData() {
        mPresenter.getLookTaskDetail(id)
    }

}