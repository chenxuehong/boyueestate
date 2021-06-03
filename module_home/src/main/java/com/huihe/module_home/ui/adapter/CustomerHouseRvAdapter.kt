package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.CustomerProfileInfo
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.DateUtils.FORMAT_SHORT
import com.kotlin.base.utils.DateUtils.datePattern
import kotlinx.android.synthetic.main.layout_customers_house_item.view.*
import kotlinx.android.synthetic.main.layout_customers_item.view.*

class CustomerHouseRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerProfileInfo.HouseInfo, CustomerHouseRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_customers_house_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var houseInfo = dataList[position]
        holder.itemView.item_customers_house_iv_cover.loadUrl(houseInfo?.coverImageUrl,R.drawable.is_empty)
        holder.itemView.item_customers_house_tv_title.text = houseInfo?.villageName
        holder.itemView.item_customers_house_tv_childTitle.text = String.format(
            holder.itemView.item_customers_house_tv_childTitle.context.resources.getString(R.string.customers_childTitle),
            houseInfo?.floorage,houseInfo?.hShape,houseInfo?.floor,houseInfo?.totalFloor)
        holder.itemView.item_customers_house_tv_latestFollowTime.text = String.format(
            holder.itemView.item_customers_house_tv_latestFollowTime.context.resources.getString(R.string.latestFollowTime),
            "未跟进")
        houseInfo?.latestFollowTime?.apply {
            holder.itemView.item_customers_house_tv_latestFollowTime.text = String.format(
                holder.itemView.item_customers_tv_latestFollowTime.context.resources.getString(R.string.latestFollowTime),
                DateUtils.stringToString(this,datePattern,FORMAT_SHORT))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
