package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.ItemHouseDetail
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseOwnerInfoPresenter
import com.huihe.module_home.presenter.view.HouseOwnerInfoView
import com.huihe.module_home.ui.activity.EntrustUserActivity
import com.huihe.module_home.ui.activity.HaveKeyUserActivity
import com.huihe.module_home.ui.activity.SoleUserActivity
import com.huihe.module_home.ui.adapter.HouseDetailOwnerRvAdapter
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_house_owner_info.*
import org.jetbrains.anko.support.v4.startActivity

class HouseOwnerInfoFragment : BaseMvpFragment<HouseOwnerInfoPresenter>(),HouseOwnerInfoView {

    var id = ""
    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_owner_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = arguments?.getString(HomeConstant.KEY_HOUSE_DETAIL) ?: ""
        id = arguments?.getString(HomeConstant.KEY_HOUSE_ID) ?: ""
        var houseDetail = Gson().fromJson<ItemHouseDetail>(
            data,
            ItemHouseDetail::class.java
        )
        initAdapter(houseDetail)
    }

    private fun initAdapter(houseDetail: ItemHouseDetail) {
        rvHouseOwnerInfo.vertical()
        var houseDetailOwnerRvAdapter = HouseDetailOwnerRvAdapter(context!!)
        houseDetailOwnerRvAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<ItemHouseDetail.OwnerInfo>{
            override fun onItemClick(view: View, item: ItemHouseDetail.OwnerInfo, position: Int) {
                onUserClicked(item)
            }
        })
        rvHouseOwnerInfo.adapter = houseDetailOwnerRvAdapter
        houseDetailOwnerRvAdapter.setData(houseDetail.ownerInfoList)
    }

    var ownerType: Int = -1
    fun onUserClicked(item: ItemHouseDetail.OwnerInfo) {
        ownerType = item.type
        if (TextUtils.isEmpty(item.uid)) {
            when (item.type) {
                CustomersModule.OwnerInfoType.maintainUser,
                CustomersModule.OwnerInfoType.imageUser,
                CustomersModule.OwnerInfoType.bargainPriceUser -> {
                    ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                        .withBoolean(BaseConstant.KEY_ISSELECT, true)
                        .navigation(context)
                }

                CustomersModule.OwnerInfoType.entrustUser -> {
                    startActivity<EntrustUserActivity>(HomeConstant.KEY_HOUSE_ID to id)
                }
                CustomersModule.OwnerInfoType.haveKeyUser -> {
                    startActivity<HaveKeyUserActivity>(HomeConstant.KEY_HOUSE_ID to id)
                }
                CustomersModule.OwnerInfoType.soleUser -> {
                    startActivity<SoleUserActivity>(HomeConstant.KEY_HOUSE_ID to id)
                }
            }
        } else {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_DEPTINFO)
                .withString(UserConstant.KEY_USER_ID, item.uid)
                .navigation()
        }
    }
}
