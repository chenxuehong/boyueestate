package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.ui.adapter.HouseDetailOwnerRvAdapter
import com.huihe.module_home.ui.adapter.HousePhotoInfoRvAdapter
import com.kotlin.base.ext.vertical
import com.kotlin.base.ext.viewPhoto
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_photo_info.*

class HousePhotoInfoFragment : BaseFragment(), HousePhotoInfoRvAdapter.OnListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_photo_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = arguments?.getString(HomeConstant.KEY_HOUSE_DETAIL) ?: ""
        var houseDetail = Gson().fromJson<ItemHouseDetail>(
            data,
            ItemHouseDetail::class.java
        )
        initAdapter(houseDetail)
    }

    private fun initAdapter(houseDetail: ItemHouseDetail) {
        rvHousePhotoInfo.vertical()
        var housePhotoInfoRvAdapter = HousePhotoInfoRvAdapter(context!!,this)
        rvHousePhotoInfo.adapter = housePhotoInfoRvAdapter
        housePhotoInfoRvAdapter.setData(
            mutableListOf(ItemHouseDetail(imagUrls = houseDetail.imagUrls),
                ItemHouseDetail(referUrls = houseDetail.referUrls))
        )
    }

    override fun onViewPhoto(
        photo: String,
        photoList: List<String>,
        position: Int,
        itemView: View
    ) {
        itemView.viewPhoto(context, position, photoList)
    }

}
