package com.huihe.module_home.ui.widget

import com.huihe.module_home.data.protocol.ISearchResult


interface ISearchResultListener {
    fun onSearchResult(
        iSearchResult: ISearchResult?,
        floorsType: SearchType
    )
}
