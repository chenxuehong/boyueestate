package com.huihe.module_home.ui.widget

import com.huihe.module_home.data.protocol.ISearchResult
import com.huihe.module_home.ui.adapter.RvAreaDistrictAdapter
import com.kennyc.view.MultiStateView


interface ISearchResultListener {
    fun onSearchResult(
        iSearchResult: ISearchResult?,
        showTip: String,
        type: Int
    )
    fun startLoad(
        adapter: RvAreaDistrictAdapter?,
        mMultiStateView: MultiStateView
    )
    fun getSearchModules():MutableList<Int>
}
