package com.huihe.module_home.ui.widget

import com.huihe.module_home.data.protocol.ISearchResult
import com.huihe.module_home.ui.adapter.RvAreaDistrictAdapter


interface ISearchResultListener {
    fun onSearchResult(
        iSearchResult: ISearchResult?,
        showTip: String,
        type: Int
    )
    fun startLoad(adapter: RvAreaDistrictAdapter?)
    fun getModuleType():Int
}
