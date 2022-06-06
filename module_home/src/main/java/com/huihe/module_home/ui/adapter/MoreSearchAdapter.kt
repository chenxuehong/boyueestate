package com.huihe.module_home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

import com.huihe.boyueentities.protocol.home.MoreSearchBean
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

class MoreSearchAdapter(mContext: Context, var mColumnNum: Int) :
    BaseRecyclerViewAdapter<MoreSearchBean, RecyclerView.ViewHolder>(mContext) {

    private val VIEW_TYPE_TITLE: Int = 1000
    private val VIEW_TYPE_CONTENT: Int = 1001
    private var checkedList = mutableListOf<MoreSearchBean>()

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].isTitle) {
            VIEW_TYPE_TITLE
        } else {
            VIEW_TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var mInflater = LayoutInflater.from(mContext)
        when (viewType) {
            VIEW_TYPE_TITLE -> {
                return TitleViewHolder(
                    mInflater.inflate(
                        R.layout.layout_more_title_item,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ContentViewHolder(
                    mInflater.inflate(
                        R.layout.layout_more_content_item,
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var item = dataList[position]
        if (item.isTitle) {
            var titleViewHolder = holder as TitleViewHolder
            titleViewHolder.tvTitle.text = item.content
        } else {
            var contentViewHolder = holder as ContentViewHolder
            contentViewHolder.tvContent.text = item.content
            var item = getItemFromCheckListByContent(item.content)
            if (item != null) {
                contentViewHolder.tvContent.setBackgroundResource(R.drawable.btn_common_pressed)
            } else {
                contentViewHolder.tvContent.setBackgroundResource(R.drawable.btn_common_unpressed)
            }
        }
    }

    private fun getItemFromCheckListByContent(content: String): MoreSearchBean? {
        checkedList.forEach { item ->
            if (item.content == content) {
                return item
            }
        }
        return null
    }

    fun setItemChecked(item: MoreSearchBean) {
        var findItem = getItemFromCheckListByContent(item.content)
        if (findItem != null) {
            removeItemByContent(item.content)
        } else {
            checkedList.add(item)
            var isMutilSelectable = isMutiSelectable(item)
            if (!isMutilSelectable) {
                removeOtherItemFromCheckList(item)
            }
        }

        notifyDataSetChanged()
    }

    private fun removeOtherItemFromCheckList(item: MoreSearchBean) {
        var iterator = checkedList.iterator()
        while (iterator.hasNext()) {
            var searchBean = iterator.next()
            if (searchBean.title == item.title && searchBean.content != item.content) {
                iterator.remove()
                continue
            }
        }
    }

    private fun removeItemByContent(content: String) {
        var iterator = checkedList.iterator()
        while (iterator.hasNext()) {
            var searchBean = iterator.next()
            if (searchBean.content == content) {
                iterator.remove()
                break
            }
        }
    }

    private fun isMutiSelectable(item: MoreSearchBean): Boolean {
        return "面积" == item.title || "房型" == item.title
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        var layoutManager = recyclerView.layoutManager as GridLayoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (dataList[position].isTitle) {
                    mColumnNum
                } else {
                    1
                }
            }
        }

    }

    fun resetData() {
        checkedList.clear()
        notifyDataSetChanged()
    }

    fun getCheckedData(): MutableList<MoreSearchBean> {
        return checkedList
    }

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle = itemView as TextView
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvContent = itemView as TextView
    }
}
