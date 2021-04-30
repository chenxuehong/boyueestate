package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.House
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.ext.getConvertData
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseDetailPresenter
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.ui.adpter.HouseDetailTvAdapter
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_detail.*

/**
 * 房源详情
 */
class HouseDetailFragment : BaseMvpFragment<HouseDetailPresenter>(), HouseDetailView {

    lateinit var houseDetailTvAdapter: HouseDetailTvAdapter
    var id:String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_detail, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString(HomeConstant.KEY_HOUSE_ID)
        initView()
        initData()
    }

    private fun initView() {
        var rightContentView = house_detail_titleBar.getRightContentView()
        rightContentView.onClick {
            Toast.makeText(context, "点击右标题", Toast.LENGTH_LONG).show()
        }
        house_detail_rvList.layoutManager = LinearLayoutManager(context)
        houseDetailTvAdapter = HouseDetailTvAdapter(context)
        house_detail_rvList.adapter = houseDetailTvAdapter

    }

    private fun initData() {
        mPresenter?.getHouseDetailById(id)
    }

    override fun onGetHouseDetailResult(house: House?) {
        var data: MutableList<ItemHouseDetail> = getConvertData(house)
        houseDetailTvAdapter?.setData(data)
    }

}