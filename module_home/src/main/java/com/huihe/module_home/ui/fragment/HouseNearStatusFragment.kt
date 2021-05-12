package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.mapapi.search.poi.*
import com.huihe.module_home.R
import com.huihe.module_home.ext.MyOnGetPoiSearchResultListener
import com.huihe.module_home.ui.adpter.HouseNearRvAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_house_near_status.*

class HouseNearStatusFragment : BaseFragment(), MyOnGetPoiSearchResultListener {
    var houseNearRvAdapter:HouseNearRvAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_house_near_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
         houseNearRvAdapter = HouseNearRvAdapter(context!!)
        houseNearStatusList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = houseNearRvAdapter
        }
    }

    override fun onGetPoiIndoorResult(p0: PoiIndoorResult?) {

    }

    override fun onGetPoiResult(p0: PoiResult?) {
        var allPoi = p0?.allPoi
        houseNearRvAdapter?.setData(allPoi!!)
    }

    override fun onGetPoiDetailResult(p0: PoiDetailResult?) {

    }

    override fun onGetPoiDetailResult(p0: PoiDetailSearchResult?) {

    }
}