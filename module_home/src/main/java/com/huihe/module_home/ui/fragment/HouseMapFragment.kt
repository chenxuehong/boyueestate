package com.huihe.module_home.ui.fragment

import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.district.DistrictSearch
import com.baidu.mapapi.search.district.DistrictSearchOption
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.*
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseMapPresenter
import com.huihe.module_home.presenter.view.FindHouseByMapView
import com.huihe.module_home.ui.adapter.RvAreaDistrictAdapter
import com.huihe.module_home.ui.widget.ISearchResultListener
import com.huihe.module_home.ui.widget.SearchResultViewController
import com.kennyc.view.MultiStateView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.LogUtils
import com.kotlin.base.utils.ReflectionUtil
import com.kotlin.provider.constant.HomeConstant
import com.huihe.boyueentities.protocol.common.District
import com.kotlin.base.common.OnRefreshListener
import com.kotlin.provider.event.AddHouseEvent
import com.kotlin.provider.event.ResetMapEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_findhousebymap.dropDownMenu
import kotlinx.android.synthetic.main.layout_area_num.view.*
import kotlinx.android.synthetic.main.layout_house_map.*


class HouseMapFragment : BaseMvpFragment<HouseMapPresenter>(), FindHouseByMapView,
    ISearchResultListener, OnRefreshListener {

    val WHAT_ADDOVERLAY = 100

    internal var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var item: MapAreaRep = msg.obj as MapAreaRep
            //????????????
            val center = LatLng(item?.latitude!!, item?.longitude!!)
            //??????Marker??????
            var contentView: View
            when (type) {
                2 -> {
                    contentView = View.inflate(context, R.layout.layout_dis_num, null)
                    contentView.tvAreaTitle.text = "${item?.name ?: ""}(${item?.num ?: ""})"
                }
                else -> {
                    contentView = View.inflate(context, R.layout.layout_area_num, null)
                    contentView.tvAreaTitle.text = item?.name ?: ""
                    contentView.tvAreaNum.text = "${item?.num ?: ""}???"
                }
            }

            val bitmap = BitmapDescriptorFactory.fromView(contentView)
            //??????MarkerOption???????????????????????????Marker
            val option: OverlayOptions = MarkerOptions()
                .position(center)
                .icon(bitmap)
            //????????????????????????
            var addOverlay = house_map_MapView?.map?.addOverlay(option)
            // ????????????
            if (type == 2) {
                val bundle = Bundle()
                bundle.putString(HomeConstant.KEY_VILLAGE_IDS, item?.id ?: "")
                addOverlay?.extraInfo = bundle
            }
        }
    }

    val TAG = HouseMapFragment::class.java.simpleName
    private lateinit var mSearchResultViewController: SearchResultViewController
    private val headers = arrayOf("??????", "??????", "??????")
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

    private var moreReq: MoreReq? = MoreReq()
    private var villageIds: MutableList<String>? = null
    private var type = 0
    private var curZoomLevel = 12f

    var mDistrictSearchlistener: OnGetDistricSearchResultListener =
        OnGetDistricSearchResultListener {
            var centerPt = it.centerPt
            LogUtils.i(
                TAG,
                "??????????????????longitude = ${centerPt?.longitude ?: ""},latitude = ${centerPt?.latitude
                    ?: ""}"
            )
            changeMapStatus(centerPt)
        }

    private fun changeMapStatus(centerPt: LatLng?) {
        //??????????????????
        val mMapStatus: MapStatus = MapStatus.Builder()
            .target(centerPt)
            .zoom(curZoomLevel)
            .build()
        //??????MapStatusUpdate??????????????????????????????????????????????????????
        val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        //??????????????????
        house_map_MapView?.map?.setMapStatus(mMapStatusUpdate)
        resetLocRanges()
        initData()
    }

    var minLongitude = 0.0
    var minLatitude = 0.0
    var maxLatitude = 0.0
    var maxLongitude = 0.0
    private fun resetLocRanges() {
        // ??????
        val pt = Point()
        pt.x = 0
        pt.y = 0
        leftTopPtLL = house_map_MapView.map.projection.fromScreenLocation(pt)
        pt.x = dm?.widthPixels!!;
        pt.y = dm?.heightPixels!!;
        // ??????
        rightBottomPtLL = house_map_MapView.map.projection.fromScreenLocation(pt)

        var longitude = leftTopPtLL?.longitude ?: 0.0
        var longitude1 = rightBottomPtLL?.longitude ?: 0.0
        if (longitude > longitude1) {
            minLongitude = longitude1
            maxLongitude = longitude
        } else {
            minLongitude = longitude
            maxLongitude = longitude1
        }
        var latitude = leftTopPtLL?.latitude ?: 0.0
        var latitude1 = rightBottomPtLL?.latitude ?: 0.0
        if (latitude > latitude1) {
            minLatitude = latitude1
            maxLatitude = latitude
        } else {
            minLatitude = latitude
            maxLatitude = latitude1
        }
        LogUtils.i(
            TAG,
            "??????????????????longitude = ${minLongitude ?: ""},latitude = ${minLatitude ?: ""}"
        )
        LogUtils.i(
            TAG,
            "??????????????????longitude = ${maxLongitude ?: ""},latitude = ${maxLatitude ?: ""}"
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
        type = 0
        curZoomLevel = 12f
        dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        initEvent()
        initView()
        initMap()
    }

    private fun initEvent() {
        Bus.observe<AddHouseEvent>()
            .subscribe {
                initData()
            }.registerInBus(this)
        Bus.observe<ResetMapEvent>()
            .subscribe {
                showLoading()
                resetReqData()
                headers.forEachIndexed { index, item ->
                    setTabText(index, item)
                }
                initData()
            }.registerInBus(this)
    }

    fun setTabText(position: Int, title: String?) {
        var tabMenuView: LinearLayout? = null
        try {
            tabMenuView = ReflectionUtil.getValue(dropDownMenu, "tabMenuView") as LinearLayout
            var tabs = getTab(tabMenuView)
            tabs[position].text = title

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var list = mutableListOf<TextView>()
    private fun getTab(tabMenuView: LinearLayout): MutableList<TextView> {
        if (list.size>0){
            return list
        }
        for (index in 0 until tabMenuView.childCount) {
            var childAt = tabMenuView.getChildAt(index)
            if (childAt is TextView) {
                list.add(childAt)
            }
        }
        return list
    }

    private fun initMap() {

        house_map_MapView?.removeViewAt(2)
        var mBaiduMap = house_map_MapView?.map
        mBaiduMap?.mapType = BaiduMap.MAP_TYPE_NORMAL
        mDistrictSearch = DistrictSearch.newInstance()
        mDistrictSearch?.setOnDistrictSearchListener(mDistrictSearchlistener)
        house_map_MapView?.map?.setOnMapLoadedCallback {
            // ??????
            mDistrictSearch?.searchDistrict(
                DistrictSearchOption()
                    .cityName("?????????")
            )
        }
        house_map_MapView?.map?.setOnMarkerClickListener {
//            ???????????????  zoomLevel =14.7  ????????????   zoomLevel  = 19   ???????????? zoomLevel  =19
            curZoomLevel = when (type) {
                0 -> {
                    14.7f
                }
                1 -> {
                    19f
                }
                else -> {
                    val extraInfo: Bundle = it.extraInfo
                    val villageIds = extraInfo.getString(HomeConstant.KEY_VILLAGE_IDS) ?: ""
                    ARouter.getInstance()
                        .build(RouterPath.UserCenter.PATH_SEARCHHOUSELIST_ACTIVITY)
                        .withString(HomeConstant.KEY_VILLAGE_IDS, villageIds)
                        .withInt(BaseConstant.KEY_STATUS,BaseConstant.KEY_STATUS_MAP)
                        .navigation()
                    18f
                }
            }
            changeType(curZoomLevel)
            var mapStatus = house_map_MapView?.map?.mapStatus
            if (mapStatus != null) {
                changeMapStatus(mapStatus.target)
            }
            true
        }
        house_map_MapView?.map?.setOnMapStatusChangeListener(object :
            BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(p0: MapStatus?) {

            }

            override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {

            }

            override fun onMapStatusChange(p0: MapStatus?) {

            }

            override fun onMapStatusChangeFinish(p0: MapStatus?) {
                if (house_map_MapView.map.projection == null) {
                    return
                }
                var zoom = p0?.zoom!!
                changeType(zoom)
                resetLocRanges()
                initData()
            }
        })
    }

    private fun changeType(zoom: Float) {
//        ????????????  type=0  zoomLevel <14.5    type=1    18> zoomLevel>14.5     type=2   zoomLevel>18
        type = if (zoom < 14.5) {
            0
        } else if (zoom > 14.5 && zoom < 18) {
            1
        } else {
            2
        }
    }

    private fun initView() {
        layoutContentView =
            LayoutInflater.from(context).inflate(R.layout.layout_house_map, null)
        mSearchResultViewController =
            SearchResultViewController(context!!, dropDownMenu.isShowing)
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

    override fun onRefresh() {
        initData()
    }

    fun resetReqData(){
        mFloorRanges =null
        mPriceRanges=null
        moreReq = MoreReq()
        villageIds = null
        type = 0
    }

    override fun onGetHouseMapResult(list: MutableList<MapAreaRep>?) {
        try {
            house_map_MapView?.map?.clear()
            mHandler.removeMessages(WHAT_ADDOVERLAY)
            list?.forEach { item ->
                val msg = Message.obtain()
                msg.what = WHAT_ADDOVERLAY
                msg.obj = item
                mHandler.sendMessage(msg)
            }
        } catch (e: Exception) {
            LogUtils.i(TAG, e.toString())
        }
    }

    private fun getData(t: MutableList<MapAreaRep>?, maxCount: Int): MutableList<MapAreaRep> {
        var data = t ?: mutableListOf()
        if (data.size > maxCount) {
            return data.subList(0, maxCount)
        }
        return data
    }

    override fun onGetAreaBeanListResult(list: MutableList<District>?) {
        mRvAreaDistrictAdapter?.setData(list ?: mutableListOf())
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
     * @desc ??????????????????
     */
    override fun startLoad(
        adapter: RvAreaDistrictAdapter?,
        mMultiStateView: MultiStateView
    ) {
        mRvAreaDistrictAdapter = adapter
        mPresenter?.getVillages()
    }

    override fun getSearchModules(): MutableList<Int> {
        return mutableListOf(
            CustomersModule.SearchType.FloorsType,
            CustomersModule.SearchType.PriceType,
            CustomersModule.SearchType.MoreType
        )
    }

    override fun onResume() {
        house_map_MapView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        house_map_MapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        try {
            mDistrictSearch?.destroy()
            Bus.unregister(this)
            mHandler.removeMessages(WHAT_ADDOVERLAY)
            house_map_MapView?.onDestroy()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}