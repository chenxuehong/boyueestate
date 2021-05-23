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
import com.huihe.module_home.data.protocol.EntrustUserRep
import com.huihe.module_home.data.protocol.EntrustUserReq
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.EntrustUserPresenter
import com.huihe.module_home.presenter.view.EntrustUserView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.OwnerInfoPutEvent
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_entrustuser.*
import org.jetbrains.anko.support.v4.toast

class EntrustUserFragment : BaseMvpFragment<EntrustUserPresenter>(), EntrustUserView {

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    var mEstateTypes :MutableList<String> = mutableListOf("住宅","办公楼","商品","车位")
    var mEstateTypePicker:SinglePicker<String>?=null
    var mEntrustUserReq = EntrustUserReq()
    var houseId:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_entrustuser, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseId = arguments?.getString(HomeConstant.KEY_HOUSE_ID)?:""
        initView()
        initData()
    }

    private fun initView() {
        Bus.observe<SearchHouseEvent>()
            .subscribe {
                setOwnerInfo(it)
            }.registerInBus(this)

        entrustUser.onClick {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }

        estateType.onClick {
            showEstateType()
        }

        tvEntrustuserCreateCopy.onClick {
            if (checkInput()) {
                mEntrustUserReq.houseId = houseId
                mEntrustUserReq.brokerUser = brokerUser.getContent()
                mEntrustUserReq.district = district.getContent()
                mEntrustUserReq.road = road.getContent()
                mEntrustUserReq.lane = lane.getContent()
                mEntrustUserReq.number = number.getContent()
                mEntrustUserReq.room = room.getContent()
                mEntrustUserReq.carportNum = carportNum.getContent()
                mEntrustUserReq.floorage = floorage.getContent()
                mEntrustUserReq.powerUser = powerUser.getContent()
                mEntrustUserReq.decorat = decorat.getContent()
                mEntrustUserReq.facility = facility.getContent()
                mEntrustUserReq.isMortgage = isMortgage.getContent()
                mEntrustUserReq.mortgageUser = mortgageUser.getContent()
                mEntrustUserReq.mortgageBalance = mortgageBalance.getContent()
                mEntrustUserReq.isLease = isLease.getContent()
                mEntrustUserReq.housePrice = housePrice.getContent()
                mPresenter.putHouseEntrust(mEntrustUserReq)
            }
        }
    }

    private fun setOwnerInfo(it: SearchHouseEvent?) {
        mEntrustUserReq.entrustUser = it?.id
        entrustUser?.setContent(it?.name?:"")
    }


    private fun initData() {
//        mPresenter.getHouseEntrust(houseId!!)
    }

    private fun showEstateType() {
        mEstateTypePicker = SinglePicker(activity, mEstateTypes);
        mEstateTypePicker?.setCanceledOnTouchOutside(true)
        mEntrustUserReq.estateType?.apply {
            mEstateTypePicker?.selectedIndex = this
        }
        mEstateTypePicker?.setCycleDisable(true)
        mEstateTypePicker?.setOnItemPickListener { index, item ->
            //1:住宅,2:办公楼,3:商品,4:车位
            mEntrustUserReq.estateType = index + 1
            estateType.setContent(item)
        };
        mEstateTypePicker?.show()
    }

    private fun checkInput(): Boolean {
        var childCount = llEntrustuser.childCount
        for (i in 0 until childCount) {
            var childView = llEntrustuser.getChildAt(i)
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
        try {
            if (mEstateTypePicker!=null){
                mEstateTypePicker?.dismiss()
            }
            Bus.unregister(this)
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    override fun onPutEntrustUserSuccess(t: EntrustUserRep?) {
        toast("设置成功")
        Bus.send(OwnerInfoPutEvent())
        activity?.finish()
    }

    override fun onEntrustUserSuccess(t: EntrustUserRep?) {
        entrustUser.setContent(t?.entrustUserName?:"")
        estateType.setContent(getEstateType(t?.estateType?:1))
        brokerUser.setContent(t?.brokerUser?:"")
        district.setContent(t?.district?:"")
        road.setContent(t?.road?:"")
        lane.setContent(t?.lane?:"")
        number.setContent(t?.number?:"")
        room.setContent(t?.room?:"")
        carportNum.setContent(t?.carportNum?:"")
        floorage.setContent(t?.floorage?:"")
        powerUser.setContent(t?.powerUser?:"")
        decorat.setContent(t?.decorat?:"")
        facility.setContent(t?.facility?:"")
        isMortgage.setContent(t?.isMortgage?:"")
        mortgageUser.setContent(t?.mortgageUser?:"")
        mortgageBalance.setContent(t?.mortgageBalance?:"")
        isLease.setContent(t?.isLease?:"")
        housePrice.setContent(t?.housePrice?:"")

        mEntrustUserReq.estateType = t?.estateType
        mEntrustUserReq.entrustUser = t?.entrustUser
    }

    private fun getEstateType(estateType: Int): String {
        // "住宅","办公楼","商品","车位"
        return when(estateType){
            1->{
                "住宅"
            }
            2->{
                "办公楼"
            }
            3->{
                "商品"
            }
            else->{
                "车位"
            }
        }
    }

}