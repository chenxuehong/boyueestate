package com.huihe.customercenter.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.MoreReq
import com.huihe.boyueentities.protocol.customer.MoreSearchBean
import com.huihe.customercenter.ext.enableList
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.ui.adapter.MoreSearchAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.widgets.ItemSpaceDecoration
import kotlinx.android.synthetic.main.layout_search_by_more.view.*

class MoreView {
    var mColumnNum: Int = 3
    var datas = mutableListOf<MoreSearchBean>(

        MoreSearchBean("客户类型", true, "customer_type", -1, "客户类型"),
        MoreSearchBean("私客", false, "customer_type", 1, "客户类型"),
        MoreSearchBean("公客", false, "customer_type", 2, "客户类型"),

        MoreSearchBean("通用", true, "custom_type", -1, "通用"),
        MoreSearchBean("我的私客", false, "isOwn", 1, "通用"),
        MoreSearchBean("带看", false, "isTakeLook", 1, "通用"),
        MoreSearchBean("我的收藏", false, "isCollection", 1, "通用")
    )
    var userType: Int? = null
    var isOwn: Int? = null
    var isTakeLook: Int? = null
    var isCollection: Int? = null

    fun initMoreView(
        mContext: Context,
        mListener: ISearchResultListener?,
        view: View
    ) {
        view.apply {
            var gridLayoutManager = GridLayoutManager(mContext, mColumnNum)
            rvMore.layoutManager = gridLayoutManager
            rvMore.addItemDecoration(
                ItemSpaceDecoration(
                    resources.getDimensionPixelOffset(R.dimen.dp_12)
                )
            )
            var moreSearchAdapter = MoreSearchAdapter(mContext, mColumnNum)
            rvMore.adapter = moreSearchAdapter
            moreSearchAdapter.setData(datas)
            moreSearchAdapter.setOnItemClickListener(object :
                BaseRecyclerViewAdapter.OnItemClickListener<MoreSearchBean> {
                override fun onItemClick(view: View, item: MoreSearchBean, position: Int) {
                    if (!item.isTitle) {
                        moreSearchAdapter.setItemChecked(item)
                    }
                    btnMoreReset.enableList(mContext, moreSearchAdapter.getCheckedData())
                }
            })
            btnMoreReset.apply {
                setOnClickListener {
                    moreSearchAdapter.resetData()
                    btnMoreReset.enableList(mContext, moreSearchAdapter.getCheckedData())
                }
            }

            btnMoreSure.setOnClickListener {
                resetData()
                var dataList = moreSearchAdapter.getCheckedData()
                dataList?.forEach { item->
                    when(item.dataType){
                        "customer_type"->{
                            userType = item.dataKey
                        }
                        "isOwn"->{
                            isOwn=  item.dataKey
                        }
                        "isTakeLook"->{
                            isTakeLook=  item.dataKey
                        }
                        "isCollection"->{
                            isCollection=  item.dataKey
                        }
                    }
                }
                mListener?.onSearchResult(
                    MoreReq(
                        userType,
                        isOwn,
                        isTakeLook,
                        isCollection
                    ),
                    if (dataList.size > 0)
                        String.format(
                            mContext.resources.getString(R.string.more_result_numSelected),
                            dataList.size
                        ) else "更多",
                    CustomersModule.SearchType.MoreType
                )
            }
        }

    }

    private fun resetData() {
        userType = null
        isOwn = null
        isTakeLook = null
        isCollection = null
    }
}
