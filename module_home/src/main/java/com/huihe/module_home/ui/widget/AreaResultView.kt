package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.data.protocol.AreaBeanConvertModel
import com.huihe.module_home.data.protocol.AreaReq
import com.huihe.module_home.ext.*
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.adpter.AreaNameRvAdapter
import com.huihe.module_home.ui.adpter.RvAreaDistrictAdapter
import com.huihe.module_home.ui.adpter.RvZoneAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_search_by_area.view.*

class AreaResultView {
    private var mRvAreaDistrictAdapter: RvAreaDistrictAdapter? = null
    private var mRvZoneAdapter: RvZoneAdapter? = null
    private var mAreaNameRvAdapter: AreaNameRvAdapter? = null
    private var mListener: ISearchResultListener? = null
    private var checkedItem: AreaBeanConvertModel? = null
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
                if (villageIds.size>0){"${mCheckedItem?.districtName}+${villageIds.size}"}else {"区域"},
                CustomersModule.SearchType.AreaType
            )
        }
    }

    fun View.initRvAreaDistrictName(mContext: Context) {
        mRvAreaDistrictAdapter = RvAreaDistrictAdapter(mContext)
        rvAreaDistrictName.layoutManager = LinearLayoutManager(mContext)
        mRvAreaDistrictAdapter?.setOnItemClickListener(
            object : BaseRecyclerViewAdapter.OnItemClickListener<AreaBeanConvertModel> {
                override fun onItemClick(view: View, item: AreaBeanConvertModel, position: Int) {

                    checkedItem = item
                    initRvZone(mContext, item.zoneBean)
                    initAreaName(mContext, mutableListOf())
                }
            }
        )
        rvAreaDistrictName.adapter = mRvAreaDistrictAdapter
        mListener?.startLoad(mRvAreaDistrictAdapter)
    }

    fun View.initRvZone(
        mContext: Context,
        zoneBeans: MutableList<AreaBeanConvertModel.ZoneBean>?
    ) {
        mRvZoneAdapter = RvZoneAdapter(mContext)
        rvAreaZoneName.layoutManager = LinearLayoutManager(mContext)
        mRvZoneAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<AreaBeanConvertModel.ZoneBean> {
            override fun onItemClick(view: View, item: AreaBeanConvertModel.ZoneBean, position: Int) {
                initAreaName(mContext, item.districtBean)
            }
        })
        rvAreaZoneName.adapter = mRvZoneAdapter
        mRvZoneAdapter?.setData(zoneBeans!!)
    }

    fun View.initAreaName(
        mContext: Context,
        districtBeans: MutableList<AreaBeanConvertModel.DistrictBean>?
    ) {
        clearAreaCheckList()
        mAreaNameRvAdapter = AreaNameRvAdapter(mContext,object :AreaNameRvAdapter.OnCheckListener{
            override fun isChecked(
                buttonView: View,
                isChecked: Boolean,
                item: AreaBeanConvertModel.DistrictBean
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




