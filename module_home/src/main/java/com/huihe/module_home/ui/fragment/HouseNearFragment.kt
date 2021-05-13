package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.poi.*
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.ext.MyOnGetPoiSearchResultListener
import com.huihe.module_home.ui.adpter.HouseNearFragmentAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_near.*
import kotlinx.android.synthetic.main.layout_window_mark.view.*


class HouseNearFragment : BaseFragment() {

    lateinit var mapInfo: ItemHouseDetail.MapInfo
    val titles: MutableList<String> = mutableListOf("交通", "教育", "医疗", "生活", "附近")
    val searchKeyWords: MutableList<String> = mutableListOf("公交", "教育", "医疗", "生活", "小区")
    val fragments: MutableList<Fragment> = mutableListOf()
    var mPoiSearch: PoiSearch? = null
    var mListenerList: MutableList<MyOnGetPoiSearchResultListener> = mutableListOf()
    var cenpt: LatLng? = null
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
        mListenerList.add(HouseNearStatusFragment())
        mListenerList.add(HouseNearStatusFragment())
        mListenerList.add(HouseNearStatusFragment())
        mListenerList.add(HouseNearStatusFragment())
        mListenerList.add(HouseNearStatusFragment())
        fragments.add(mListenerList[0] as Fragment)
        fragments.add(mListenerList[1] as Fragment)
        fragments.add(mListenerList[2] as Fragment)
        fragments.add(mListenerList[3] as Fragment)
        fragments.add(mListenerList[4] as Fragment)
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

        cenpt = LatLng(mapInfo?.latitude!!, mapInfo?.longitude!!)
        //定义地图状态
        val mMapStatus: MapStatus = MapStatus.Builder()
            .target(cenpt)
            .zoom(17.2f)
            .build()

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        //改变地图状态
        mBaiduMap?.setMapStatus(mMapStatusUpdate)
        house_near_MapView?.removeViewAt(2)
        mPoiSearch = PoiSearch.newInstance()
        mPoiSearch?.setOnGetPoiSearchResultListener(listener)
        mPoiSearch?.searchNearby(
            PoiNearbySearchOption()
                .location(cenpt)
                .radius(5000)
                .pageCapacity(20)
                .keyword(searchKeyWords[0])
                .pageNum(0)
        )
        house_near_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mPoiSearch?.searchNearby(
                    PoiNearbySearchOption()
                        .location(cenpt)
                        .radius(5000)
                        .keyword(searchKeyWords[position])
                        .pageCapacity(20)
                        .pageNum(0)
                )
            }
        })
    }

    override fun onDestroy() {
        mPoiSearch?.destroy();
        mListenerList?.clear()
        super.onDestroy()
    }

    var infoWindow:InfoWindow?=null
    var listener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {

        override fun onGetPoiResult(poiResult: PoiResult) {
            mListenerList.forEach { listener ->
                listener?.onGetPoiResult(poiResult)
            }
            val optons = MarkerOptions()
            val bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location)
            val bundle = Bundle()
            bundle.putString("marker",mapInfo.villageName)
            bundle.putDouble("latitude",mapInfo?.latitude!!)
            bundle.putDouble("longitude",mapInfo?.longitude!!)
            optons.position(cenpt)
                .extraInfo(bundle)
                .icon(bitmap)
            var addOverlay = house_near_MapView?.map?.addOverlay(optons)

            house_near_MapView?.map?.setOnMarkerClickListener { marker ->
                house_near_MapView?.map?.hideInfoWindow()
                val extraInfo = marker.extraInfo
                var title = extraInfo.getString("marker")
                var latitude = extraInfo.getDouble("latitude")
                var longitude = extraInfo.getDouble("longitude")
                val windowList = mutableListOf<InfoWindow>()
                val windowView = View.inflate(context,R.layout.layout_window_mark,null)
                windowView.tvMarkTitle.text = title
                infoWindow = InfoWindow(windowView, LatLng(latitude,longitude),DensityUtils.dp2px(context,-15f))
                windowList.add(infoWindow!!)
                house_near_MapView?.map?.showInfoWindows(windowList)
                true
            }
            var allPoi = poiResult.allPoi
            house_near_MapView?.map?.clear()
            allPoi?.forEach { item ->
                var location = item.location
                val optons = MarkerOptions()
                val latLng = LatLng(location?.latitude!!, location?.longitude!!)
                val bundle = Bundle()
                bundle.putString("marker",item.name)
                bundle.putDouble("latitude",location?.latitude!!)
                bundle.putDouble("longitude",location?.longitude!!)
                val bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.location)
                optons.position(latLng)
                    .extraInfo(bundle)
                    .icon(bitmap)
                var addOverlay = house_near_MapView?.map?.addOverlay(optons)
            }
        }

        override fun onGetPoiDetailResult(poiDetailSearchResult: PoiDetailSearchResult) {
            mListenerList.forEach { listener ->
                listener?.onGetPoiDetailResult(poiDetailSearchResult)
            }
        }

        override fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {
            mListenerList.forEach { listener ->
                listener?.onGetPoiIndoorResult(poiIndoorResult)
            }
        }

        //废弃
        override fun onGetPoiDetailResult(poiDetailResult: PoiDetailResult) {
            mListenerList.forEach { listener ->
                listener?.onGetPoiDetailResult(poiDetailResult)
            }
        }
    }
}