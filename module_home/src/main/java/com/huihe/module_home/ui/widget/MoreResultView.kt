package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.MoreSearchBean
import com.huihe.module_home.ui.adpter.MoreSearchAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.widgets.ItemSpaceDecoration
import kotlinx.android.synthetic.main.layout_search_by_more.view.*

/*状态 :全部  有效  他售  我售 暂缓    新上 ：今日新上 三日新上   一周新上  类型：出售  出租  租售
通用：我的房源  钥匙  独家    我的维护盘  流通   委托房源   我的收藏
面积：50以下  50-70  70-90  90-110  110-130  130-150  150以上（多选）
房型    一室  二室  三室  四室  五室  五室以上（多选）*/
var datas = mutableListOf<MoreSearchBean>(

    MoreSearchBean("状态", true, 0,""),
    MoreSearchBean("全部", false, 0,"状态"),
    MoreSearchBean("有效", false, 0,"状态"),
    MoreSearchBean("他售", false, 0,"状态"),
    MoreSearchBean("暂缓", false, 0,"状态"),

    MoreSearchBean("新上", true, 0,""),
    MoreSearchBean("今日新上", false, 0,"新上"),
    MoreSearchBean("三日新上", false, 0,"新上"),
    MoreSearchBean("一周新上", false, 0,"新上"),

    MoreSearchBean("类型", true, 0,""),
    MoreSearchBean("出售", false, 0,"类型"),
    MoreSearchBean("出租", false, 0,"类型"),
    MoreSearchBean("租售", false, 0,"类型"),
    MoreSearchBean("通用", false, 0,"类型"),
    MoreSearchBean("我的房源", false, 0,"类型"),
    MoreSearchBean("钥匙", false, 0,"类型"),
    MoreSearchBean("独家", false, 0,"类型"),
    MoreSearchBean("我的维护盘", false, 0,"类型"),
    MoreSearchBean("流通", false, 0,"类型"),
    MoreSearchBean("委托房源", false, 0,"类型"),
    MoreSearchBean("我的收藏", false, 0,"类型"),

    MoreSearchBean("面积", true, 0,""),
    MoreSearchBean("50以下", false, 0,"面积"),
    MoreSearchBean("50-70", false, 0,"面积"),
    MoreSearchBean("70-90", false, 0,"面积"),
    MoreSearchBean("90-110", false, 0,"面积"),
    MoreSearchBean("110-130", false, 0,"面积"),
    MoreSearchBean("130-150", false, 0,"面积"),
    MoreSearchBean("150以上", false, 0,"面积"),

    MoreSearchBean("房型", true, 0,""),
    MoreSearchBean("一室", false, 0,"房型"),
    MoreSearchBean("二室", false, 0,"房型"),
    MoreSearchBean("三室", false, 0,"房型"),
    MoreSearchBean("四室", false, 0,"房型"),
    MoreSearchBean("五室", false, 0,"房型"),
    MoreSearchBean("五室以上", false, 0,"房型")
)
var mColumnNum: Int = 3
fun View.initMoreView(
    mContext: Context,
    mListener: ISearchResultListener?
) {
    var gridLayoutManager = GridLayoutManager(mContext, mColumnNum)
    rvMore.layoutManager = gridLayoutManager
    rvMore.addItemDecoration(ItemSpaceDecoration(
        resources.getDimensionPixelOffset(R.dimen.dp_12)))
    var moreSearchAdapter = MoreSearchAdapter(mContext, mColumnNum)
    rvMore.adapter = moreSearchAdapter
    moreSearchAdapter.setData(datas)
    moreSearchAdapter.setOnItemClickListener(object :
        BaseRecyclerViewAdapter.OnItemClickListener<MoreSearchBean> {
        override fun onItemClick(view: View, item: MoreSearchBean, position: Int) {
            if (!item.isTitle) {
                moreSearchAdapter.setItemChecked(item)
            }
        }
    })
    btnMoreReset.setOnClickListener {
        moreSearchAdapter.resetData()
    }
    btnMoreSure.setOnClickListener {
//        mListener?.onSearchResult()
    }

}
