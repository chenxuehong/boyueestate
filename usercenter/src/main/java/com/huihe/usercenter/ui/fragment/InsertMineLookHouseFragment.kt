package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.InsertMineLookHousePresenter
import com.huihe.usercenter.presenter.view.InsertMineLookHouseView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.fragment.BaseMvpFragment

class InsertMineLookHouseFragment :
    BaseMvpFragment<InsertMineLookHousePresenter>(), InsertMineLookHouseView {

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
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_insert_lookhouse, container!!)
    }

}