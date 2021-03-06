package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.CustomerDetailBean
import com.huihe.customercenter.injection.module.CustomersModule
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_customer_detail_rewarks_item.view.*

class CustomerBasicInfoRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<CustomerDetailBean.BasicInfo,CustomerBasicInfoRvAdapter.ViewHolder>(
        mContext
    ) {

    var basicInfoAdapter: BasicInfoRvAdapter
    var createUeerInfoAdapter: BasicInfoRvAdapter
    init {
        basicInfoAdapter = BasicInfoRvAdapter(mContext)
        createUeerInfoAdapter = BasicInfoRvAdapter(mContext)
    }
    override fun getItemViewType(position: Int): Int {

        when (position) {
            0 -> {
                return CustomersModule.CustomerDetailType.BASIC_INFO
            }
            1 -> {
                return CustomersModule.CustomerDetailType.REWARKS
            }
        }
        return CustomersModule.CustomerDetailType.CREATE_USER_INFO
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        when (viewType) {
            CustomersModule.CustomerDetailType.BASIC_INFO -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customer_detail_basicinfo_item,
                        parent,
                        false
                    )
                )
            }
            CustomersModule.CustomerDetailType.REWARKS -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customer_detail_rewarks_item,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customer_detail_basicinfo_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        var customerDetailBean = dataList[position]
        when (getItemViewType(position)) {
            CustomersModule.CustomerDetailType.BASIC_INFO -> {
                bindBasicInfo(holder, customerDetailBean.customerBasicInfo)
            }
            CustomersModule.CustomerDetailType.REWARKS -> {
                bindRewarksInfo(holder, customerDetailBean.rewarks)
            }
            else -> {
                bindCreateUserInfo(holder, customerDetailBean.createUserInfo)
            }
        }
    }

    private fun bindBasicInfo(
        holder: ViewHolder,
        basicInfo: CustomerDetailBean.BasicInfo.CustomerBasicInfo?
    ) {
        (holder.itemView as RecyclerView).apply {
            isNestedScrollingEnabled = false
            vertical()
            adapter = basicInfoAdapter
        }
        basicInfoAdapter.setData(
            mutableListOf(
                CustomerDetailBean.ItemBasicInfo("????????????", "", true),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.customerName, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.source, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.demandFloor, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.demandHouseType, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.demandArea, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.demandBeat, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.isSubstitution, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.demandBudget, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.viewHouseDate, false),
                CustomerDetailBean.ItemBasicInfo("??????????????????", basicInfo?.isFirstHouse, false),
                CustomerDetailBean.ItemBasicInfo("??????????????????", basicInfo?.successDate, false),
                CustomerDetailBean.ItemBasicInfo("????????????", basicInfo?.isStyle, false)
            )
        )
    }

    private fun bindRewarksInfo(holder: ViewHolder, rewarks: String?) {
        holder.itemView.tvCustomerRewarks.text = rewarks ?: ""
    }

    private fun bindCreateUserInfo(
        holder: ViewHolder,
        createUserInfo: CustomerDetailBean.BasicInfo.CreateUserInfo?
    ) {
        (holder.itemView as RecyclerView).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(mContext)
            adapter = createUeerInfoAdapter
        }
        createUeerInfoAdapter.setData(
            mutableListOf(
                CustomerDetailBean.ItemBasicInfo("?????????", "", true),
                CustomerDetailBean.ItemBasicInfo(
                    "?????????",
                    "${createUserInfo?.createUserName ?: ""} ${createUserInfo?.createDate ?: ""}",
                    false
                ),
                CustomerDetailBean.ItemBasicInfo(
                    "?????????",
                    "${createUserInfo?.updateUserName ?: ""} ${createUserInfo?.updateDate ?: ""}",
                    false
                ),
                CustomerDetailBean.ItemBasicInfo(
                    "",
                    "",
                    false
                )
            )
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}