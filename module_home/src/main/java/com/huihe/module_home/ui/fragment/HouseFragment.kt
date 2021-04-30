package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.House
import com.huihe.module_home.data.protocol.FloorResult
import com.huihe.module_home.data.protocol.ISearchResult
import com.huihe.module_home.data.protocol.PriceResult
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HousePresenter
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.ui.activity.HouseDetailActivity
import com.huihe.module_home.ui.adpter.SecondHandHouseAdapter
import com.huihe.module_home.ui.widget.ISearchResultListener
import com.huihe.module_home.ui.widget.SearchResultViewController
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_secondhandhouse.*
import org.jetbrains.anko.support.v4.startActivity


class HouseFragment : BaseMvpFragment<HousePresenter>(), SecondHandHouseView,
    ISearchResultListener {

    private val TAG: String? = HouseFragment::class.java.simpleName
    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    private var hasMoreData = true
    private var mGoodsAdapter: SecondHandHouseAdapter? = null
    private val headers = arrayOf("区域", "楼层", "价格", "更多", "排序")
    private val citys =
        arrayOf("不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州")
    private val popupViews = mutableListOf<View>()

    init {
        hasMoreData = true
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_secondhandhouse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRefreshLayout()
        initData()
    }

    private fun initView() {
        customers_mRecyclerView.layoutManager = LinearLayoutManager(context)
        mGoodsAdapter = SecondHandHouseAdapter(context!!)
        customers_mRecyclerView.adapter = mGoodsAdapter

        mGoodsAdapter?.setOnItemClickListener(object :

            BaseRecyclerViewAdapter.OnItemClickListener<House> {
            override fun onItemClick(view: View, item: House, position: Int) {
                startActivity<HouseDetailActivity>(HomeConstant.KEY_HOUSE_ID to item.id)
            }
        })
        var init = SearchResultViewController.init(context!!, dropDownMenu.isShowing)
        dropDownMenu.setDropDownMenu(headers.asList(), init.getAllViews(this), customers_mMultiStateView)

    }

    override fun onSearchResult(iSearchResult: ISearchResult?,showTip: String, floorsType: Int) {
        dropDownMenu.setTabText(showTip)
        when (floorsType) {
            CustomersModule.SearchType.AreaType -> {

            }
            CustomersModule.SearchType.FloorsType -> {

                if (iSearchResult != null){
                    var floorResult = iSearchResult as FloorResult
                    Log.i(
                        TAG,
                        "floorResult : floorLess =" + floorResult.floorLess + ",floorMore=" + floorResult.floorMore
                    )
                }
            }
            CustomersModule.SearchType.PriceType -> {
                if (iSearchResult != null) {
                    var priceResult = iSearchResult as PriceResult
                    Log.i(
                        TAG,
                        "priceResult : priceLess =" + priceResult.priceLess + ",priceMore=" + priceResult.priceMore
                    )
                }

            }
            CustomersModule.SearchType.MoreType -> {

            }
            CustomersModule.SearchType.SortType -> {

            }
        }
        if (dropDownMenu.isShowing) {
            dropDownMenu.closeMenu()
        }
    }

    override fun onDestroyView() {
        //退出activity前关闭菜单
        if (dropDownMenu.isShowing) {
            dropDownMenu.closeMenu()
        }
        SearchResultViewController.detach()
        super.onDestroyView()
    }

    private fun initRefreshLayout() {
        customers_mBGARefreshLayout.setEnableRefresh(false)
        customers_mBGARefreshLayout.setOnLoadMoreListener {
            if (hasMoreData) {
                mCurrentPage++
                loadData()
            } else {
                customers_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
            }
        }
    }

    private fun initData() {
        customers_mMultiStateView?.startLoading()
        loadData()
    }

    private fun loadData() {
        mPresenter?.getHouseList(mCurrentPage, mPageSize)
    }

    override fun onDataIsNull() {
        customers_mMultiStateView?.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onError(text: String) {
        customers_mMultiStateView?.viewState = MultiStateView.VIEW_STATE_ERROR
    }

    override fun onGetHouseListResult(result: MutableList<House>?) {
        customers_mBGARefreshLayout?.finishLoadMore()
        customers_mBGARefreshLayout?.finishRefresh()
        hasMoreData = if (result != null) (result.size <= mPageSize) else false
        if (result != null && result.size > 0) {
            if (mCurrentPage == 1) {
                mGoodsAdapter?.setData(result)
            } else {
                mGoodsAdapter?.dataList?.addAll(result)
                mGoodsAdapter?.notifyDataSetChanged()
            }
            customers_mMultiStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            onDataIsNull()
        }
    }
}