package com.huihe.module_home.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.activity.HouseNearActivity
import com.huihe.module_home.ui.holder.ItemHouseDetailHolder
import com.huihe.module_home.ui.holder.ItemHouseRewarksHolder
import com.huihe.module_home.ui.holder.MapHolder
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.layout_house_detail_map_item.view.*
import kotlinx.android.synthetic.main.layout_house_rewarks_info_item.view.*

class HouseBasicInfoRvItemAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<ItemHouseDetail, RecyclerView.ViewHolder>(mContext) {

    var hasLoadMap = false
    var houseDetailMapview: MapView? = null
    lateinit var rvHouseBasicInfo: RecyclerView
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                CustomersModule.HouseDetailType.ITEM_DETAIL_INFO
            }
            1 -> {
                return CustomersModule.HouseDetailType.ITEM_REWARKS_INFO
            }
            else -> CustomersModule.HouseDetailType.MAP
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var holder: RecyclerView.ViewHolder =
            MapHolder(initInflater(mContext, R.layout.layout_house_detail_map_item, parent))
        when (viewType) {
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
            else->{
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
            CustomersModule.HouseDetailType.ITEM_DETAIL_INFO -> {
                initBasicInfo(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_REWARKS_INFO -> {
                var rewarksHolder: ItemHouseRewarksHolder = holder as ItemHouseRewarksHolder
                rewarksHolder.itemView.tvHouseDetailRewarks.text = itemHouseDetail.rewarks
            }
            CustomersModule.HouseDetailType.MAP -> {
                initMap(holder, itemHouseDetail)
            }
        }
    }

    private fun initBasicInfo(holder: RecyclerView.ViewHolder, itemHouseDetail: ItemHouseDetail) {
        var detailHolder: ItemHouseDetailHolder = holder as ItemHouseDetailHolder
        var rvList = detailHolder.itemView as RecyclerView
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.isNestedScrollingEnabled = false
        var houseDetailRvAdapter = HouseDetailRvItemAdapter(mContext)
        rvList.adapter = houseDetailRvAdapter
        houseDetailRvAdapter.setData(itemHouseDetail?.detailInfoList!!)
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

        holder.itemView.house_detail_maplayout.setScrollView(rvHouseBasicInfo)
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
        val locView = View.inflate(mContext,R.layout.layout_loc,null)
        val bitmap = BitmapDescriptorFactory
            .fromView(locView)
        optons.position(latLng)
            .icon(bitmap)
        var addOverlay = mBaiduMap?.addOverlay(optons)
        val bundle = Bundle()
        bundle.putString("villageName", mapInfo?.villageName ?: "")
        addOverlay?.extraInfo = bundle

        houseDetailMapview?.removeViewAt(2)
//        holder.itemView.house_detail_maplayout.setScrollView(mhouseDetailRvlist)
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

    fun setRecylerview(rvHouseBasicInfo: RecyclerView) {
        this.rvHouseBasicInfo = rvHouseBasicInfo
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
