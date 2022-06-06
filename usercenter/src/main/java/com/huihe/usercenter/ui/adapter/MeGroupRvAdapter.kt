package com.huihe.usercenter.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.MeItemBean
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.DensityUtils
import kotlinx.android.synthetic.main.layout_me_group_item.view.*

class MeGroupRvAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<MeItemBean, MeGroupRvAdapter.ViewHolder>(mContext),
    BaseRecyclerViewAdapter.OnItemClickListener<MeItemBean.ItemData> {
    var list:MutableList<MeItemBean>?=null
    var takeLookTaskList:MutableList<MeItemBean.ItemData>?=null
    //ItemClick事件
    var mItemChildClickListener: OnChildItemClickListener? = null

    init {
        takeLookTaskList = mutableListOf(
            MeItemBean.ItemData(mContext.resources.getString(R.string.to_start),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.take_look),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.in_summary),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.under_review),R.drawable.log_manager,0)
        )
        var integralList = mutableListOf(
            MeItemBean.ItemData(mContext.resources.getString(R.string.Store_data),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.Department_data),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.Employee_data),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.Integral_record),R.drawable.log_manager,0)
        )
        val corporateCultureList = mutableListOf(
//            MeItemBean.ItemData(mContext.resources.getString(R.string.BehavioralScore),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.corporateCulture),R.drawable.log_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.address_book),R.drawable.address_book,0)
        )
        val systemList = mutableListOf(
            MeItemBean.ItemData(mContext.resources.getString(R.string.area_manager),R.drawable.area_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.district_manager),R.drawable.district_manager,0),
            MeItemBean.ItemData(mContext.resources.getString(R.string.setting),R.drawable.settting,0)
        )
        list = mutableListOf(
            MeItemBean(mContext.resources.getString(R.string.takeLookTask), takeLookTaskList!!),
//            MeItemBean(mContext.resources.getString(R.string.ranking), integralList!!),
            MeItemBean(mContext.resources.getString(R.string.corporateCulture),corporateCultureList),
            MeItemBean(mContext.resources.getString(R.string.system),systemList)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initInflater(mContext, R.layout.layout_me_group_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var meItemBean = dataList[position]
        holder.itemView.tvTitle.text = meItemBean.title
        var meChildRvAdapter = MeChildRvAdapter(mContext)
        meChildRvAdapter.setOnItemClickListener(this)
        holder.itemView.rvList.apply {
            vertical(4, DensityUtils.dp2px(mContext,15f))
            isNestedScrollingEnabled = false
            adapter = meChildRvAdapter
        }
        meChildRvAdapter.setData(meItemBean.list)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun init(){
        list?.let { setData(it) }
    }

    override fun onItemClick(view: View, item: MeItemBean.ItemData, position: Int) {
        mItemChildClickListener?.onItemClick(view,item,position)
    }

    /*
    ItemClick事件声明
 */
    interface OnChildItemClickListener {
        fun onItemClick(view:View,item:MeItemBean.ItemData, position: Int)
    }

    fun setOnChildItemClickListener(listener: OnChildItemClickListener) {
        this.mItemChildClickListener = listener
    }

    /**
     * @desc 设置待处理数
     */
    fun setTakeLookCount(to_start:Int,take_look:Int,in_summary:Int,under_review:Int){
        takeLookTaskList?.set(0,MeItemBean.ItemData(mContext.resources.getString(R.string.to_start),R.drawable.log_manager,to_start))
        takeLookTaskList?.set(1, MeItemBean.ItemData(mContext.resources.getString(R.string.take_look),R.drawable.log_manager,take_look))
        takeLookTaskList?.set(2, MeItemBean.ItemData(mContext.resources.getString(R.string.in_summary),R.drawable.log_manager,in_summary))
        takeLookTaskList?.set(3, MeItemBean.ItemData(mContext.resources.getString(R.string.under_review),R.drawable.log_manager,under_review))
        notifyItemChanged(0)
    }

    fun onDestory(){
        takeLookTaskList?.clear()
        list?.clear()
        takeLookTaskList = null
        list = null
    }
}