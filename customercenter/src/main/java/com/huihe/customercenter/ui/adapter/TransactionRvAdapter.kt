package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R
import com.huihe.customercenter.data.protocol.CustomerRep
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.DateUtils
import kotlinx.android.synthetic.main.layout_transaction_item.view.*

class TransactionRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerRep.Customer, TransactionRvAdapter.ViewHolder>(mContext) {
    var colors: List<IntArray> = mutableListOf()
    val intArr1 =
        intArrayOf(R.color.common_red, R.color.white, R.color.white, R.color.white)
    val intArr2 = intArrayOf(
        R.color.tao_bg_color,
        R.color.tao_bg_color,
        R.color.tao_bg_color,
        R.color.tao_bg_color
    )
    val intArr3 = intArrayOf(
        R.color.color_66999999,
        R.color.color_66999999,
        R.color.color_66999999,
        R.color.color_66999999
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_transaction_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var item = dataList[position]
        holder.itemView.tvCustomerFirstLetter.text = getFirstLetter(item.customerName)
        holder.itemView.tvCustomerUserName.text = "${item.customerName ?: ""}"
        holder.itemView.tvCustomerDemandBeat.text = "需求区域: ${item.demandBeat ?: ""}"
        holder.itemView.tvCustomerDemandFloor.text = "需求楼层: ${item.demandFloor ?: ""}"
        holder.itemView.tvCustomerDemandCreateDate.text = "${DateUtils.stringToString(
            item.createDate ?: "",
            DateUtils.datePattern,
            DateUtils.FORMAT_SHORT
        )} ${item.createUserName}"
        val tags = mutableListOf<String>()
        var customerType = item.customerType ?: "1"
        if (1 == customerType && "1" == item.isTop) {
            tags.add("置顶")
        } else if ( 1 != customerType && "1" == item.isCornucopia) {
            tags.add("淘")
        }

        var takeLookCount = item.takeLookCount ?: 0
        if (takeLookCount > 0) {
            tags.add("带看${takeLookCount}")
        }
        colors = colors.toMutableList()
        (colors as MutableList<IntArray>).clear()
        if (1 == customerType && "1" == item.isTop) {
            (colors as MutableList<IntArray>).add(intArr1)
        }
        if (1 != customerType && "1" == item.isCornucopia) {
            (colors as MutableList<IntArray>).add(intArr2)
        }
        if (takeLookCount > 0) {
            (colors as MutableList<IntArray>).add(intArr3)
        }
        holder.itemView.item_customers_tags.setTags(tags, colors)
    }

    private fun getFirstLetter(item: String?): String {
        var customerName = item ?: ""
        if (customerName.isNotEmpty()) {
            return customerName.substring(0, 1)
        }
        return ""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}