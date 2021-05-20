package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.qqtheme.framework.picker.SinglePicker
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.AddHouseInfoReq
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.AddHousePresenter
import com.huihe.module_home.presenter.view.AddHouseView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.event.VillageEvent
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_add_house.*
import org.jetbrains.anko.support.v4.toast

class AddHouseFragment : BaseMvpFragment<AddHousePresenter>(), AddHouseView{

    var mTransactionTypePicker:SinglePicker<String>?=null
    var req:AddHouseInfoReq?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_house, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        req = AddHouseInfoReq()
        initView()
    }

    private fun initView() {
        tvAddHouse.onClick {
            if (checkInput()){
                commit()
            }
        }
        nsvTransaction_type.onClick {
            selectTransactionType()
        }
        nsvVillage.onClick {
            selectVillage()
        }
    }

    private fun commit() {
        req?.floorage = nivfloorage.getContent()
        req?.price = nivSalePrice.getContent()
        req?.rent = nivrent.getContent()
        req?.building = nivbuilding.getContent()
        req?.hNum = nivrNumber.getContent()
        req?.ownerName = nivName.getContent()
        req?.floor = nivgroundFloor.getContent()
        req?.totalFloor = nivtotalFloors.getContent()
        req?.hShape = "${nivroom.getContent()}室${nivhall.getContent()}厅${nivdefend.getContent()}卫"
        req?.ownerTel = getTel()
        mPresenter?.addHouseInfo(
            req!!
        )
    }

    private fun getTel(): String? {
        var data:MutableList<String> =  mutableListOf()
        if (!TextUtils.isEmpty(nivPhone.getContent())){
            data.add(nivPhone.getContent()!!)
        }
        if (!TextUtils.isEmpty(nivPhone1.getContent())){
            data.add(nivPhone1.getContent()!!)
        }
        if (!TextUtils.isEmpty(nivPhone2.getContent())){
            data.add(nivPhone2.getContent()!!)
        }
        var sb = StringBuffer()
        data.forEach { item ->
            sb.append("*").append(item)
        }
        if (data.size>0){
            return sb.substring(1).toString()
        }else{
            return ""
        }
    }

    private fun selectVillage() {
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_COMMUNITY_MANAGER)
            .withBoolean(BaseConstant.KEY_ISSELECT,true)
            .navigation()
        Bus.observe<VillageEvent>()
            .subscribe {
                req?.villageId = it.id?:""
                nsvVillage.setContent("${it.villageName}")
            }.registerInBus(this)

    }

    private fun selectTransactionType() {
        // 出售 0, 出租 1,租售 2
        mTransactionTypePicker = SinglePicker(
            activity, mutableListOf<String>(
                "出售",
                "出租",
                "租售"
            )
        )
        mTransactionTypePicker?.setCanceledOnTouchOutside(true)
        mTransactionTypePicker?.selectedIndex = 1
        mTransactionTypePicker?.setCycleDisable(true)
        mTransactionTypePicker?.setOnItemPickListener { index, item ->
            var transactionType = 0
            when (item) {
                "出售" -> {
                    transactionType = 0
                    nsvTransaction_type.setContent("出售")
                }
                "出租" -> {
                    transactionType = 1
                    nsvTransaction_type.setContent("出租")
                }
                "租售" -> {
                    transactionType = 2
                    nsvTransaction_type.setContent("租售")
                }
            }
            showTransactionType(transactionType)
            req?.transactionType = transactionType

        }
        mTransactionTypePicker?.show()
    }

    private fun showTransactionType(transactionType: Int) {
        when (transactionType) {
            0 -> {
                nsvTransaction_type.setContent("出售")
            }
            1 -> {
                nsvTransaction_type.setContent("出租")
            }
            2 -> {
                nsvTransaction_type.setContent("租售")
            }
        }
    }

    private fun checkInput(): Boolean {
        var childCount = llAddHouse.childCount
        if (!TextUtils.isEmpty(nivrNumber.getContent()) && nivrNumber.getContent()?.length !=4){
            toast("房间号长度必须为4")
            return false
        }
        for (i in 0 until childCount) {
            var childView = llAddHouse.getChildAt(i)
            if (childView is NecessaryTitleSelectView) {
                if (childView.isNecessary && TextUtils.isEmpty(childView.getContent())) {
                    var tipContentText = childView.getTipContentText()
                    toast(tipContentText)
                    return false
                }
            } else if (childView is NecessaryTitleInputView) {
                if (childView.isNecessary && TextUtils.isEmpty(childView.getContent())) {
                    var tipContentText = childView.getHintContent()
                    toast(tipContentText)
                    return false
                }
            }
        }
        return true
    }

    override fun onAddHouseInfoSuccess(t: SetHouseInfoRep?) {
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            Bus.unregister(this)
            mTransactionTypePicker?.dismiss()
        } catch (e: Exception) {
        }
    }
}


