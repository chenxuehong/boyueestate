package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import com.huihe.module_home.R
import java.util.*

class SearchResultViewController : ISearchView {

    private var mListener: ISearchResultListener? = null
    override fun getAllViews(listener: ISearchResultListener): MutableList<View> {
        mListener = listener
        // 区域
        popupViews!!.add(getView(SearchType.AreaType))
        // 楼层
        popupViews!!.add(getView(SearchType.FloorsType))
        // 价格
        popupViews!!.add(getView(SearchType.PriceType))
        // 更多
        popupViews!!.add(getView(SearchType.MoreType))
        // 排序
        popupViews!!.add(getView(SearchType.SortType))
        return popupViews!!
    }

    override fun getView(searchType: SearchType): View {
        var inflate: View? = null
        when (searchType) {
            SearchType.AreaType -> {
                inflate = View.inflate(mContext, R.layout.activity_album_select, null)
                inflate.initAreaView(mContext!!, mListener)
            }
            SearchType.FloorsType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_floors, null)
                inflate.initFloorsView(mContext!!, mListener)
            }
            SearchType.PriceType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_price, null)
                inflate.initPriceView(mContext!!, mListener)
            }
            SearchType.MoreType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_more, null)
                inflate.initMoreView(mContext!!, mListener)
            }
            else// 排序
            -> {
                inflate = View.inflate(mContext, R.layout.activity_album_select, null)
                inflate.initSortView(mContext!!, mListener)
            }
        }
        return inflate
    }

    companion object {

        private val instance = SearchResultViewController()
        private var mContext: Context? = null
        private var mIsShowing: Boolean = false
        private var popupViews: MutableList<View>? = null

        fun init(context: Context, isShowing: Boolean): SearchResultViewController {
            mContext = context
            mIsShowing = isShowing
            popupViews = ArrayList()
            return instance
        }

        fun detach() {
            mContext = null
            if (popupViews != null) {
                popupViews!!.clear()
                popupViews = null
            }
        }
    }
}
