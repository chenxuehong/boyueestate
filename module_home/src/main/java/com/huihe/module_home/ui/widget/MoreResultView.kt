package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.FloorageReq
import com.huihe.module_home.data.protocol.MoreReq
import com.huihe.module_home.data.protocol.MoreSearchBean
import com.huihe.module_home.data.protocol.RoomNumReq
import com.huihe.module_home.ext.enable
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.adpter.MoreSearchAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.widgets.ItemSpaceDecoration
import kotlinx.android.synthetic.main.layout_search_by_more.view.*

class MoreResultView {

    /*状态 :全部  有效  他售  我售 暂缓    新上 ：今日新上 三日新上   一周新上  类型：出售  出租  租售
    通用：我的房源  钥匙  独家    我的维护盘  流通   委托房源   我的收藏
    面积：50以下  50-70  70-90  90-110  110-130  130-150  150以上（多选）
    房型    一室  二室  三室  四室  五室  五室以上（多选）*/
    var datas = mutableListOf<MoreSearchBean>(
        // hFlag: 全部null 有效0 我售 2 他售 3 暂缓 1
        MoreSearchBean("状态", true, null, ""),
        MoreSearchBean("全部", false, null, "状态"),
        MoreSearchBean("有效", false, 0, "状态"),
        MoreSearchBean("暂缓", false, 1, "状态"),
        MoreSearchBean("我售", false, 2, "状态"),
        MoreSearchBean("他售", false, 3, "状态"),

        // days: 今日新上1 三日新上3 一周新上7
        MoreSearchBean("新上", true, null, ""),
        MoreSearchBean("今日新上", false, 1, "新上"),
        MoreSearchBean("三日新上", false, 3, "新上"),
        MoreSearchBean("一周新上", false, 7, "新上"),

        // transactionType: 租售2 出租1 出售0
        MoreSearchBean("类型", true, null, ""),
        MoreSearchBean("出售", false, 0, "类型"),
        MoreSearchBean("出租", false, 1, "类型"),
        MoreSearchBean("租售", false, 2, "类型"),

        // 我的房源: myHouse 1 钥匙:hasKey 1 独家:hasSole 1 我的维护盘: myMaintain
        // 流通:1 isCirculation 委托房源:1 entrustHouse 1 我的收藏:myCollect 1
        MoreSearchBean("通用", true, null, ""),
        MoreSearchBean("我的房源", false, 1, "通用"),
        MoreSearchBean("钥匙", false, 1, "通用"),
        MoreSearchBean("独家", false, 1, "通用"),
        MoreSearchBean("我的维护盘", false, 1, "通用"),
        MoreSearchBean("流通", false, 1, "通用"),
        MoreSearchBean("委托房源", false, 1, "通用"),
        MoreSearchBean("我的收藏", false, 1, "通用"),

        MoreSearchBean("面积", true, 3, ""),
        MoreSearchBean("50以下", false, 3, "面积"),
        MoreSearchBean("50-70", false, 3, "面积"),
        MoreSearchBean("70-90", false, 3, "面积"),
        MoreSearchBean("90-110", false, 3, "面积"),
        MoreSearchBean("110-130", false, 3, "面积"),
        MoreSearchBean("130-150", false, 3, "面积"),
        MoreSearchBean("150以上", false, 3, "面积"),

        // roomNumRanges [{roomNumMore: 2, roomNumLess: 2}]
        MoreSearchBean("房型", true, 4, ""),
        MoreSearchBean("一室", false, 4, "房型"),
        MoreSearchBean("二室", false, 4, "房型"),
        MoreSearchBean("三室", false, 4, "房型"),
        MoreSearchBean("四室", false, 4, "房型"),
        MoreSearchBean("五室", false, 4, "房型"),
        MoreSearchBean("五室以上", false, 4, "房型")
    )

    var mColumnNum: Int = 3
    var hFlag: Int? = null
    var days: Int? = null

    var myHouse: Int? = null
    var hasKey: Int? = null
    var hasSole: Int? = null
    var myMaintain: Int? = null
    var isCirculation: Int? = null
    var entrustHouse: Int? = null
    var myCollect: Int? = null

    var floorageRanges: MutableList<FloorageReq>? = mutableListOf()
    var roomNumRanges: MutableList<RoomNumReq>? = mutableListOf()

