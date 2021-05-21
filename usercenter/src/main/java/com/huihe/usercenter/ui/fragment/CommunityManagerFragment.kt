package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.gson.Gson
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.CommunityManagerPresenter
import com.huihe.usercenter.presenter.view.CommunityManagerView
import com.huihe.usercenter.ui.activity.SearchCommunityActivity
import com.huihe.usercenter.ui.adapter.CityRvAdapter
import com.huihe.usercenter.ui.adapter.CountyRvAdapter
import com.huihe.usercenter.ui.adapter.ProvinceRvAdapter
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.event.VillageEvent
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.data.protocol.District
import kotlinx.android.synthetic.main.fragment_community_manager.*
import org.jetbrains.anko.support.v4.startActivity

class CommunityManagerFragment : BaseMvpFragment<CommunityManagerPresenter>(),
    CommunityManagerView {

    var mProvinceRvAdapter: ProvinceRvAdapter? = null
    var mCityRvAdapter: CityRvAdapter? = null
    var mCountyRvAdapter: CountyRvAdapter? = null
    var isSelect = false
    var mDistrictList: MutableList<District> = mutableListOf()

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_community_manager, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isSelect = arguments?.getBoolean(BaseConstant.KEY_ISSELECT, false) ?: false
        initView()
        initData()
    }

    private fun initView() {
        if (isSelect){
            headCommunityManager.setTitle("选择区域")
        }
        rvCommunityManagerInput.onClick {
            if (mDistrictList != null) {
                startActivity<SearchCommunityActivity>(BaseConstant.KEY_ISSELECT to isSelect)
            }
        }
        rvCommunityManagerLeft.layoutManager = LinearLayoutManager(context)
        mProvinceRvAdapter = ProvinceRvAdapter(context!!)
        mProvinceRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<District> {
            override fun onItemClick(view: View, item: District, position: Int) {
                initCityAdapter(item)
            }
        })
        rvCommunityManagerLeft.adapter = mProvinceRvAdapter

        mCityRvAdapter = CityRvAdapter(context!!)
        mCountyRvAdapter = CountyRvAdapter(context!!)
        if (isSelect) {
            Bus.observe<VillageEvent>()
                .subscribe {
                    activity?.finish()
                }.registerInBus(this)
        }
    }

    private fun initCityAdapter(item: District) {
        rvCommunityManagerCenter.layoutManager = LinearLayoutManager(context)
        mCityRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<District.ZoneBean> {
            override fun onItemClick(view: View, item: District.ZoneBean, position: Int) {
                initCountyAdapter(item)
            }
        })
        mCityRvAdapter?.resetData()
        rvCommunityManagerCenter.adapter = mCityRvAdapter
        mCityRvAdapter?.setData(item.zones)
    }

    private fun initCountyAdapter(item: District.ZoneBean) {
        mCountyRvAdapter?.resetData()
        rvCommunityManagerRight.layoutManager = LinearLayoutManager(context)
        mCountyRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<District.ZoneBean.VillageBean> {
            override fun onItemClick(
                view: View,
                item: District.ZoneBean.VillageBean,
                position: Int
            ) {
                if (isSelect) {
                    finishForGetData(item)
                }
            }
        })
        rvCommunityManagerRight.adapter = mCountyRvAdapter
        mCountyRvAdapter?.setData(item.villages ?: mutableListOf())
    }

    private fun finishForGetData(item: District.ZoneBean.VillageBean) {
        Bus.send(
            VillageEvent(
                item.id ?: "",
                item.districtName ?: "",
                item.zoneName ?: "",
                item.name ?: ""
            )
        )
    }

    private fun initData() {
        mPresenter.getVillages()
    }

    override fun onGetAreaBeanListResult(list: MutableList<District>?) {
        mDistrictList = list ?: mutableListOf()
        mProvinceRvAdapter?.setData(mDistrictList)
    }

    override fun onDestroy() {
        try {
            if (isSelect) {
                Bus.unregister(this)
            }
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
