package com.huihe.customercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R
import com.huihe.customercenter.data.protocol.CustomerDetailBean
import com.huihe.customercenter.injection.module.CustomersModule
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_customer_detail_maininfo_item.view.*
import kotlinx.android.synthetic.main.layout_customer_detail_rewarks_item.view.*

class CustomerDetailRvAdapter(mContext: Context, val listener: OnCustomerListener) :
    BaseRecyclerViewAdapter<CustomerDetailBean, CustomerDetailRvAdapter.ViewHolder>(mContext) {

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
    var isFistRefresh = true
    var basicInfoAdapter: BasicInfoRvAdapter
    var helperInfoRvAdapter: HelperInfoRvAdapter
    var createUeerInfoAdapter: BasicInfoRvAdapter

    init {
        isFistRefresh = true
        helperInfoRvAdapter = HelperInfoRvAdapter(mContext)
        basicInfoAdapter = BasicInfoRvAdapter(mContext)
        createUeerInfoAdapter = BasicInfoRvAdapter(mContext)
    }

    override fun getItemViewType(position: Int): Int {

        when (position) {
            0 -> {
                return CustomersModule.CustomerDetailType.MAIN_INFO
            }
            1 -> {
                return CustomersModule.CustomerDetailType.HELPER_INFO
            }
            2 -> {
                return CustomersModule.CustomerDetailType.BASIC_INFO
            }
            3 -> {
                return CustomersModule.CustomerDetailType.REWARKS
            }
        }
        return CustomersModule.CustomerDetailType.CREATE_USER_INFO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType) {
            CustomersModule.CustomerDetailType.MAIN_INFO -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customer_detail_maininfo_item,
                        parent,
                        false
                    )
                )
            }
            CustomersModule.CustomerDetailType.HELPER_INFO -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.layout_customer_detail_helperinfo_item,
                        parent,
                        false
                    )
                )
            }
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
            CustomersModule.CustomerDetailType.MAIN_INFO -> {
                bindMainInfo(holder, customerDetailBean.mainInfo)
            }
            CustomersModule.CustomerDetailType.HELPER_INFO -> {
                bindHelperInfo(holder, customerDetailBean.helperInfo)
            }
            CustomersModule.CustomerDetailType.BASIC_INFO -> {
                bindBasicInfo(holder, customerDetailBean.basicInfo)
            }
            CustomersModule.CustomerDetailType.REWARKS -> {
                bindRewarksInfo(holder, customerDetailBean.rewarks)
            }
            else -> {
                bindCreateUserInfo(holder, customerDetailBean.createUserInfo)
            }
        }
        isFistRefresh = false
    }

    private fun bindMainInfo(
        holder: ViewHolder,
        item: CustomerDetailBean.CustomerMainInfo?
    ) {
        holder.itemView.tvDetailCustomerName.text = item?.customerName ?: ""
        val tags = mutableListOf<String>()
        var customerType = item?.customerType ?: "1"
        if (1 == customerType && 1 == item?.isTop) {
            tags.add("置顶")
        } else if (1 != customerType && 1 == item?.isCornucopia) {
            tags.add("淘")
        }

        var takeLookCount = item?.takeLookCount ?: 0
        if (takeLookCount > 0) {
            tags.add("带看${takeLookCount}")
        }
        colors = colors.toMutableList()
        (colors as MutableList<IntArray>).clear()
        if (1 == customerType && 1 == item?.isTop) {
            (colors as MutableList<IntArray>).add(intArr1)
        }
        if (1 != customerType && 1 == item?.isCornucopia) {
            (colors as MutableList<IntArray>).add(intArr2)
        }
        if (takeLookCount > 0) {
            (colors as MutableList<IntArray>).add(intArr3)
        }

        holder.itemView.detailCustomerTags.setTags(tags, colors)
        holder.itemView.tvDemandBeat.text = "需求区域: ${item?.demandBeat ?: ""}"
        holder.itemView.tvDemandBudget.text = "预算: ${item?.demandBudget ?: ""}"
    }

    private fun bindHelperInfo(
        holder: ViewHolder,
        helperInfo: CustomerDetailBean.HelperInfo?
    ) {
        (holder.itemView as RecyclerView).apply {
            isNestedScrollingEnabled = false
            vertical(4, resources.getDimensionPixelOffset(R.dimen.dp_16))
            adapter = helperInfoRvAdapter
        }
        helperInfoRvAdapter.setOnItemClickListener(object :
            OnItemClickListener<CustomerDetailBean.ItemHelperInfo> {
            override fun onItemClick(
                view: View,
                item: CustomerDetailBean.ItemHelperInfo,
                position: Int
            ) {
                when (position) {
                    0 -> {
                        listener?.onPhoneCall(view,item?.helperInfo?.mobile)
                    }
                    1 -> {
                        listener?.onFollow(view,item?.helperInfo?.customerCode)
                    }
                    2 -> {
                        listener?.onTakeLook(view,item?.helperInfo?.customerCode)
                    }
                    3 -> {
                        listener?.onLog(view,item?.helperInfo?.customerCode)
                    }
                }
            }
        })
        helperInfoRvAdapter.setData(
            mutableListOf(
                CustomerDetailBean.ItemHelperInfo(
                    helperInfo,
                    "电话",
                    R.drawable.owner_tel
                ),
                CustomerDetailBean.ItemHelperInfo(
                    helperInfo,
                    "跟进",
                    R.drawable.owner_follow
                ),
                CustomerDetailBean.ItemHelperInfo(
                    helperInfo,
                    "带看",
                    R.drawable.owner_takelook
                ),
                CustomerDetailBean.ItemHelperInfo(
                    helperInfo,
                    "日志",
                    R.drawable.owner_log
                )
            )
        )
    }

    private fun bindBasicInfo(
        holder: ViewHolder,
        basicInfo: CustomerDetailBean.CustomerBasicInfo?
    ) {
        (holder.itemView as RecyclerView).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(mContext)
            adapter = basicInfoAdapter
        }
        basicInfoAdapter.setData(
            mutableListOf(
                CustomerDetailBean.ItemBasicInfo("基本信息", "", true),
                CustomerDetailBean.ItemBasicInfo("客户姓名", basicInfo?.customerName, false),
                CustomerDetailBean.ItemBasicInfo("客户来源", basicInfo?.source, false),
                CustomerDetailBean.ItemBasicInfo("需求楼层", basicInfo?.demandFloor, false),
                CustomerDetailBean.ItemBasicInfo("需求户型", basicInfo?.demandHouseType, false),
                CustomerDetailBean.ItemBasicInfo("需求面积", basicInfo?.demandArea, false),
                CustomerDetailBean.ItemBasicInfo("需求区域", basicInfo?.demandBeat, false),
                CustomerDetailBean.ItemBasicInfo("需求置换", basicInfo?.isSubstitution, false),
                CustomerDetailBean.ItemBasicInfo("需求预算", basicInfo?.demandBudget, false),
                CustomerDetailBean.ItemBasicInfo("看房时间", basicInfo?.viewHouseDate, false),
                CustomerDetailBean.ItemBasicInfo("是否首套购房", basicInfo?.isFirstHouse, false),
                CustomerDetailBean.ItemBasicInfo("预判成交日期", basicInfo?.successDate, false),
                CustomerDetailBean.ItemBasicInfo("个性需求", basicInfo?.isStyle, false)
            )
        )
    }

    private fun bindRewarksInfo(holder: ViewHolder, rewarks: String?) {
        holder.itemView.tvCustomerRewarks.text = rewarks ?: ""
    }

    private fun bindCreateUserInfo(
        holder: ViewHolder,
        createUserInfo: CustomerDetailBean.CreateUserInfo?
    ) {
        (holder.itemView as RecyclerView).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(mContext)
            adapter = createUeerInfoAdapter
        }
        createUeerInfoAdapter.setData(
            mutableListOf(
                CustomerDetailBean.ItemBasicInfo("相关人", "", true),
                CustomerDetailBean.ItemBasicInfo(
                    "录入人",
                    "${createUserInfo?.createUserName ?: ""} ${createUserInfo?.createDate ?: ""}",
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

    interface OnCustomerListener {
        fun onPhoneCall(view: View,mobile: MutableList<String>?)
        fun onFollow(view: View,customerCode: String?)
        fun onTakeLook(view: View,customerCode: String?)
        fun onLog(view: View,customerCode: String?)
    }
}
