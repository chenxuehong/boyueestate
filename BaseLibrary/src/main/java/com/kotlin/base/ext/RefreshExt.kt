package com.kotlin.base.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kennyc.view.MultiStateView
import com.kotlin.base.common.OnRefreshListener
import com.kotlin.base.utils.FragmentUtils
import com.scwang.smart.refresh.layout.SmartRefreshLayout

fun MultiStateView.isLoading():Boolean{
    return viewState == MultiStateView.VIEW_STATE_LOADING;
}

fun SmartRefreshLayout.triggerAutoRefresh(isLoading: () -> Boolean){
    if (!isLoading()){
        autoRefresh()
    }
}

fun List<Fragment>?.doRefreshFragments(){
    this?.forEachIndexed { index, fragment ->
        if (fragment is OnRefreshListener){
            (fragment as OnRefreshListener).onRefresh()
        }
    }
}

fun List<Fragment>?.doRefreshFragment(index:Int){
    this?.apply {
        if (index < this.size) {
            if (this[index] is OnRefreshListener){
                (this[index] as OnRefreshListener).onRefresh()
            }
        }
    }
}