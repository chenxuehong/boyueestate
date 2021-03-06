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
import com.huihe.boyueentities.protocol.home.SearchReq
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

        Bus.observe<SearchHouseEvent>()
            .subscribe {
                showItem(it)
            }.registerInBus(this)

        nivSchoolIds.onClick {
            // ????????????
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_DISTRICT)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivCreateUsers.onClick {
            // ???????????????
            type = "CreateUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivImageUsers.onClick {
            // ???????????????
            type = "ImageUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivMaintainUsers.onClick {
            // ???????????????
            type = "MaintainUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivBargainPriceUsers.onClick {
            // ???????????????
            type = "BargainPriceUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivBlockUsers.onClick {
            // ???????????????
            type = "BlockUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivHaveKeyUsers.onClick {
            // ???????????????
            type = "HaveKeyUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivSoleUsers.onClick {
            // ???????????????
            type = "SoleUsers"
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nivEntrustUsers.onClick {
            // ???????????????
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
                    nivSchoolIds?.setContent(it.name)
                    req.schoolIds = mutableListOf(it.id)
                }
                "AddressBookFragment" -> {
                    when (this@SearchHouseFragment.type){
                        "CreateUsers"-> {
                            nivCreateUsers?.setContent(it.name)
                            req.createUsers = mutableListOf(it.id)
                        }
                        "ImageUsers" -> {
                            nivImageUsers?.setContent(it.name)
                            req.imageUsers = mutableListOf(it.id)
                        }
                        "MaintainUsers"-> {
                            nivMaintainUsers?.setContent(it.name)
                            req.maintainUsers = mutableListOf(it.id)
                        }
                        "BargainPriceUsers" -> {
                            nivBargainPriceUsers?.setContent(it.name)
                            req.bargainPriceUsers = mutableListOf(it.id)
                        }
                        "BlockUsers" -> {
                            nivBlockUsers?.setContent(it.name)
                            req.blockUsers = mutableListOf(it.id)
                        }
                        "HaveKeyUsers" -> {
                            nivHaveKeyUsers?.setContent(it.name)
                            req.haveKeyUsers = mutableListOf(it.id)
                        }
                        "SoleUsers" -> {
                            nivSoleUsers?.setContent(it.name)
                            req.soleUsers = mutableListOf(it.id)
                        }
                        "EntrustUsers" -> {
                            nivEntrustUsers?.setContent(it.name)
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
