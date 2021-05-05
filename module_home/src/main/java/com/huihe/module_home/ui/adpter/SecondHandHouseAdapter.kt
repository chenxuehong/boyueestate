package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.House
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.DateUtils.FORMAT_SHORT
import com.kotlin.base.utils.DateUtils.datePattern
import com.kotlin.base.utils.YuanFenConverter
import kotlinx.android.synthetic.main.layout_customers_item.view.*


class SecondHandHouseAdapter (context: Context) : BaseRecyclerViewAdapter<House, SecondHandHouseAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(
                R.layout.layout_customers_item,
                parent,
                false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        var customer = dataList[position]
        holder.itemView.item_customers_iv_cover.loadUrl(customer?.coverImageUrl)
        // 小区名villageName+座栋building+房号hNum组成
        holder.itemView.item_customers_tv_title.text = String.format(
            holder.itemView.item_customers_tv_title.context.resources.getString(R.string.customers_title),
            customer?.villageName,customer?.building,customer?.hNum)
        // 由 面积floorage房型hShape楼层floor/总楼层totalFloor
        holder.itemView.item_customers_tv_childTitle.text = String.format(
            holder.itemView.item_customers_tv_childTitle.context.resources.getString(R.string.customers_childTitle),
            customer?.floorage,customer?.hShape,customer?.floor,customer?.totalFloor)
        customer?.createTime?.apply {
            holder.itemView.item_customers_tv_createUser.text = String.format(
                holder.itemView.item_customers_tv_createUser.context.resources.getString(com.huihe.module_home.R.string.createUser),
                customer?.createUserName, DateUtils.stringToString(this,datePattern,FORMAT_SHORT))
        }
        customer?.latestFollowTime?.apply {
            holder.itemView.item_customers_tv_latestFollowTime.text = String.format(
                holder.itemView.item_customers_tv_latestFollowTime.context.resources.getString(R.string.latestFollowTime),
                DateUtils.stringToString(this,datePattern,FORMAT_SHORT))
        }

        holder.itemView.item_customers_tv_total_price.text = "${YuanFenConverter.getRoundFloor(customer?.price)}${"万"}"
        var amount = customer?.floorage?.toBigDecimal()?.let { customer.price?.div(it) }
        var argePrice = YuanFenConverter.getRoundFloor(amount)
        holder.itemView.item_customers_tv_sprice.text = "$argePrice${"万/m²"}"
        var split = customer?.label?.split(";")

        holder.itemView.item_customers_tags.tags = if (split!=null && split.size>5) split?.subList(0,4) else getNotNullData(split)

    }

    private fun getNotNullData(split: List<String>?): List<String>{
        return split ?: mutableListOf()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
