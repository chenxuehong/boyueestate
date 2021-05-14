package com.huihe.customercenter.ui.widget

import com.huihe.customercenter.ui.adapter.DeptUsersRvAdapter
import com.huihe.customercenter.ui.adapter.StatusRvAdapter


interface ISearchResultListener {
    fun onSearchResult(
        iSearchResult: ISearchResult?,
        showTip: String,
        type: Int
    )

    fun startLoadDeptUsers(adapter: DeptUsersRvAdapter)
    fun startLoadStatus(mSortRvAdapter: StatusRvAdapter)
}
