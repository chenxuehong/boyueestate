package com.huihe.customercenter.ui.widget

import android.content.Context
import android.view.View
import com.huihe.customercenter.R
import com.huihe.customercenter.injection.module.CustomersModule
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
        // 录入人
        popupViews!!.add(getView(CustomersModule.SearchType.CreateUserType))
        // 状态
        popupViews!!.add(getView(CustomersModule.SearchType.StatusType))
        // 更多
        popupViews!!.add(getView(CustomersModule.SearchType.MoreType))
        // 排序
        popupViews!!.add(getView(CustomersModule.SearchType.SortType))
        return popupViews!!
    }

    override fun getView(searchType:Int): View {
        var inflate: View
        when (searchType) {
            CustomersModule.SearchType.CreateUserType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_create_user, null)
                CreateUserView().initCreateUserView(mContext!!, mListener,inflate)
            }
            CustomersModule.SearchType.StatusType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_status, null)
                StatusView().initStatusView(mContext!!, mListener,inflate)
            }
            CustomersModule.SearchType.MoreType -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_more, null)
                MoreView().initMoreView(mContext!!, mListener,inflate)
            }
            else// 排序
            -> {
                inflate = View.inflate(mContext, R.layout.layout_search_by_sort, null)
                SortView().initSortView(mContext!!, mListener,inflate)
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
