package com.huihe.module_home.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.SearchReq
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_search_house.*

class SearchHouseFragment : BaseFragment() {

    var req = SearchReq()
    var type = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_search_house, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        req = SearchReq()
        set_houseinfo_titleBar.getRightView().onClick {
            if (checkInput()) {
                req.villageName = nivVillageName.getContent()
                req.ownerTel = nivOwnerTel.getContent()
                req.houseCode = nivHouseCode.getContent()
                req.building = nivBuilding.getContent()
                req.hNum = nivHNum.getContent()
                var intent = Intent()
                intent.putExtra(HomeConstant.KEY_SEARCH_REQ, Gson().toJson(req))
                activity?.setResult(0, intent)
                activity?.finish()
            }
        }

        Bus.observe<SearchHouseEvent>()
            .subscribe {
                showItem(it)
            }.registerInBus(this)

        nivSchoolIds.onClick {
            // 选择学区
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_DISTRICT)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivCreateUsers.onClick {
            // 选择录入人
            type = "CreateUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivImageUsers.onClick {
            // 选择图片人
            type = "ImageUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivMaintainUsers.onClick {
            // 选择维护人
            type = "MaintainUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivBargainPriceUsers.onClick {
            // 选择推荐人
            type = "BargainPriceUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivBlockUsers.onClick {
            // 选择封盘人
            type = "BlockUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivHaveKeyUsers.onClick {
            // 选择钥匙人
            type = "HaveKeyUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivSoleUsers.onClick {
            // 选择独家人
            type = "SoleUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivEntrustUsers.onClick {
            // 选择委托人
            type = "EntrustUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
    }

    private fun checkInput(): Boolean {
        var childCount = llsearchHouse.childCount
        for (i in 0 until childCount) {
            var childView = llsearchHouse.getChildAt(i)
            if (childView is NecessaryTitleSelectView) {
                if (!TextUtils.isEmpty(childView.getContent())) {
                    return true
                }
            } else if (childView is NecessaryTitleInputView) {
                if (!TextUtils.isEmpty(childView.getContent())) {
                    return true
                }
            }
        }
        return false
    }

    private fun showItem(it: SearchHouseEvent?) {
        it?.apply {
            when (type) {
                "DistrictFragment" -> {
                    nivSchoolIds.setContent(it.name)
                    req.schoolIds = mutableListOf(it.id)
                }
                "AddressBookFragment" -> {
                    when {
                        "CreateUsers".equals(type) -> {
                            nivCreateUsers.setContent(it.name)
                            req.createUsers = mutableListOf(it.id)
                        }
                        "ImageUsers".equals(type) -> {
                            nivImageUsers.setContent(it.name)
                            req.imageUsers = mutableListOf(it.id)
                        }
                        "MaintainUsers".equals(type) -> {
                            nivMaintainUsers.setContent(it.name)
                            req.maintainUsers = mutableListOf(it.id)
                        }
                        "BargainPriceUsers".equals(type) -> {
                            nivBargainPriceUsers.setContent(it.name)
                            req.bargainPriceUsers = mutableListOf(it.id)
                        }
                        "BlockUsers".equals(type) -> {
                            nivBlockUsers.setContent(it.name)
                            req.blockUsers = mutableListOf(it.id)
                        }
                        "HaveKeyUsers".equals(type) -> {
                            nivHaveKeyUsers.setContent(it.name)
                            req.haveKeyUsers = mutableListOf(it.id)
                        }
                        "SoleUsers".equals(type) -> {
                            nivSoleUsers.setContent(it.name)
                            req.soleUsers = mutableListOf(it.id)
                        }
                        "EntrustUsers".equals(type) -> {
                            nivEntrustUsers.setContent(it.name)
                            req.entrustUsers = mutableListOf(it.id)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        try {
            Bus.unregister(this)
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
