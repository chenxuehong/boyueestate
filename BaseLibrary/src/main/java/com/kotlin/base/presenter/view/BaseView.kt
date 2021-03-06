package com.kotlin.base.presenter.view

/*
    MVP中视图回调 基类
 */
interface BaseView {
    fun showLoading()
    fun showLoading(tip:String)
    fun hideLoading()
    fun onError(text:String)
    fun onDataIsNull(){}//默认实现
}
