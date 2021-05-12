package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.ui.adpter.HouseNearFragmentAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_near.*

class HouseNearFragment() : BaseFragment() {

    lateinit var mapInfo: ItemHouseDetail.MapInfo
    val titles: MutableList<String> = mutableListOf("交通", "教育", "医疗", "生活", "附近")
    val fragments: MutableList<Fragment> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return LayoutInflater.from(context).inflate(R.layout.fragment_house_near, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mapJson = arguments?.getString(HomeConstant.KEY_MAP_BEAN)
        mapInfo =
            Gson().fromJson<ItemHouseDetail.MapInfo>(mapJson, ItemHouseDetail.MapInfo::class.java)
        initAdapter()
        initView()
    }

    private fun initAdapter() {
        house_near_tabLayout.setupWithViewPager(house_near_viewPager)
        house_near_viewPager.adapter =
            HouseNearFragmentAdapter(childFragmentManager, titles, fragments)
    }

    private fun initView() {
        if (mapInfo?.latitude == null || mapInfo?.longitude == null) {
//           Log.i()
            return
        }
        var mBaiduMap = house_near_MapView?.map
        mBaiduMap?.mapType = BaiduMap.MAP_TYPE_NORMAL

        val cenpt = LatLng(mapInfo?.latitude!!, mapInfo?.longitude!!)
        //定义地图状态
        val mMapStatus: MapStatus = MapStatus.Builder()
            .target(cenpt)
            .zoom(17.2f)
            .build()

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        //改变地图状态
        mBaiduMap?.setMapStatus(mMapStatusUpdate)

        val optons = MarkerOptions()
        val latLng = LatLng(mapInfo?.latitude!!, mapInfo?.longitude!!)
        val bitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.location)
        optons.position(latLng)
            .icon(bitmap)
        var addOverlay = mBaiduMap?.addOverlay(optons)
        house_near_MapView?.removeViewAt(2)
    }
}