    fun initMoreView(
        mContext: Context,
        mListener: ISearchResultListener?,
        mview: View
    ) {
        var gridLayoutManager = GridLayoutManager(mContext, mColumnNum)
        mview.rvMore.layoutManager = gridLayoutManager
        mview.rvMore.addItemDecoration(
            ItemSpaceDecoration(
                mview.resources.getDimensionPixelOffset(R.dimen.dp_12)
            )
        )
        var moreSearchAdapter = MoreSearchAdapter(mContext, mColumnNum)
        mview.rvMore.adapter = moreSearchAdapter
        moreSearchAdapter.setData(datas)
        moreSearchAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<MoreSearchBean> {
            override fun onItemClick(view: View, item: MoreSearchBean, position: Int) {
                if (!item.isTitle) {
                    moreSearchAdapter.setItemChecked(item)
                }
                mview?.btnMoreReset?.enable(mContext,moreSearchAdapter)
            }
        })
        mview.btnMoreReset.apply {
            setOnClickListener {
                moreSearchAdapter.resetData()
                mview.btnMoreReset.enable(mContext,moreSearchAdapter)
            }
        }

        mview.btnMoreSure.setOnClickListener {
            resetData()
            var dataList = moreSearchAdapter.getCheckedData()
            dataList.forEach {
                if (!it.isTitle) {
                    var dataType = it.dataType
                    when (it.title) {
                        "状态" -> {
                            hFlag = dataType
                        }
                        "新上" -> {
                            days = dataType
                        }
                        "通用" -> {
                            handleCommon(it)
                        }
                        "面积" -> {
                            handleFloorageRanges(it.content)
                        }
                        else -> {
                            // 房型
                            handleRoomNumRanges(it.content)
                        }
                    }
                }
            }

            mListener?.onSearchResult(
                MoreReq(
                    hFlag,
                    days,
                    myHouse,
                    hasKey,
                    hasSole,
                    myMaintain,
                    isCirculation,
                    entrustHouse,
                    myCollect,
                    floorageRanges,
                    roomNumRanges
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

    fun resetData() {
        hFlag = null
        days = null
        myHouse = null
        hasKey = null
        hasSole = null
        myMaintain = null
        isCirculation = null
        entrustHouse = null
        myCollect = null
        floorageRanges = null
        roomNumRanges = null
    }

    private fun handleCommon(it: MoreSearchBean) {
        when (it.content) {
            "我的房源" -> {
                myHouse = it.dataType
            }
            "钥匙" -> {
                hasKey = it.dataType
            }
            "独家" -> {
                hasSole = it.dataType
            }
            "我的维护盘" -> {
                myMaintain = it.dataType
            }
            "流通" -> {
                isCirculation = it.dataType
            }
            "委托房源" -> {
                entrustHouse = it.dataType
            }
            else -> {
                // 我的收藏
                myCollect = it.dataType
            }
        }
    }

    fun handleFloorageRanges(content: String) {
        if (floorageRanges == null) {
            floorageRanges = mutableListOf()
        }
        when (content) {
            "50以下" -> {
                floorageRanges?.add(FloorageReq("0", "50"))
            }
            "50-70" -> {
                floorageRanges?.add(FloorageReq("50", "70"))
            }
            "70-90" -> {
                floorageRanges?.add(FloorageReq("70", "90"))
            }
            "90-110" -> {
                floorageRanges?.add(FloorageReq("90", "110"))
            }
            "110-130" -> {
                floorageRanges?.add(FloorageReq("110", "130"))
            }
            "130-150" -> {
                floorageRanges?.add(FloorageReq("130", "150"))
            }
            else -> {
                floorageRanges?.add(FloorageReq("150", "999"))
            }
        }
    }

    fun handleRoomNumRanges(content: String) {
        if (roomNumRanges == null) {
            roomNumRanges = mutableListOf()
        }
        when (content) {
            "一室" -> {
                roomNumRanges?.add(RoomNumReq("1", "1"))
            }
            "二室" -> {
                roomNumRanges?.add(RoomNumReq("2", "2"))
            }
            "三室" -> {
                roomNumRanges?.add(RoomNumReq("3", "3"))
            }
            "四室" -> {
                roomNumRanges?.add(RoomNumReq("4", "4"))
            }
            "五室" -> {
                roomNumRanges?.add(RoomNumReq("5", "5"))
            }
            "五室以上" -> {
                roomNumRanges?.add(RoomNumReq("5", "99"))
            }
        }
    }
}