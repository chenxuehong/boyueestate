package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseDetailPresenter
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.ui.adpter.HouseDetailTvAdapter
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_house_detail.*

/**
 * 房源详情
 */
class HouseDetailFragment : BaseMvpFragment<HouseDetailPresenter>(), HouseDetailView {

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        var rightContentView = house_detail_titleBar.getRightContentView()
        rightContentView.onClick {
            Toast.makeText(context,"点击右标题",Toast.LENGTH_LONG).show()
        }
        house_detail_rvList.layoutManager = LinearLayoutManager(context)
        var houseDetailTvAdapter = HouseDetailTvAdapter(context)
        house_detail_rvList.adapter = houseDetailTvAdapter
        
    }

    override fun onGetHouseDetailResult() {

    }

}