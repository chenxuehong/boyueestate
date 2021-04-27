package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.FloorResult
import com.huihe.module_home.ui.adpter.FloorsRvAdapter
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.hideKeyboard
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

private val floors =
    mutableListOf<String>("5以下", "5-10", "10-15", "15-20", "20-25", "25-30", "30以上")
private var maxFloor = "100"
private var minFloor = "1"

fun View.initFloorsView(
    mContext: Context,
    mListener: ISearchResultListener?
) {
    val etMinValue = findViewById<EditText>(R.id.etFloorsMinValue)
    val etMaxValue = findViewById<EditText>(R.id.etFloorsMaxValue)
    val rvFloorsSearch = findViewById<RecyclerView>(R.id.rvFloorsSearch)
    rvFloorsSearch.layoutManager = LinearLayoutManager(mContext)
    var floorsRvAdapter = FloorsRvAdapter(mContext)
    floorsRvAdapter.setOnItemClickListener( object :
        BaseRecyclerViewAdapter.OnItemClickListener<String> {
        override fun onItemClick(view:View,floor: String, position: Int) {
            mContext.hideKeyboard(view)
            var floorRanges = getFloorRanges(floor, position, floorsRvAdapter.dataList.size)
            if (floorRanges?.size == 2) {
                mListener?.onSearchResult(
                    FloorResult(floorRanges[0], floorRanges[1]),
                    SearchType.FloorsType
                )
            }
        }
    })
    rvFloorsSearch.adapter = floorsRvAdapter
    floorsRvAdapter.setData(floors)
    val tvSure = findViewById<TextView>(R.id.tvFloorsSure)
    tvSure.enable(etMinValue) { isBtnEnable(etMinValue,etMaxValue) }
    tvSure.enable(etMaxValue) { isBtnEnable(etMinValue,etMaxValue) }
    tvSure.setOnClickListener {
        mContext.hideKeyboard(it)
        var floorRanges = getRealFloorRanges(
            etMinValue.text.toString().trim(),
            etMaxValue.text.toString().trim()
        )
        if (floorRanges?.size == 2) {
            mListener?.onSearchResult(
                FloorResult(floorRanges[0], floorRanges[1]),
                SearchType.FloorsType
            )
        }
    }

}

/*
      判断按钮是否可用
   */
private fun View.isBtnEnable(etMinValue:EditText,etMaxValue:EditText): Boolean {
    return etMinValue.text.isNullOrEmpty().not() &&
            etMaxValue.text.isNullOrEmpty().not()
}

private fun getFloorRanges(floor: String, position: Int, length: Int): MutableList<String> {
    var startFloor = minFloor
    var endFloor = maxFloor
    when (position) {
        0 -> {
            startFloor = minFloor
            endFloor = "5"
        }
        length - 1 -> {
            startFloor = "30"
            endFloor = maxFloor
        }
        else -> {
            var split = floor.split("-")
            startFloor = split[0]
            endFloor = split[1]
        }
    }
    return getRealFloorRanges(startFloor, endFloor)
}

fun getRealFloorRanges(startFloor: String, endFloor: String): MutableList<String> {
    var realFloorRanges = mutableListOf<String>()
    realFloorRanges.add(startFloor)
    realFloorRanges.add(endFloor)
    return realFloorRanges
}
