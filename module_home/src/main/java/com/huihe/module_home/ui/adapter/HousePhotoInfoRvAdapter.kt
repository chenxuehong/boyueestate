package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseDetail

import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.holder.*
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.widgets.GridViewItemDecoration
import kotlinx.android.synthetic.main.layout_house_rewarks_info_item.view.*

class HousePhotoInfoRvAdapter(mContext: Context,var listener:OnListener) :
    BaseRecyclerViewAdapter<ItemHouseDetail, RecyclerView.ViewHolder>(mContext) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                CustomersModule.HouseDetailType.ITEM_PHOTO
            }
            else -> {
                CustomersModule.HouseDetailType.ITEM_REFERURL
            }
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
            CustomersModule.HouseDetailType.ITEM_PHOTO -> holder = ItemPhotoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_photo_item,
                    parent
                )
            )

            else -> {
                holder =
                    ItemRefeurlHolder(
                        initInflater(
                            mContext,
                            R.layout.layout_house_referurl_item,
                            parent
                        )
                    )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemHouseDetail = dataList[position]
        when (getItemViewType(position)) {
            CustomersModule.HouseDetailType.ITEM_PHOTO -> {
                initPhotos(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_REFERURL -> {
                initReferurl(holder, itemHouseDetail)
            }
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


                listener?.onViewPhoto(
                    item.url ?: "",
                    getPhotoList(houseDetailPhotoRvAdapter.dataList),
                    position,
                    view
                )
            }

        })
        houseDetailPhotoRvAdapter.setData(imagUrls)
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
                listener?.onViewPhoto(
                    item.url ?: "",
                    getReferPhotoList(houseDetailReferImageRvAdapter.dataList),
                    position,
                    view
                )
            }

        })
        houseDetailReferImageRvAdapter.setData(imagUrls)

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
    interface OnListener {
        fun onViewPhoto(
            photo: String,
            photoList: List<String>,
            position: Int,
            itemView: View
        )

    }
}
