package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.qqtheme.framework.entity.Province
import cn.qqtheme.framework.picker.AddressPicker
import cn.qqtheme.framework.picker.SinglePicker
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.AddHouseInfoReq
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.ext.getConvertProvinceList
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.AddHousePresenter
import com.huihe.module_home.presenter.view.AddHouseView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.provider.data.protocol.District
import kotlinx.android.synthetic.main.fragment_add_house.*
import kotlinx.android.synthetic.main.fragment_add_house.nivfloorage
import kotlinx.android.synthetic.main.fragment_add_house.nsvTransaction_type
import kotlinx.android.synthetic.main.fragment_add_house.nsvVillage
import org.jetbrains.anko.support.v4.toast

class AddHouseFragment : BaseMvpFragment<AddHousePresenter>(), AddHouseView{

    var mTransactionTypePicker:SinglePicker<String>?=null
    var req:AddHouseInfoReq?=null
    var mProvinceList: ArrayList<Province>? = null
    var selectedCounty: String? = null
    var mAddresspicker: AddressPicker? = null
    var selectedProvince: String? = null
    var selectedCity: String? = null

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
            if (mProvinceList != null && mProvinceList!!.size > 0) {
                selectVillage()
            } else {
                mPresenter.getVillages()
            }
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

    override fun onGetAreaBeanListResult(list: MutableList<District>?) {
        mProvinceList = getConvertProvinceList(list)
        selectVillage()
    }

    private fun selectVillage() {
        mAddresspicker = AddressPicker(activity, mProvinceList)
        mAddresspicker?.setColumnWeight(2 / 8.0f, 3 / 8.0f, 3 / 8.0f)//省级、地级和县级的比例为2:3:3
        if (!TextUtils.isEmpty(selectedCounty)) {
            mAddresspicker?.setSelectedItem(selectedProvince, selectedCity, selectedCounty)
        }
        mAddresspicker?.setOnAddressPickListener { province, city, county ->
            if (province == null || city ==null || county==null){
                return@setOnAddressPickListener
            }
            selectedProvince = province.areaName?:""
            selectedCity = city.areaName?:""
            selectedCounty = county.areaName?:""
            req?.villageId = county.areaId?:""
            nsvVillage.setContent("${selectedProvince}/${selectedCity}/${selectedCounty}")
        }
        mAddresspicker?.show()
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
            mTransactionTypePicker?.dismiss()
        } catch (e: Exception) {
        }
    }
}
