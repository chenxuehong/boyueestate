package com.huihe.customercenter.ui.widget

import android.view.View

interface ISearchView {

    fun getAllViews(listener: ISearchResultListener): List<View>
    fun getView(type: Int): View
}
