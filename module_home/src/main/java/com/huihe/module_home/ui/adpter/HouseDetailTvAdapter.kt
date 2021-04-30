package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

class HouseDetailTvAdapter(mContext: Context?) :
    BaseRecyclerViewAdapter<ItemHouseDetail, RecyclerView.ViewHolder>(mContext!!) {

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
                return CustomersModule.HouseDetailType.ITEM_OWNER_INFO
            }
            4 -> {
                return CustomersModule.HouseDetailType.ITEM_PHOTO
            }
            else -> return CustomersModule.HouseDetailType.MAP
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        // CustomersModule.HouseDetailType.MAP
        var holder: RecyclerView.ViewHolder = MapHolder(initInflater(mContext, R.layout.layout_house_detail_map_item,parent))
        if (viewType == CustomersModule.HouseDetailType.BANNER) {
            holder = BannerHolder(initInflater(mContext,R.layout.layout_house_detail_banner_item,parent))
        } else if (viewType == CustomersModule.HouseDetailType.BASIC_INFO) {
            holder = HouseBasicInfoHolder(initInflater(mContext,R.layout.layout_house_basic_info_item,parent))
        } else if (viewType == CustomersModule.HouseDetailType.ITEM_DETAIL_INFO) {
            holder = ItemHouseDetailHolder(initInflater(mContext,R.layout.layout_house_detail_info_item,parent))
        } else if (viewType == CustomersModule.HouseDetailType.ITEM_OWNER_INFO) {
            holder = ItemHouseOwnerInfoHolder(initInflater(mContext,R.layout.layout_house_owner_info_item,parent))
        } else if (viewType == CustomersModule.HouseDetailType.ITEM_PHOTO) {
            holder = ItemHouseOwnerInfoHolder(initInflater(mContext,R.layout.layout_house_photo_item,parent))
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    internal inner class BannerHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class HouseBasicInfoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class ItemHouseDetailHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class ItemHouseOwnerInfoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class ItemPhotoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class MapHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

}