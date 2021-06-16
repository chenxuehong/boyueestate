package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import com.huihe.module_home.R
import com.huihe.module_home.injection.module.CustomersModule
import java.util.*

class SearchResultViewController : ISearchView {

    private var mListener: ISearchResultListener? = null
    private var mContext: Context? = null
    private var mIsShowing: Boolean = false
    private var popupViews: MutableList<View>? = null

    constructor(context: Context, isShowing: Boolean) {
        mContext = context
        mIsShowing = isShowing
        popupViews = ArrayList()
    }

    override fun getAllViews(listener: ISearchResultListener): MutableList<View> {
        mListener = listener
        popupViews?.clear()
        var modules = mListener?.getSearchModules()
        modules?.apply {
            forEach {item->
                when(item){
                    CustomersModule.SearchType.AreaType->{
                        // 区域
                        popupViews!!.add(getView(CustomersModule.SearchType.AreaType))
                    }
                    CustomersModule.SearchType.FloorsType->{
                        // 楼层
                        popupViews!!.add(getView(CustomersModule.SearchType.FloorsType))
                    }
                    CustomersModule.SearchType.PriceType->{
                        // 价格
                        popupViews!!.add(getView(CustomersModule.SearchType.PriceType))
                    }
                    CustomersModule.SearchType.MoreType->{
                        // 更多
                        popupViews!!.add(getView(CustomersModule.SearchType.MoreType))
                    }
                    CustomersModule.SearchType.SortType->{
                        // 排序
                        popupViews!!.add(getView(CustomersModule.SearchType.SortType))
                    }
                }
            }
        }
        return popupViews!!
    }

    override fun getView(searchType:Int): View {
        var inflate: View
        when (searchType) {
            CustomersModule.SearchType.AreaType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_area, null)
                AreaResultView().initAreaView(mContext!!, mListener,inflate)
            }
            CustomersModule.SearchType.FloorsType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_floors, null)
                FloorResultView().initFloorsView(mContext!!, mListener,inflate)
            }
            CustomersModule.SearchType.PriceType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_price, null)
                PriceResultView().initPriceView(mContext!!, mListener,inflate)
            }
            CustomersModule.SearchType.MoreType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_more, null)
                MoreResultView().initMoreView(mContext!!, mListener,inflate)
            }
            else// 排序
            -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_sort, null)
                SortResultView().initSortView(mContext!!, mListener,inflate)
            }
        }
        return inflate
    }

    fun detach() {
        mContext = null
        if (popupViews != null) {
            popupViews!!.clear()
            popupViews = null
        }
    }
}
