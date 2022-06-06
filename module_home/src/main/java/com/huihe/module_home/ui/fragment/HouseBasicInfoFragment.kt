package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.ItemHouseDetail
import com.huihe.module_home.ui.adapter.HouseBasicInfoRvItemAdapter
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_basic_info.*

class HouseBasicInfoFragment : BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_basic_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = arguments?.getString(HomeConstant.KEY_HOUSE_DETAIL)?:""
        var houseDetail = Gson().fromJson<ItemHouseDetail>(
            data,
            ItemHouseDetail::class.java
        )
        initAdapter(houseDetail)
    }

    private fun initAdapter(houseDetail:ItemHouseDetail) {
        rvHouseBasicInfo.vertical()
        var houseBasicInfoRvItemAdapter = HouseBasicInfoRvItemAdapter(context!!)
        houseBasicInfoRvItemAdapter.setRecylerview(rvHouseBasicInfo)
        rvHouseBasicInfo.adapter = houseBasicInfoRvItemAdapter
        houseBasicInfoRvItemAdapter.setData(mutableListOf(
            ItemHouseDetail(detailInfoList= houseDetail.detailInfoList),
            ItemHouseDetail(rewarks= houseDetail.rewarks),
            ItemHouseDetail(mapInfo= houseDetail.mapInfo)
        ))
    }
}
