package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.Customer
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

class SecondHandHouseAdapter (context: Context) : BaseRecyclerViewAdapter<Customer, SecondHandHouseAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(
                R.layout.layout_customers_item,
                parent,
                false)
        return ViewHolder(view)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
