package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.PoiSearch
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.ui.adapter.HouseNearFragmentAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.AllPoiEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_house_near.*
import kotlinx.android.synthetic.main.layout_window_mark.view.*
import java.util.concurrent.TimeUnit


class HouseNearFragment : BaseFragment() {

    lateinit var mapInfo: ItemHouseDetail.MapInfo
    val titles: MutableList<String> = mutableListOf("交通", "教育", "医疗", "生活", "附近")
    val searchKeyWords: MutableList<String> = mutableListOf("公交", "教育", "医疗", "生活", "小区")
    var infoWindow: InfoWindow? = null
    val fragments: MutableList<Fragment> = mutableListOf()
    var cenpt: LatLng? = null
    var mapPoiInfo = HashMap<Int,List<PoiInfo>>()
    var mCurIndex = 0
    var houseNearFragmentAdapter :HouseNearFragmentAdapter?=null
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
        mCurIndex = 0
        mapInfo =
            Gson().fromJson<ItemHouseDetail.MapInfo>(mapJson, ItemHouseDetail.MapInfo::class.java)
        initAdapter()
        initMap()
        initListener()
    }

    private fun initListener() {
        Bus.observe<AllPoiEvent>()
            .subscribe {
                mapPoiInfo.put(it.index,it.poiInfos)
                updateMapMarkers()
            }.registerInBus(this)
        house_near_viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mCurIndex = position
                updateMapMarkers()
            }
        })
    }

    private fun updateMapMarkers() {
        var poiInfos = mapPoiInfo.get(mCurIndex)
        if (poiInfos != null && poiInfos.isNotEmpty()) {
            val optons = MarkerOptions()
            val locView = View.inflate(context!!,R.layout.layout_loc,null)
            val bitmap = BitmapDescriptorFactory
                .fromView(locView)
            val bundle = Bundle()
            bundle.putString("marker", mapInfo.villageName)
            bundle.putDouble("latitude", mapInfo?.latitude!!)
            bundle.putDouble("longitude", mapInfo?.longitude!!)
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
                val windowView = View.inflate(context, R.layout.layout_window_mark, null)
                windowView.tvMarkTitle.text = title
                infoWindow = InfoWindow(
                    windowView,
                    LatLng(latitude, longitude),
                    DensityUtils.dp2px(context, -15f)
                )
                windowList.add(infoWindow!!)
                house_near_MapView?.map?.showInfoWindows(windowList)
                true
            }
            house_near_MapView?.map?.clear()
            poiInfos?.forEach { item ->
                var location = item.location
                val optons = MarkerOptions()
                val latLng = LatLng(location?.latitude!!, location?.longitude!!)
                val bundle = Bundle()
                bundle.putString("marker", item.name)
                bundle.putDouble("latitude", location?.latitude!!)
                bundle.putDouble("longitude", location?.longitude!!)

                val locView = View.inflate(context!!,R.layout.layout_loc,null)
                val bitmap = BitmapDescriptorFactory
                    .fromView(locView)
                optons.position(latLng)
                    .extraInfo(bundle)
                    .icon(bitmap)
                var addOverlay = house_near_MapView?.map?.addOverlay(optons)
            }
        }

    }

    private fun initAdapter() {
        searchKeyWords.forEachIndexed { index, item ->
            fragments.add(
                getFragmentWithArg(
                    HouseNearStatusFragment(),
                    HomeConstant.KEY_LATITUDE to mapInfo?.latitude,
                    HomeConstant.KEY_LONGITUDE to mapInfo?.longitude,
                    HomeConstant.KEY_TITLE to item,
                    HomeConstant.KEY_INDEX to index
                )
            )
        }
        house_near_viewPager.offscreenPageLimit = 1
        house_near_tabLayout.setupWithViewPager(house_near_viewPager)
        houseNearFragmentAdapter =
            HouseNearFragmentAdapter(childFragmentManager, titles, fragments)
        house_near_viewPager.adapter = houseNearFragmentAdapter

    }

    private fun initMap() {
        if (mapInfo?.latitude == null || mapInfo?.longitude == null) {
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
    }

    override fun onPause() {
        house_near_MapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        house_near_MapView?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        try {
            if(mapPoiInfo!=null){
                mapPoiInfo?.clear()
            }
            houseNearFragmentAdapter?.clearData()
            mapPoiInfo?.clear()
            fragments?.clear()
            titles?.clear()
            searchKeyWords?.clear()
            infoWindow = null
            if(house_near_MapView!=null){
                house_near_MapView?.onDestroy()
            }
            Bus.unregister(this)
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

}