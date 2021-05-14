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
import com.huihe.module_home.data.protocol.*
import com.huihe.module_home.ext.getConvertData
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
import kotlinx.android.synthetic.main.fragment_findhousebymap.dropDownMenu
import kotlinx.android.synthetic.main.fragment_house_near.*
import kotlinx.android.synthetic.main.fragment_secondhandhouse.*
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
    var mRvAreaDistrictAdapter: RvAreaDistrictAdapter? = null
    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    private var mFloorRanges: MutableList<FloorReq>? = null
    private var mPriceRanges: MutableList<PriceReq>? = null
    private var sortReq: SortReq? = SortReq()
    private var moreReq: MoreReq? = MoreReq()
    private var villageIds: MutableList<String>? = null
    private var type = 0
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
                .zoom(12f)
                .build()
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
            //改变地图状态
            house_map_MapView?.map?.setMapStatus(mMapStatusUpdate)

            resetLocRanges()
            initData()
        }

    var minLongitude =0.0
    var minLatitude =0.0
    var maxLatitude =0.0
    var maxLongitude =0.0
    private fun resetLocRanges() {
        // 左上
        val pt = Point()
        pt.x = 0
        pt.y = 0
        leftTopPtLL = house_map_MapView.map.projection.fromScreenLocation(pt)
        pt.x = dm?.widthPixels!!;
        pt.y = dm?.heightPixels!!;
        // 右下
        rightBottomPtLL = house_map_MapView.map.projection.fromScreenLocation(pt)

        var longitude = leftTopPtLL?.longitude?:0.0
        var longitude1 = rightBottomPtLL?.longitude?:0.0
        if (longitude>longitude1){
            minLongitude = longitude1
            maxLongitude = longitude
        }else{
            minLongitude = longitude
            maxLongitude = longitude1
        }
        var latitude = leftTopPtLL?.latitude?:0.0
        var latitude1 = rightBottomPtLL?.latitude?:0.0
        if (latitude>latitude1){
            minLatitude = latitude1
            maxLatitude = latitude
        }else{
            minLatitude = latitude
            maxLatitude = latitude1
        }
        LogUtils.i(
            TAG,
            "最小经纬度：longitude = ${minLongitude ?: ""},latitude = ${minLatitude ?: ""}"
        )
        LogUtils.i(
            TAG,
            "最大经纬度：longitude = ${maxLongitude ?: ""},latitude = ${maxLatitude ?: ""}"
        )
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
        house_map_MapView?.map?.setOnMapStatusChangeListener(object :BaiduMap.OnMapStatusChangeListener{
            override fun onMapStatusChangeStart(p0: MapStatus?) {

            }

            override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {

            }

            override fun onMapStatusChange(p0: MapStatus?) {

            }

            override fun onMapStatusChangeFinish(p0: MapStatus?) {
                if(house_map_MapView.map.projection==null){
                    return
                }
                var zoom = p0?.zoom!!
                type = if (zoom < 14.5) {
                    0
                } else if (zoom > 14.5 && zoom < 16) {
                    1
                } else {
                    2
                }
                resetLocRanges()
                initData()
            }
        })
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
                minLongitude,
                maxLongitude
            )
        )
        val latitudeRanges = mutableListOf<HouseMapReq.LatitudeBean>()
        latitudeRanges.add(
            HouseMapReq.LatitudeBean(
                minLatitude,
                maxLatitude
            )
        )
        mPresenter.getMapRoomList(
            HouseMapReq(
                longitudeRanges = longitudeRanges,
                latitudeRanges = latitudeRanges,
                floorRanges = mFloorRanges,
                priceRanges = mPriceRanges,
                hFlag = moreReq?.hFlag,
                days = moreReq?.days,
                myHouse = moreReq?.myHouse,
                hasKey = moreReq?.hasKey,
                hasSole = moreReq?.hasSole,
                myMaintain = moreReq?.myMaintain,
                isCirculation = moreReq?.isCirculation,
                entrustHouse = moreReq?.entrustHouse,
                myCollect = moreReq?.myCollect,
                floorageRanges = moreReq?.floorageRanges,
                roomNumRanges = moreReq?.roomNumRanges,
                villageIds = villageIds,
                type = type
            )
        )
    }

    override fun onGetHouseMapResult(t: MutableList<MapAreaRep>?) {
        try {
            house_map_MapView?.map?.clear()
            t?.forEach { item ->

                //圆心位置
                val center = LatLng(item?.latitude!!, item?.longitude!!)
                //构建Marker图标
                val contentView = View.inflate(context, R.layout.layout_area_num, null)
                contentView.tvAreaTitle.text = item?.name ?: ""
                contentView.tvAreaNum.text = "${item?.num ?: ""}套"
                val bitmap = BitmapDescriptorFactory.fromView(contentView)
                //构建MarkerOption，用于在地图上添加Marker
                val option: OverlayOptions = MarkerOptions()
                    .position(center)
                    .icon(bitmap)
                //在地图上显示文本
                house_map_MapView?.map?.addOverlay(option)
            }
        } catch (e: Exception) {
            LogUtils.i(TAG,e.toString())
        }
    }

    override fun onGetAreaBeanListResult(areaBeans: MutableList<AreaBean>?) {
        var data = getConvertData(areaBeans)
        mRvAreaDistrictAdapter?.setData(data)
    }

    override fun onSearchResult(iSearchResult: ISearchResult?, showTip: String, type: Int) {
        showLoading()
        dropDownMenu.setTabText(showTip)
        resetData()
        when (type) {
            CustomersModule.SearchType.AreaType -> {
                if (iSearchResult != null) {
                    var areaReq = iSearchResult as AreaReq
                    villageIds = areaReq.villageIds
                }
            }
            CustomersModule.SearchType.FloorsType -> {
                if (iSearchResult != null) {
                    mFloorRanges = mutableListOf()
                    var floorResult = iSearchResult as FloorReq
                    mFloorRanges?.add(
                        FloorReq(
                            floorResult.floorMore!!,
                            floorResult.floorLess!!
                        )
                    )
                } else {
                    mFloorRanges = null
                }
            }
            CustomersModule.SearchType.PriceType -> {
                if (iSearchResult != null) {
                    mPriceRanges = mutableListOf()
                    var priceResult = iSearchResult as PriceReq
                    mPriceRanges?.add(PriceReq(priceResult.priceMore!!, priceResult.priceLess!!))
                } else {
                    mPriceRanges = null
                }

            }
            CustomersModule.SearchType.MoreType -> {
                if (iSearchResult != null) {
                    moreReq = iSearchResult as MoreReq
                } else {
                    moreReq = null
                }
            }
        }
        initData()
        dropDownMenu.closeMenu()
    }

    private fun resetData() {
        mCurrentPage = 1
    }

    /**
     * @desc 加载小区数据
     */
    override fun startLoad(adapter: RvAreaDistrictAdapter?) {
        mRvAreaDistrictAdapter = adapter
        mPresenter?.getVillages()
    }

    override fun getModuleType(): Int {
        return SearchResultViewController.MODULE_MAP_HOUSE_FRAGMENT
    }

    override fun onResume() {
        super.onResume()
        house_map_MapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        house_map_MapView?.onPause()
    }

    override fun onDestroy() {
        mDistrictSearch?.destroy()
        house_map_MapView?.onDestroy()
        super.onDestroy()
    }
}