package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.MineLookHouseReq
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.InsertMineLookHousePresenter
import com.huihe.usercenter.presenter.view.InsertMineLookHouseView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.getString
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.isRepeat
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.HouseSelectEvent
import com.kotlin.provider.event.RefreshLookTaskDetailEvent
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_insert_lookhouse.*
import org.jetbrains.anko.support.v4.toast

class InsertMineLookHouseFragment :
    BaseMvpFragment<InsertMineLookHousePresenter>(), InsertMineLookHouseView {

    var houseCodeList = mutableListOf<String>()
    lateinit var req: MineLookHouseReq
    var takeLookId:String?=null
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
        houseCodeList?.clear()
        takeLookId = arguments?.getString(UserConstant.KEY_ID)
        req = MineLookHouseReq(takeLookId = takeLookId)
        initView()
    }

    private fun initView() {
        Bus.observe<HouseSelectEvent>()
            .subscribe {
                onShowHouseCode(it.houseCode)
            }.registerInBus(this)
        Bus.observe<SearchHouseEvent>()
            .subscribe {
                if ("AddressBookFragment" == it.type) {
                    onShowAccompanyLooker(it)
                }
            }.registerInBus(this)
        nsvLookHouse.onClick {
            // 选择房源
            ARouter.getInstance()
                .build(RouterPath.HomeCenter.PATH_HOUSE_SELECT_ACTIVITY)
                .navigation(context)
        }
        nsvAccompanyLooker.onClick {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        tvAddHouse.onClick {
            if (checkInput()) {
                req?.follow = etEditFollow.text.toString()
                mPresenter?.insertMineLookHouse(req)
            }
        }
    }

    private fun onShowHouseCode(houseCode: String?) {
        if (houseCode.isNullOrEmpty()) {
            toast("房源编码为空")
            return
        }
        houseCodeList.isRepeat(houseCode) {
            toast("请不要重复添加房源")
            return@isRepeat
        }
        houseCodeList.add(houseCode)
        nsvLookHouse.setContent(houseCodeList.getString(","))
        req?.houseCode = nsvLookHouse.getContent()
    }

    private fun onShowAccompanyLooker(it: SearchHouseEvent?) {
        nsvAccompanyLooker.setContent(it?.name ?: "")
        req?.accompanyUserId = it?.id
        req?.accompanyUser = it?.name
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
        if (etEditFollow.text.toString().isNullOrEmpty()) {
            toast(resources.getString(R.string.follow_input))
            return false
        }
        return true
    }

    override fun onInsertSuccess() {
        toast(resources.getString(R.string.commitSuccess))
        activity?.finish()
        Bus.send(RefreshLookTaskDetailEvent())
    }

    override fun onDestroy() {
        houseCodeList?.clear()
        Bus.unregister(this)
        super.onDestroy()
    }

}