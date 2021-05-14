package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.AreaBeanConvertModel
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_area_item.view.*

class RvAreaDistrictAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<AreaBeanConvertModel, RvAreaDistrictAdapter.ViewHolder>(mContext) {

    private  var mCheckedItem :AreaBeanConvertModel?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(mContext).inflate(R.layout.layout_area_item, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = dataList[position]
        holder.itemView.tvAreaName.text = item.districtName
        holder.itemView.tvAreaName.setTextColor(
            if (hasChecked(item)){
                mContext.resources.getColor(R.color.main_color)
            }else{
                mContext.resources.getColor(R.color.color_333333)
            }
        )
        holder.itemView.areaCheckbox.setVisible(false)
    }

    private fun hasChecked(item: AreaBeanConvertModel): Boolean {
        return item?.districtId?.equals(mCheckedItem?.districtId)!!
    }

    override fun changeStatus(
        t1: View,
        t: AreaBeanConvertModel,
        position: Int
    ) {
        super.changeStatus(t1, t, position)
        mCheckedItem = t
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}
