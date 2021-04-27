package com.huihe.module_home.ui.widget

import android.view.View

interface ISearchView {

    fun getAllViews(listener: ISearchResultListener): List<View>
    fun getView(type: SearchType): View
}
