package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.AreaBean
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
        // 区域
        popupViews!!.add(getView(CustomersModule.SearchType.AreaType))
        // 楼层
        popupViews!!.add(getView(CustomersModule.SearchType.FloorsType))
        // 价格
        popupViews!!.add(getView(CustomersModule.SearchType.PriceType))
        // 更多
        popupViews!!.add(getView(CustomersModule.SearchType.MoreType))
        var moduleType = mListener?.getModuleType()
        if (MODULE_HOUSE_FRAGMENT == moduleType){
            // 排序
            popupViews!!.add(getView(CustomersModule.SearchType.SortType))
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

    companion object{
        val MODULE_HOUSE_FRAGMENT: Int=1000
        val MODULE_MAP_HOUSE_FRAGMENT: Int=1001
    }
    fun detach() {
        mContext = null
        if (popupViews != null) {
            popupViews!!.clear()
            popupViews = null
        }
    }
}
