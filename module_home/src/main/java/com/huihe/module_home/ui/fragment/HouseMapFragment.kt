package com.huihe.module_home.ui.fragment

import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.district.DistrictSearch
import com.baidu.mapapi.search.district.DistrictSearchOption
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseMapReq
import com.huihe.module_home.data.protocol.ISearchResult
import com.huihe.module_home.data.protocol.MapAreaRep
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseMapPresenter
import com.huihe.module_home.presenter.view.FindHouseByMapView
import com.huihe.module_home.ui.adpter.RvAreaDistrictAdapter
import com.huihe.module_home.ui.widget.ISearchResultListener
import com.huihe.module_home.ui.widget.SearchResultViewController
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_findhousebymap.*
import kotlinx.android.synthetic.main.layout_area_num.view.*
import kotlinx.android.synthetic.main.layout_house_map.*


class HouseMapFragment : BaseMvpFragment<HouseMapPresenter>(), FindHouseByMapView,
    ISearchResultListener {

    val TAG = HouseMapFragment::class.java.simpleName
    private lateinit var mSearchResultViewController: SearchResultViewController
    private val headers = arrayOf("区域", "楼层", "价格", "更多")
    var layoutContentView: View? = null
    var dm: DisplayMetrics? = null
    var leftTopPtLL: LatLng? = null
    var rightBottomPtLL: LatLng? = null
    var mDistrictSearch: DistrictSearch? = null
    var mDistrictSearchlistener: OnGetDistricSearchResultListener =
        OnGetDistricSearchResultListener {
            var centerPt = it.centerPt
            LogUtils.i(
                TAG,
                "中心经纬度：longitude = ${centerPt?.longitude ?: ""},latitude = ${centerPt?.latitude ?: ""}"
            )
            //定义地图状态
            val mMapStatus: MapStatus = MapStatus.Builder()
                .target(centerPt)
                .zoom(14f)
                .build()
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
            //改变地图状态
            house_map_MapView?.map?.setMapStatus(mMapStatusUpdate)

            // 左上
            val pt = Point()
            pt.x = 0
            pt.y = 0
            leftTopPtLL = house_map_MapView.map.projection.fromScreenLocation(pt)
            pt.x = dm?.widthPixels!!;
            pt.y = dm?.heightPixels!!;
            // 右下
            rightBottomPtLL = house_map_MapView.map.projection.fromScreenLocation(pt)

            LogUtils.i(
                TAG,
                "最小经纬度：longitude = ${leftTopPtLL?.longitude ?: ""},latitude = ${leftTopPtLL?.latitude ?: ""}"
            )
            LogUtils.i(
                TAG,
                "最大经纬度：longitude = ${rightBottomPtLL?.longitude ?: ""},latitude = ${rightBottomPtLL?.latitude ?: ""}"
            )
            initData()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_findhousebymap, container, false)
    }


    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        initView()
        initMap()
    }

    private fun initMap() {

        house_map_MapView?.removeViewAt(2)
        var mBaiduMap = house_map_MapView?.map
        mBaiduMap?.mapType = BaiduMap.MAP_TYPE_NORMAL
        var mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(mDistrictSearchlistener);
        house_map_MapView?.map?.setOnMapLoadedCallback { // 左上
            mDistrictSearch.searchDistrict(
                DistrictSearchOption()
                    .cityName("上海市")
            )
        }
    }

    private fun initView() {
        layoutContentView =
            LayoutInflater.from(context).inflate(R.layout.layout_house_map, null)
        mSearchResultViewController =
            SearchResultViewController.init(context!!, dropDownMenu.isShowing)
        var allViews = mSearchResultViewController.getAllViews(this)
        dropDownMenu.setDropDownMenu(
            headers.asList(),
            allViews,
            layoutContentView!!
        )
    }

    private fun initData() {

        if (leftTopPtLL == null || rightBottomPtLL == null) {
            return
        }
        val longitudeRanges = mutableListOf<HouseMapReq.LongitudeBean>()
        longitudeRanges.add(
            HouseMapReq.LongitudeBean(
                leftTopPtLL?.longitude,
                rightBottomPtLL?.longitude
            )
        )
        val latitudeRanges = mutableListOf<HouseMapReq.LatitudeBean>()
        latitudeRanges.add(
            HouseMapReq.LatitudeBean(
                rightBottomPtLL?.latitude,
                leftTopPtLL?.latitude
            )
        )
        mPresenter.getMapRoomList(
            HouseMapReq(
                longitudeRanges = longitudeRanges,
                latitudeRanges = latitudeRanges
            )
        )
    }

    override fun onGetHouseMapResult(t: MutableList<MapAreaRep>?) {
        t?.forEach { item ->

            //圆心位置
            val center = LatLng(item?.latitude!!, item?.longitude!!)
            //构建Marker图标
            val contentView = View.inflate(context, R.layout.layout_area_num, null)
            contentView.tvAreaTitle.text = item?.name?:""
            contentView.tvAreaNum.text = "${item?.num?:""}套"
            val bitmap = BitmapDescriptorFactory.fromView(contentView)
            //构建MarkerOption，用于在地图上添加Marker
            val option: OverlayOptions = MarkerOptions()
                .position(center)
                .icon(bitmap)
            //在地图上显示文本
            house_map_MapView?.map?.addOverlay(option)
        }
    }

    override fun onSearchResult(iSearchResult: ISearchResult?, showTip: String, type: Int) {

    }

    /**
     * @desc 加载小区数据
     */
    override fun startLoad(adapter: RvAreaDistrictAdapter?) {

    }

    override fun getModuleType(): Int {
        return SearchResultViewController.MODULE_MAP_HOUSE_FRAGMENT
    }

    override fun onDestroy() {
        mDistrictSearch?.destroy()
        super.onDestroy()
    }
}