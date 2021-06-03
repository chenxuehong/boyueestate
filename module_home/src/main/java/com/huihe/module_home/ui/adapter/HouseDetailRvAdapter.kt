package com.huihe.module_home.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.activity.HouseFollowActivity
import com.huihe.module_home.ui.activity.HouseLogHomeActivity
import com.huihe.module_home.ui.activity.HouseNearActivity
import com.huihe.module_home.ui.activity.HouseTakeLookRecordActivity
import com.huihe.module_home.ui.holder.*
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.base.widgets.GridViewItemDecoration
import com.kotlin.provider.constant.HomeConstant
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RectangleIndicator
import kotlinx.android.synthetic.main.layout_house_detail_banner_item.view.*
import kotlinx.android.synthetic.main.layout_house_detail_map_item.view.*
import kotlinx.android.synthetic.main.layout_house_rewarks_info_item.view.*


class HouseDetailRvAdapter(mContext: Context?, var mListener: OnListener) :
    BaseRecyclerViewAdapter<ItemHouseDetail, RecyclerView.ViewHolder>(mContext!!) {

    var banner: Banner<HouseDetail.ImagUrlsBean, ImageAdapter>? = null
    var mId: String? = ""

    var houseDetailMapview: MapView? = null
    var mhouseDetailRvlist: RecyclerView? = null
    var hasLoadMap = false

    init {
        hasLoadMap = false
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return CustomersModule.HouseDetailType.BANNER
            }
            1 -> {
                return CustomersModule.HouseDetailType.BASIC_INFO
            }
            2 -> {
                return CustomersModule.HouseDetailType.ITEM_DETAIL_INFO
            }
            3 -> {
                return CustomersModule.HouseDetailType.ITEM_REWARKS_INFO
            }
            4 -> {
                return CustomersModule.HouseDetailType.ITEM_OWNER_INFO
            }
            5 -> {
                return CustomersModule.HouseDetailType.ITEM_PHOTO
            }
            6 -> {
                return CustomersModule.HouseDetailType.ITEM_REFERURL
            }
            else -> return CustomersModule.HouseDetailType.MAP
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        // CustomersModule.HouseDetailType.MAP
        var holder: RecyclerView.ViewHolder =
            MapHolder(initInflater(mContext, R.layout.layout_house_detail_map_item, parent))
        when (viewType) {
            CustomersModule.HouseDetailType.BANNER -> holder = BannerHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_detail_banner_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.BASIC_INFO -> holder = HouseBasicInfoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_basic_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_DETAIL_INFO -> holder = ItemHouseDetailHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_detail_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_REWARKS_INFO -> holder = ItemHouseRewarksHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_rewarks_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_OWNER_INFO -> holder = ItemHouseOwnerInfoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_owner_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_PHOTO -> holder = ItemPhotoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_photo_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_REFERURL -> holder = ItemRefeurlHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_referurl_item,
                    parent
                )
            )

            else -> {
                holder =
                    MapHolder(initInflater(mContext, R.layout.layout_house_detail_map_item, parent))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemHouseDetail = dataList[position]
        when (getItemViewType(position)) {
            CustomersModule.HouseDetailType.BANNER -> {
                initBanner(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.BASIC_INFO -> {
                initBasicInfo(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_DETAIL_INFO -> {
                initDetailInfoList(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_REWARKS_INFO -> {
                var rewarksHolder: ItemHouseRewarksHolder = holder as ItemHouseRewarksHolder
                rewarksHolder.itemView.tvHouseDetailRewarks.text = itemHouseDetail.rewarks
            }
            CustomersModule.HouseDetailType.ITEM_OWNER_INFO -> {
                initOwnerInfoList(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_PHOTO -> {
                initPhotos(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_REFERURL -> {
                initReferurl(holder, itemHouseDetail)
            }

            CustomersModule.HouseDetailType.MAP -> {
                initMap(holder, itemHouseDetail)
            }

        }
    }

    private fun initMap(holder: RecyclerView.ViewHolder, itemHouseDetail: ItemHouseDetail) {
        if (hasLoadMap) {
            return
        }
        var mapInfo = itemHouseDetail.mapInfo
        if (mapInfo?.latitude == null || mapInfo?.longitude == null) {
//           Log.i()
            return
        }
        houseDetailMapview = holder.itemView.house_detail_mapView
        var mBaiduMap = houseDetailMapview?.map
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
        val bundle = Bundle()
        bundle.putString("villageName", mapInfo?.villageName ?: "")
        addOverlay?.extraInfo = bundle

        houseDetailMapview?.removeViewAt(2)
        holder.itemView.house_detail_maplayout.setScrollView(mhouseDetailRvlist)
        mBaiduMap?.setOnMarkerClickListener {
            val extraInfo: Bundle = it.extraInfo
            val content = extraInfo.getInt("villageName")
            Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show()
            true
        }
        holder.itemView.house_detail_map_right_title.onClick {
            val intent = Intent(mContext, HouseNearActivity::class.java)
            intent.putExtra(HomeConstant.KEY_MAP_BEAN, Gson().toJson(mapInfo))
            mContext.startActivity(intent)
        }
        hasLoadMap = true
    }

    private fun initDetailInfoList(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var detailHolder: ItemHouseDetailHolder = holder as ItemHouseDetailHolder
        var rvList = detailHolder.itemView as RecyclerView
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.isNestedScrollingEnabled = false
        var houseDetailRvAdapter = HouseDetailRvItemAdapter(mContext)
        rvList.adapter = houseDetailRvAdapter
        houseDetailRvAdapter.setData(itemHouseDetail?.detailInfoList!!)
    }

    private fun initOwnerInfoList(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var detailHolder: ItemHouseOwnerInfoHolder = holder as ItemHouseOwnerInfoHolder
        var rvList = detailHolder.itemView as RecyclerView
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.isNestedScrollingEnabled = false
        var houseDetailRvAdapter = HouseDetailOwnerRvAdapter(mContext)
        rvList.adapter = houseDetailRvAdapter
        houseDetailRvAdapter.setOnItemClickListener(object :
            OnItemClickListener<ItemHouseDetail.OwnerInfo> {
            override fun onItemClick(view: View, item: ItemHouseDetail.OwnerInfo, position: Int) {

                mListener?.onUserClicked(item)
            }

        })
        houseDetailRvAdapter.setData(itemHouseDetail?.ownerInfoList!!)
    }

    private fun initBanner(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var bannerHolder: BannerHolder = holder as BannerHolder
        banner =
            bannerHolder.itemView.houseDetailBanner as Banner<HouseDetail.ImagUrlsBean, ImageAdapter>?
        bannerHolder.itemView.houseDetailBanner?.apply {
            adapter = ImageAdapter(mContext, itemHouseDetail.bannerList)
            indicator = RectangleIndicator(mContext)
            setIndicatorSelectedColorRes(R.color.white)
            setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            setBannerGalleryMZ(
                mContext.resources.getDimensionPixelOffset(R.dimen.dp_6),
                0.8f
            )

            setOnBannerListener { data, position ->
                mListener?.onViewPhoto(
                    (data as HouseDetail.ImagUrlsBean).url ?: "",
                    getPhotoList(itemHouseDetail.bannerList ?: mutableListOf()),
                    position,
                    bannerHolder.itemView
                )
            }

            setBannerRound(mContext.resources.getDimension(R.dimen.dp_10))
        }
    }

    private fun initBasicInfo(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var basicHolder: HouseBasicInfoHolder = holder as HouseBasicInfoHolder
        basicHolder.tvTitle.text =
            "${itemHouseDetail?.basicInfo?.villageName}-${itemHouseDetail?.basicInfo?.building}-${itemHouseDetail?.basicInfo?.hNum}"
        basicHolder.tvPrice.text = "${itemHouseDetail?.basicInfo?.price}万"
        var amount = itemHouseDetail?.basicInfo?.floorage?.toBigDecimal()
            ?.let { itemHouseDetail?.basicInfo?.price?.div(it) }
        var argePrice = YuanFenConverter.getRoundFloor(amount)
        basicHolder.tvArgePrice.text = "${argePrice}万/m²"
        basicHolder.tvFloorage.text = "${itemHouseDetail?.basicInfo?.floorage}m²"
        basicHolder.tvHShape.text = "${itemHouseDetail?.basicInfo?.hShape}"
        var split = itemHouseDetail?.basicInfo?.label?.split(";")
        basicHolder.tags.tags =
            if (split != null && split.size > 5) split?.subList(0, 4) else getNotNullData(
                split
            )

        basicHolder.tvTel.onClick {
            mListener?.onShowTelListDialog(mId,basicHolder.tvTel)
        }
        basicHolder.tvFollow.onClick {
            var intent = Intent(mContext, HouseFollowActivity::class.java)
            intent.putExtra(HomeConstant.KEY_HOUSE_CODE, itemHouseDetail.houseCode)
            intent.putExtra(HomeConstant.KEY_HOUSE_ID, mId)
            mContext.startActivity(intent)
        }
        basicHolder.tvTakeLook.onClick {
            var intent = Intent(mContext, HouseTakeLookRecordActivity::class.java)
            intent.putExtra(HomeConstant.KEY_CODE, itemHouseDetail.houseCode)
            mContext.startActivity(intent)
        }
        basicHolder.tvLog.onClick {
            var intent = Intent(mContext, HouseLogHomeActivity::class.java)
            intent.putExtra(HomeConstant.KEY_HOUSE_CODE, itemHouseDetail.houseCode)
            mContext.startActivity(intent)
        }
    }

    private fun initPhotos(holder: RecyclerView.ViewHolder, itemHouseDetail: ItemHouseDetail) {
        var viewHolder = holder as ItemPhotoHolder
        var imagUrls = itemHouseDetail.imagUrls
        var houseDetailPhotoRvAdapter = HouseDetailPhotoRvAdapter(mContext)
        var linearLayoutManager = GridLayoutManager(mContext, 4)
        viewHolder.mRvHousePhoto.apply {
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
            adapter = houseDetailPhotoRvAdapter
            if (itemDecorationCount == 0)
                addItemDecoration(
                    GridViewItemDecoration(
                        4,
                        mContext.resources.getDimensionPixelOffset(R.dimen.dp_15)
                    )
                )
        }
        houseDetailPhotoRvAdapter.setOnItemClickListener(object :
            OnItemClickListener<HouseDetail.ImagUrlsBean> {
            override fun onItemClick(view: View, item: HouseDetail.ImagUrlsBean, position: Int) {


                mListener?.onViewPhoto(
                    item.url ?: "",
                    getPhotoList(houseDetailPhotoRvAdapter.dataList),
                    position,
                    view
                )
            }

        })
        houseDetailPhotoRvAdapter.setData(imagUrls)
    }

    var photoList = mutableListOf<String>()
    private fun getPhotoList(dataList: MutableList<HouseDetail.ImagUrlsBean>): List<String> {
        photoList?.clear()
        dataList.forEach { item ->
            photoList.add(item.url ?: "")
        }
        return photoList
    }

    private fun getReferPhotoList(dataList: MutableList<HouseDetail.ReferUrlsBean>): List<String> {
        photoList?.clear()
        dataList.forEach { item ->
            photoList.add(item.url ?: "")
        }
        return photoList
    }

    private fun initReferurl(holder: RecyclerView.ViewHolder, itemHouseDetail: ItemHouseDetail) {
        var viewHolder = holder as ItemRefeurlHolder
        var imagUrls = itemHouseDetail.referUrls
        var houseDetailReferImageRvAdapter = HouseDetailReferImageRvAdapter(mContext)
        var linearLayoutManager = GridLayoutManager(mContext, 4)
        viewHolder.mRvHousePhoto.apply {
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
            adapter = houseDetailReferImageRvAdapter
            if (itemDecorationCount == 0)
                addItemDecoration(
                    GridViewItemDecoration(
                        4,
                        mContext.resources.getDimensionPixelOffset(R.dimen.dp_15)
                    )
                )
        }
        houseDetailReferImageRvAdapter.setOnItemClickListener(object :
            OnItemClickListener<HouseDetail.ReferUrlsBean> {
            override fun onItemClick(view: View, item: HouseDetail.ReferUrlsBean, position: Int) {
                mListener?.onViewPhoto(
                    item.url ?: "",
                    getReferPhotoList(houseDetailReferImageRvAdapter.dataList),
                    position,
                    view
                )
            }

        })
        houseDetailReferImageRvAdapter.setData(imagUrls)

    }

    private fun getNotNullData(split: List<String>?): List<String> {
        return split ?: mutableListOf()
    }

    fun onStart() {
        //开始轮播
        banner?.start()
    }

    fun onStop() {
        //停止轮播
        banner?.stop()
    }

    fun onPause() {
        houseDetailMapview?.onPause()
    }

    fun onResume() {
        houseDetailMapview?.onResume()
    }

    fun onDestroy() {
        //销毁
        try {
            banner?.destroy()
            houseDetailMapview?.onDestroy()
        } catch (e: Exception) {
        }
    }

    fun setId(id: String?) {
        mId = id
    }

    fun setRecyclerview(houseDetailRvlist: RecyclerView?) {
        mhouseDetailRvlist = houseDetailRvlist
    }

    interface OnListener {
        fun onUserClicked(item: ItemHouseDetail.OwnerInfo)
        fun onViewPhoto(
            photo: String,
            photoList: List<String>,
            position: Int,
            itemView: View
        )

        fun onShowTelListDialog(houseId: String?, tvTel: TextView)
    }
}