package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.module_home.data.protocol.CustomerProfileInfo
import com.kotlin.base.ext.getFirstChar
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_sale_customer_item.view.*

class SaleCustomerRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerProfileInfo.CustomerInfo, SaleCustomerRvAdapter.ViewHolder>(
        mContext
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_sale_customer_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var customerInfo = dataList[position]
        holder.itemView.tvSaleCustomerLetter.text = getFirstChar(customerInfo.customerName)
        holder.itemView.tvSaleCustomerName.text = customerInfo.customerName
        holder.itemView.tvSaleCustomerIsTop.setVisible(customerInfo.isTop != null && customerInfo.isTop == 1)
        holder.itemView.tvSaleCustomerArea.text = customerInfo.demandFloor?:""
        holder.itemView.tvSaleCustomerRoad.text = customerInfo.demandBeat?:""
        holder.itemView.tvSaleCustomerTime.text =  "${customerInfo?.createDate?:""} ${customerInfo?.createUserName?:""}"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
