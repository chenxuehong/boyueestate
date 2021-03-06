package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.FloorReq
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.adapter.FloorsRvAdapter
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.hideKeyboard
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter

class FloorResultView {
    // 底层 1-2 3-4 5-7 8层以上
    private val floors =
        mutableListOf<String>("不限", "底层", "1-2", "3-4", "5-7",  "8以上")
    private var title = "楼层"
    private var maxFloor = "100"
    private var minFloor = "1"

    fun initFloorsView(
        mContext: Context,
        mListener: ISearchResultListener?,
        view: View
    ) {
        val etMinValue = view.findViewById<EditText>(R.id.etFloorsMinValue)
        val etMaxValue = view.findViewById<EditText>(R.id.etFloorsMaxValue)
        val rvFloorsSearch = view.findViewById<RecyclerView>(R.id.rvFloorsSearch)
        rvFloorsSearch.layoutManager = LinearLayoutManager(mContext)
        var floorsRvAdapter = FloorsRvAdapter(mContext)
        floorsRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View, floor: String, position: Int) {
                mContext.hideKeyboard(view)
                var floorRanges = getFloorRanges(floor, position, floorsRvAdapter.dataList.size)
                if (floorRanges?.size == 2) {
                    mListener?.onSearchResult(
                        FloorReq(floorRanges[0], floorRanges[1]),
                        floor,
                        CustomersModule.SearchType.FloorsType
                    )
                } else {
                    mListener?.onSearchResult(
                        null,
                        title,
                        CustomersModule.SearchType.FloorsType
                    )
                }
            }
        })
        rvFloorsSearch.adapter = floorsRvAdapter
        floorsRvAdapter.setData(floors)
        val tvSure = view.findViewById<TextView>(R.id.tvFloorsSure)
        tvSure.enable(etMinValue) { view.isBtnEnable(etMinValue, etMaxValue) }
        tvSure.enable(etMaxValue) { view.isBtnEnable(etMinValue, etMaxValue) }
        tvSure.setOnClickListener {
            mContext.hideKeyboard(it)
            var floorRanges = getRealFloorRanges(
                etMinValue.text.toString().trim(),
                etMaxValue.text.toString().trim()
            )
            if (floorRanges?.size == 2) {
                mListener?.onSearchResult(
                    FloorReq(floorRanges[0], floorRanges[1]),
                    String.format(
                        mContext.resources.getString(R.string.minfloor_maxfloor),
                        etMinValue.text.toString().trim(),
                        etMaxValue.text.toString().trim()
                    ),
                    CustomersModule.SearchType.FloorsType
                )
            }
        }

    }

    /*
          判断按钮是否可用
       */
    private fun View.isBtnEnable(etMinValue: EditText, etMaxValue: EditText): Boolean {
        return etMinValue.text.isNullOrEmpty().not() &&
                etMaxValue.text.isNullOrEmpty().not()
    }

    //  不限 底层 1-2 3-4 5-7 8层以上
    private fun getFloorRanges(floor: String, position: Int, length: Int): MutableList<String>? {
        var startFloor = minFloor
        var endFloor = maxFloor
        when (position) {
            0 -> {
                return null
            }
            1 -> {
                startFloor = minFloor
                endFloor = "1"
            }
            length - 1 -> {
                startFloor = "8"
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
}

