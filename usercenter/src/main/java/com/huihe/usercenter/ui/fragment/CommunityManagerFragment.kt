package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.AreaBeanRep
import com.huihe.usercenter.ext.getConvertProvinceList
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.CommunityManagerPresenter
import com.huihe.usercenter.presenter.view.CommunityManagerView
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.ui.activity.SearchCommunityActivity
import com.huihe.usercenter.ui.adapter.CityRvAdapter
import com.huihe.usercenter.ui.adapter.CountyRvAdapter
import com.huihe.usercenter.ui.adapter.ProvinceRvAdapter
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_community_manager.*
import org.jetbrains.anko.support.v4.startActivity

class CommunityManagerFragment : BaseMvpFragment<CommunityManagerPresenter>(),
    CommunityManagerView {

    var mProvinceRvAdapter: ProvinceRvAdapter? = null
    var mCityRvAdapter: CityRvAdapter? = null
    var mCountyRvAdapter: CountyRvAdapter? = null
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
        initView()
        initData()
    }

    private fun initView() {

        rvCommunityManagerInput.onClick {
            startActivity<SearchCommunityActivity>()
        }
        rvCommunityManagerInput.setVisible(false)
        rvCommunityManagerLeft.layoutManager = LinearLayoutManager(context)
        mProvinceRvAdapter = ProvinceRvAdapter(context!!)
        mProvinceRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Province> {
            override fun onItemClick(view: View, item: Province, position: Int) {
                initCityAdapter(item)
            }
        })
        rvCommunityManagerLeft.adapter = mProvinceRvAdapter

        mCityRvAdapter = CityRvAdapter(context!!)
        mCountyRvAdapter = CountyRvAdapter(context!!)
    }

    private fun initCityAdapter(item: Province) {
        rvCommunityManagerCenter.layoutManager = LinearLayoutManager(context)
        mCityRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<City> {
            override fun onItemClick(view: View, item: City, position: Int) {
                initCountyAdapter(item)
            }
        })
        mCityRvAdapter?.resetData()
        rvCommunityManagerCenter.adapter = mCityRvAdapter
        mCityRvAdapter?.setData(item.cities)
    }

    private fun initCountyAdapter(item: City) {
        mCountyRvAdapter?.resetData()
        rvCommunityManagerRight.layoutManager = LinearLayoutManager(context)
        rvCommunityManagerRight.adapter = mCountyRvAdapter
        mCountyRvAdapter?.setData(item.counties)
    }

    private fun initData() {
        mPresenter.getVillages()
    }

    override fun onGetAreaBeanListResult(list: MutableList<AreaBeanRep.AreaBean>?) {
        var mProvinceList = getConvertProvinceList(list)
        mProvinceRvAdapter?.setData(mProvinceList)
    }

}
