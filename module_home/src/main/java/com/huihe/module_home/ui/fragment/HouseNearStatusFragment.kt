package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.InfoWindow
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.*
import com.eightbitlab.rxbus.Bus
import com.huihe.module_home.R
import com.huihe.module_home.ext.MyOnGetPoiSearchResultListener
import com.huihe.module_home.ui.adapter.HouseNearRvAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.AllPoiEvent
import kotlinx.android.synthetic.main.fragment_house_near.*
import kotlinx.android.synthetic.main.fragment_house_near_status.*
import kotlinx.android.synthetic.main.layout_window_mark.view.*

class HouseNearStatusFragment : BaseFragment() {
    var houseNearRvAdapter:HouseNearRvAdapter?=null
    var mPoiSearch: PoiSearch? = null
    var latitude:Double ?=null
    var longitude:Double ?=null
    var title:String = ""
    var index:Int = 0
    var poiInfoList :MutableList<PoiInfo> = mutableListOf()
    var listener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {

        override fun onGetPoiResult(poiResult: PoiResult) {
            houseNearRvAdapter?.setData(poiResult.allPoi)
            poiInfoList = poiResult.allPoi
            Bus.send(AllPoiEvent(poiResult.allPoi,index))
        }

        override fun onGetPoiDetailResult(poiDetailSearchResult: PoiDetailSearchResult) {

        }

        override fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {

        }

        //废弃
        override fun onGetPoiDetailResult(poiDetailResult: PoiDetailResult) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_house_near_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latitude = arguments?.getDouble(HomeConstant.KEY_LATITUDE)
        longitude = arguments?.getDouble(HomeConstant.KEY_LONGITUDE)
        title = arguments?.getString(HomeConstant.KEY_TITLE)?:""
        index = arguments?.getInt(HomeConstant.KEY_INDEX,0)?:0
        initAdapter()
        if (latitude!=null && longitude!=null){
            initMap()
        }
    }

    private fun initMap() {
        var cenpt = LatLng(latitude!!,longitude!!)
        mPoiSearch = PoiSearch.newInstance()
        mPoiSearch?.setOnGetPoiSearchResultListener(listener)
        mPoiSearch?.searchNearby(
            PoiNearbySearchOption()
                .location(cenpt)
                .radius(5000)
                .pageCapacity(10)
                .keyword(title)
                .pageNum(0)
        )
    }

    private fun initAdapter() {
         houseNearRvAdapter = HouseNearRvAdapter(context!!)
        houseNearStatusList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = houseNearRvAdapter
        }
    }
}