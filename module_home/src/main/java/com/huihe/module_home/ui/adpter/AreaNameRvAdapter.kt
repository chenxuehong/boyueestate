package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.module_home.data.protocol.AreaBeanConvertModel
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_area_item.view.*

class AreaNameRvAdapter(mContext: Context,val mListener:OnCheckListener) :
    BaseRecyclerViewAdapter<AreaBeanConvertModel.DistrictBean, AreaNameRvAdapter.ViewHolder>(
        mContext
    ) {


    private  var mCheckedItem:AreaBeanConvertModel.DistrictBean?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(mContext).inflate(R.layout.layout_area_item, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var item = dataList[position]
        holder.itemView.tvAreaName.text = item.name
        holder.itemView.tvAreaName.setTextColor(
            if (hasChecked(item)){
                mContext.resources.getColor(R.color.main_color)
            }else{
                mContext.resources.getColor(R.color.color_333333)
            }
        )
        holder.itemView.areaCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            mListener?.isChecked(buttonView,isChecked,item)
        }
        holder.itemView.areaCheckbox.isChecked = item.isChecked!!
    }

    private fun hasChecked(item: AreaBeanConvertModel.DistrictBean): Boolean {
        return item?.id?.equals(mCheckedItem?.id)!!
    }

    override fun changeStatus(t: AreaBeanConvertModel.DistrictBean) {
        super.changeStatus(t)
        mCheckedItem = t
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnCheckListener{
        fun isChecked(
            buttonView: View,
            isChecked: Boolean,
            item: AreaBeanConvertModel.DistrictBean
        )
    }
}