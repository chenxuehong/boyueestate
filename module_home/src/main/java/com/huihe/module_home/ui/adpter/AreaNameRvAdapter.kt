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

class AreaNameRvAdapter(
    mContext: Context,
    val mListener: OnCheckListener,
    val checkedItem: AreaBeanConvertModel?
) :
    BaseRecyclerViewAdapter<AreaBeanConvertModel.DistrictBean, AreaNameRvAdapter.ViewHolder>(
        mContext
    ) {


    private val checkList:MutableList<Int> = mutableListOf()
    init {
        checkList.clear()
    }
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
            if (hasAreaCheck(position)){
                mContext.resources.getColor(R.color.main_color)
            }else{
                mContext.resources.getColor(R.color.color_333333)
            }
        )
        holder.itemView.areaCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            mListener?.isChecked(buttonView,isChecked,item)
            if (isChecked){
                if (!checkList.contains(position)){
                    checkList.add(position)
                    holder.itemView.tvAreaName.setTextColor( mContext.resources.getColor(R.color.main_color))
                }
            }else{
                if (checkList.contains(position)){
                    checkList.remove(position)
                    holder.itemView.tvAreaName.setTextColor( mContext.resources.getColor(R.color.color_333333))
                }
            }
        }
        holder.itemView.areaCheckbox.isChecked = hasAreaCheck(position)
    }

    private fun hasAreaCheck(position: Int): Boolean {
        checkList?.forEach { item ->
            if (item == position){
                return true
            }
        }
        return false
    }

    override fun changeStatus(
        itemView: View,
        t: AreaBeanConvertModel.DistrictBean,
        position: Int
    ) {
        super.changeStatus(itemView, t, position)
        if (!checkList.contains(position)){
            checkList.add(position)
        }else{
            checkList.remove(position)
        }
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
