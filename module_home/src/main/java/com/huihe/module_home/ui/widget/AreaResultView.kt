package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.boyueentities.protocol.home.AreaReq
import com.huihe.module_home.ext.*
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.adapter.AreaNameRvAdapter
import com.huihe.module_home.ui.adapter.RvAreaDistrictAdapter
import com.huihe.module_home.ui.adapter.RvZoneAdapter
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.huihe.boyueentities.protocol.common.District
import kotlinx.android.synthetic.main.layout_search_by_area.view.*

class AreaResultView {
    private var mRvAreaDistrictAdapter: RvAreaDistrictAdapter? = null
    private var mRvZoneAdapter: RvZoneAdapter? = null
    private var mAreaNameRvAdapter: AreaNameRvAdapter? = null
    private var mListener: ISearchResultListener? = null
    private var checkedItem: District? = null
    fun initAreaView(
        mContext: Context,
        listener: ISearchResultListener?,
        view: View
    ) {
        mListener = listener
        view.initRvAreaDistrictName(mContext)
        view.btnAreaReset.setOnClickListener {
            view.initRvAreaDistrictName(mContext)
            view.initRvZone(mContext, mutableListOf())
            view.initAreaName(mContext, mutableListOf())
        }
        view.btnAreaSure.setOnClickListener {
            var villageIds = getVillageIds()
            mListener?.onSearchResult(
                AreaReq(villageIds!!),
                if (villageIds.size>0){"${mCheckedItem?.name}+${villageIds.size}"}else {"区域"},
                CustomersModule.SearchType.AreaType
            )
        }
        view.mMultiStateView.startLoading()
    }

    fun View.initRvAreaDistrictName(mContext: Context) {
        mRvAreaDistrictAdapter = RvAreaDistrictAdapter(mContext)
        rvAreaDistrictName.layoutManager = LinearLayoutManager(mContext)
        mRvAreaDistrictAdapter?.setOnItemClickListener(
            object : BaseRecyclerViewAdapter.OnItemClickListener<District> {
                override fun onItemClick(view: View, item: District, position: Int) {

                    checkedItem = item
                    initRvZone(mContext, item.zones)
                    initAreaName(mContext, mutableListOf())
                }
            }
        )
        rvAreaDistrictName.adapter = mRvAreaDistrictAdapter
        mListener?.startLoad(mRvAreaDistrictAdapter,mMultiStateView)
    }

    fun View.initRvZone(
        mContext: Context,
        zoneBeans: MutableList<District.ZoneBean>?
    ) {
        mRvZoneAdapter = RvZoneAdapter(mContext)
        rvAreaZoneName.layoutManager = LinearLayoutManager(mContext)
        mRvZoneAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<District.ZoneBean> {
            override fun onItemClick(view: View, item: District.ZoneBean, position: Int) {
                initAreaName(mContext, item.villages)
            }
        })
        rvAreaZoneName.adapter = mRvZoneAdapter
        mRvZoneAdapter?.setData(zoneBeans!!)
    }

    fun View.initAreaName(
        mContext: Context,
        districtBeans: MutableList<District.ZoneBean.VillageBean>?
    ) {
        clearAreaCheckList()
        mAreaNameRvAdapter = AreaNameRvAdapter(mContext,object :AreaNameRvAdapter.OnCheckListener{
            override fun isChecked(
                buttonView: View,
                isChecked: Boolean,
                item: District.ZoneBean.VillageBean
            ) {
                setItemAreaChecked(item,isChecked,checkedItem)
                btnAreaReset.enable(mContext)
            }
        },checkedItem)
        rvAreaName.layoutManager = LinearLayoutManager(mContext)
        rvAreaName.adapter = mAreaNameRvAdapter
        mAreaNameRvAdapter?.setData(districtBeans!!)
    }
}




