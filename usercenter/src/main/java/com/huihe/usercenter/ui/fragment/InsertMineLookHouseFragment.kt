package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.InsertMineLookHousePresenter
import com.huihe.usercenter.presenter.view.InsertMineLookHouseView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_insert_lookhouse.*
import org.jetbrains.anko.support.v4.toast

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        nsvLookHouse.onClick {

        }
        nsvAccompanyLooker.onClick {

        }
        tvAddHouse.onClick {
            if (checkInput()){

            }
        }
    }

    private fun checkInput(): Boolean {
        if (nsvLookHouse.isNecessary && TextUtils.isEmpty(nsvLookHouse.getContent())) {
            var tipContentText = nsvLookHouse.getTipContentText()
            toast(tipContentText)
            return false
        }
        if (nsvAccompanyLooker.isNecessary && TextUtils.isEmpty(nsvAccompanyLooker.getContent())) {
            var tipContentText = nsvAccompanyLooker.getTipContentText()
            toast(tipContentText)
            return false
        }
        if (etEditFollow.text.toString().isNullOrEmpty()){
            toast(resources.getString(R.id.insertHouse_follow_input))
            return false
        }
        return true
    }
}