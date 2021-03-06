package com.huihe.module_home.ui.fragment

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
import com.huihe.boyueentities.protocol.home.HouseDetail
import com.huihe.boyueentities.protocol.home.SetHouseInfoRep
import com.huihe.boyueentities.protocol.home.SetHouseInfoReq
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.SetHouseInfoPresenter
import com.huihe.module_home.presenter.view.SetHouseInfoView
import com.kotlin.base.common.BaseConstant
import com.kotlin.provider.event.VillageEvent
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.base.widgets.picker.WheelPicker.picker.SinglePicker
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.*
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivSalePrice
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivbuilding
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivdefend
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivfloorage
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivgroundFloor
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivhall
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivrNumber
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivrent
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivroom
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nivtotalFloors
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nsvTransaction_type
import kotlinx.android.synthetic.main.layout_fragment_set_house_info.nsvVillage
import org.jetbrains.anko.support.v4.toast


class SetHouseInfoFragment : BaseMvpFragment<SetHouseInfoPresenter>(), SetHouseInfoView {

    lateinit var req: SetHouseInfoReq
    lateinit var mTransactionTypePicker: SinglePicker<String>
    var selectedProvince: String? = null
    var selectedCity: String? = null
    var selectedCounty: String? = null
    lateinit var houseDetail: HouseDetail
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_fragment_set_house_info, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = arguments?.getString(HomeConstant.KEY_HOUSE_DETAIL)
        houseDetail = Gson().fromJson<HouseDetail>(data, HouseDetail::class.java)
        initView()
        initData()
    }

    private fun initData() {
        req = SetHouseInfoReq()
        req.id = houseDetail.id
        req.price = houseDetail.price?.toString()
        req.rent = houseDetail.rent
        req.buyPrice = houseDetail.buyPrice
        req.ownerLowPrice = houseDetail.ownerLowPrice
        req.unitPrice = houseDetail.unitPrice
        req.building = houseDetail.building
        req.hNum = houseDetail.hNum
        req.floor = houseDetail?.floor?.toString()
        req.totalFloor = houseDetail.totalFloor?.toString()
        req.floorage = houseDetail.floorage?.toString()
        req.hShape = houseDetail.hShape
        req.loanState = houseDetail.loanState
        req.orientation = houseDetail.orientation
        req.isOnlyHouse = houseDetail.isOnlyHouse
        req.stairs = houseDetail.stairs
        req.houseHolds = houseDetail.houseHolds
        req.seeTime = houseDetail.seeTime
        req.housePropertyYear = houseDetail.housePropertyYear
        req.decorat = houseDetail.decorat
        req.propertyRightUserSize = houseDetail.propertyRightUserSize
        req.soldReason = houseDetail.soldReason
        req.remarks = houseDetail.remarks

        req.transactionType = houseDetail.transactionType
        req.villageId = houseDetail.villageId
        showTransactionType(req.transactionType)
        if (!TextUtils.isEmpty(selectedCounty))
            nsvVillage.setContent("${selectedProvince}/${selectedCity}/${selectedCounty}")
        nivSalePrice.setContent(req.price)
        nivrent.setContent(req.rent)
        nivbuy_price.setContent(req.buyPrice)
        nivreserve_price.setContent(req.ownerLowPrice)
        nivunit_price.setContent(req.unitPrice)
        nivbuilding.setContent(req.building)
        nivrNumber.setContent(req.hNum)
        nivgroundFloor.setContent(req.floor)
        nivtotalFloors.setContent(req.totalFloor)
        nivfloorage.setContent(req.floorage)
        var room = req.hShape?.split("???")
        if (room?.size!! > 1) {
            nivroom.setContent(room[0])
            var hall = room[1]?.split("???")
            if (hall?.size!! > 1) {
                nivhall.setContent(hall[0])
                var defend = hall[1]?.split("???")
                if (defend?.size!! > 1) {
                    nivdefend.setContent(defend[0])
                }
            }
        }
        nivMortgage_or_not.setContent(req.loanState)
        nivorientation.setContent(req.orientation)
        nivAt_least_five.setContent(req.isOnlyHouse)
        nivladder.setContent(req.stairs)
        nivhousehold.setContent(req.houseHolds)
        nivlook_time.setContent(req.seeTime)
        nivConstruction_period.setContent(req.housePropertyYear)
        nivdecoration.setContent(req.decorat)
        nivproperty_owners.setContent(req.propertyRightUserSize)
        etBuy_reasons.setText(req.soldReason)
        etRewarks.setText(req.remarks)
    }

    private fun initView() {
        nsvTransaction_type.onClick {
            selectTransactionType()
        }
        nsvVillage.onClick {
            selectVillage()
        }
        tvAlterHouseInfo.onClick {
            if (checkInput()) {
                commit()
            }
        }
    }

    private fun selectTransactionType() {
        // ?????? 0, ?????? 1,?????? 2
        mTransactionTypePicker = SinglePicker(
            activity, mutableListOf<String>(
                "??????",
                "??????",
                "??????"
            )
        )
        mTransactionTypePicker?.setCanceledOnTouchOutside(true)
        mTransactionTypePicker?.selectedIndex = 1
        mTransactionTypePicker?.setCycleDisable(true)
        mTransactionTypePicker?.setOnItemPickListener { index, item ->
            var transactionType = 0
            when (item) {
                "??????" -> {
                    transactionType = 0
                    nsvTransaction_type.setContent("??????")
                }
                "??????" -> {
                    transactionType = 1
                    nsvTransaction_type.setContent("??????")
                }
                "??????" -> {
                    transactionType = 2
                    nsvTransaction_type.setContent("??????")
                }
            }
            showTransactionType(transactionType)
            req.transactionType = transactionType

        }
        mTransactionTypePicker?.show()
    }

    private fun showTransactionType(transactionType: Int?) {
        when (transactionType) {
            0 -> {
                nsvTransaction_type.setContent("??????")
            }
            1 -> {
                nsvTransaction_type.setContent("??????")
            }
            2 -> {
                nsvTransaction_type.setContent("??????")
            }
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

    private fun commit() {
        req.price = nivSalePrice.getContent()
        req.rent = nivrent.getContent()
        req.buyPrice = nivbuy_price.getContent()
        req.ownerLowPrice = nivreserve_price.getContent()
        req.unitPrice = nivunit_price.getContent()
        req.building = nivbuilding.getContent()
        req.hNum = nivrNumber.getContent()
        req.floor = nivgroundFloor.getContent()
        req.totalFloor = nivtotalFloors.getContent()
        req.floorage = nivfloorage.getContent()
        req.hShape = "${nivroom.getContent()}???${nivhall.getContent()}???${nivdefend.getContent()}???"
        req.loanState = nivMortgage_or_not.getContent()
        req.orientation = nivorientation.getContent()
        req.isOnlyHouse = nivAt_least_five.getContent()
        req.stairs = nivladder.getContent()
        req.houseHolds = nivhousehold.getContent()
        req.seeTime = nivlook_time.getContent()
        req.housePropertyYear = nivConstruction_period.getContent()
        req.decorat = nivdecoration.getContent()
        req.propertyRightUserSize = nivproperty_owners.getContent()
        req.soldReason = etBuy_reasons.text.toString().trim()
        req.remarks = etRewarks.text.toString().trim()
        mPresenter?.putHouseInfo(req)
    }

    override fun onSetHouseInfoSuccess(t: SetHouseInfoRep?) {
        toast(resources.getString(R.string.commit_successful))
        activity?.finish()
    }

    private fun checkInput(): Boolean {
        var childCount = slSetHouseInfo.childCount
        for (i in 0 until childCount) {
            var childView = slSetHouseInfo.getChildAt(i)
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

    override fun onDestroy() {
        super.onDestroy()
        try {
            Bus.unregister(this)
            mTransactionTypePicker?.dismiss()
        } catch (e: Exception) {
        }
    }
}