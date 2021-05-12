package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.module_home.data.protocol.CustomerProfileInfo
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_customers_profile_info_item.view.*

class CustomerProfileRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerProfileInfo?, RecyclerView.ViewHolder>(mContext) {

    val VIEW_TYPE_HOUSE = 1000
    val VIEW_TYPE_SALE_CUSTOMER = 1001
    val VIEW_TYPE_RENT_CUSTOMER = 1002
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_HOUSE
            1 -> VIEW_TYPE_SALE_CUSTOMER
            else -> VIEW_TYPE_RENT_CUSTOMER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_HOUSE -> {
                return HouseHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customers_profile_info_item,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_SALE_CUSTOMER -> {
                return SaleCustomerHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customers_profile_info_item,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return RentCustomerHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customers_profile_info_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var customerProfileInfo = dataList[position]
        var itemViewType = getItemViewType(position)
        when (itemViewType) {
            VIEW_TYPE_HOUSE -> {
                var houseInfo = customerProfileInfo?.houseInfo
                var customerHouseRvAdapter = CustomerHouseRvAdapter(mContext)
                (holder as HouseHolder).apply {
                    tvCustomerProfileInfoTitle.text =
                        "${mContext.resources.getString(R.string.house_num)}(${houseInfo?.size})"
                    rvCustomerProfileInfo.apply {
                        isNestedScrollingEnabled = false
                        layoutManager = LinearLayoutManager(mContext)
                        adapter = customerHouseRvAdapter
                    }
                }
                customerHouseRvAdapter.setData(houseInfo!!)
            }
            VIEW_TYPE_SALE_CUSTOMER -> {
                var saleCustomer = customerProfileInfo?.saleCustomer
                var saleCustomerRvAdapter = SaleCustomerRvAdapter(mContext)
                (holder as SaleCustomerHolder).apply {
                    tvCustomerProfileInfoTitle.text =
                        "${mContext.resources.getString(R.string.house_num)}(${saleCustomer?.size})"
                    rvCustomerProfileInfo.apply {
                        isNestedScrollingEnabled = false
                        layoutManager = LinearLayoutManager(mContext)
                        adapter = saleCustomerRvAdapter
                    }
                }
                saleCustomerRvAdapter.setData(saleCustomer!!)
            }
            else -> {
                var rentCustomer = customerProfileInfo?.rentCustomer
                var rentCustomerRvAdapter = SaleCustomerRvAdapter(mContext)
                (holder as RentCustomerHolder).apply {
                    tvCustomerProfileInfoTitle.text =
                        "${mContext.resources.getString(R.string.house_num)}(${rentCustomer?.size})"
                    rvCustomerProfileInfo.apply {
                        isNestedScrollingEnabled = false
                        layoutManager = LinearLayoutManager(mContext)
                        adapter = rentCustomerRvAdapter
                    }
                }
                rentCustomerRvAdapter.setData(rentCustomer!!)
            }
        }
    }

    inner class HouseHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCustomerProfileInfoTitle = view.tvCustomerProfileInfoTitle
        var rvCustomerProfileInfo = view.rvCustomerProfileInfo
    }

    inner class SaleCustomerHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCustomerProfileInfoTitle = view.tvCustomerProfileInfoTitle
        var rvCustomerProfileInfo = view.rvCustomerProfileInfo
    }

    inner class RentCustomerHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCustomerProfileInfoTitle = view.tvCustomerProfileInfoTitle
        var rvCustomerProfileInfo = view.rvCustomerProfileInfo
    }

}
