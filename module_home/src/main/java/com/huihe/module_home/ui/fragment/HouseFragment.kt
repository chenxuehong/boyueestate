package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.*
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HousePresenter
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.ui.activity.HouseDetailActivity
import com.huihe.module_home.ui.adpter.RvAreaDistrictAdapter
import com.huihe.module_home.ui.adpter.SecondHandHouseAdapter
import com.huihe.module_home.ui.inter.RefreshListener
import com.huihe.module_home.ui.widget.ISearchResultListener
import com.huihe.module_home.ui.widget.SearchResultViewController
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.data.protocol.District
import kotlinx.android.synthetic.main.fragment_secondhandhouse.*
import kotlinx.android.synthetic.main.layout_refresh.view.*
import org.jetbrains.anko.support.v4.startActivity


class HouseFragment : BaseMvpFragment<HousePresenter>(), SecondHandHouseView,
    ISearchResultListener, RefreshListener {

    private val TAG: String? = HouseFragment::class.java.simpleName
    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    private var hasMoreData = true
    private var mGoodsAdapter: SecondHandHouseAdapter? = null
    private lateinit var layoutRefreshContentView: View
    private lateinit var mSearchResultViewController: SearchResultViewController
    private val headers = arrayOf("区域", "楼层", "价格", "更多", "排序")
    private var mFloorRanges: MutableList<FloorReq>? = null
    private var mPriceRanges: MutableList<PriceReq>? = null
    private var sortReq: SortReq? = SortReq()
    private var moreReq: MoreReq? = MoreReq()
    private var villageIds: MutableList<String>? = null
    private var mRvAreaDistrictAdapter: RvAreaDistrictAdapter? = null
    private var isHouseSelect: Boolean = false

    init {
        hasMoreData = true
    }

    val RESULT_CODE_GET_HOUSE_CODE: Int = 1000
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
        isHouseSelect = arguments?.getBoolean(HomeConstant.KEY_IS_HOUSE_SELECT, false) ?: false
        initView()
        initRefreshLayout()
        initData()
    }

    private fun initView() {
        layoutRefreshContentView =
            LayoutInflater.from(context).inflate(R.layout.layout_refresh, null)
        layoutRefreshContentView?.customers_mRecyclerView?.layoutManager =
            LinearLayoutManager(context)
        mGoodsAdapter = SecondHandHouseAdapter(context!!)
        layoutRefreshContentView?.customers_mRecyclerView?.adapter = mGoodsAdapter

        mGoodsAdapter?.setOnItemClickListener(object :

            BaseRecyclerViewAdapter.OnItemClickListener<House> {
            override fun onItemClick(view: View, item: House, position: Int) {
                if (isHouseSelect) {
                    finishForSetResultStr(
                        HomeConstant.KEY_HOUSE_CODE,
                        item.houseCode!!,
                        RESULT_CODE_GET_HOUSE_CODE
                    )
                } else {
                    startActivity<HouseDetailActivity>(HomeConstant.KEY_HOUSE_ID to item.id)
                }
            }
        })
        mSearchResultViewController =
            SearchResultViewController(context!!, dropDownMenu.isShowing)
        dropDownMenu.setDropDownMenu(
            headers.asList(),
            mSearchResultViewController.getAllViews(this),
            layoutRefreshContentView
        )
    }

    override fun onSearchResult(iSearchResult: ISearchResult?, showTip: String, floorsType: Int) {
        showLoading()
        dropDownMenu.setTabText(showTip)
        resetData()
        when (floorsType) {
            CustomersModule.SearchType.AreaType -> {
                if (iSearchResult != null) {
                    var areaReq = iSearchResult as AreaReq
                    villageIds = areaReq.villageIds
                }
            }
            CustomersModule.SearchType.FloorsType -> {
                if (iSearchResult != null) {
                    mFloorRanges = mutableListOf()
                    var floorResult = iSearchResult as FloorReq
                    mFloorRanges?.add(
                        FloorReq(
                            floorResult.floorMore!!,
                            floorResult.floorLess!!
                        )
                    )
                } else {
                    mFloorRanges = null
                }
            }
            CustomersModule.SearchType.PriceType -> {
                if (iSearchResult != null) {
                    mPriceRanges = mutableListOf()
                    var priceResult = iSearchResult as PriceReq
                    mPriceRanges?.add(PriceReq(priceResult.priceMore!!, priceResult.priceLess!!))
                } else {
                    mPriceRanges = null
                }

            }
            CustomersModule.SearchType.MoreType -> {
                if (iSearchResult != null) {
                    moreReq = iSearchResult as MoreReq
                } else {
                    moreReq = null
                }
            }
            CustomersModule.SearchType.SortType -> {
                sortReq = SortReq()
                if (iSearchResult != null) {
                    sortReq = iSearchResult as SortReq
                }
            }
        }
        loadData()
        dropDownMenu.closeMenu()
    }

    private fun resetData() {
        mCurrentPage = 1
    }

    override fun onDestroyView() {
        //退出activity前关闭菜单
        dropDownMenu?.closeMenu()
        mSearchResultViewController?.detach()
        super.onDestroyView()
    }

    private fun initRefreshLayout() {
        layoutRefreshContentView?.customers_mBGARefreshLayout.setEnableRefresh(false)
        layoutRefreshContentView?.customers_mBGARefreshLayout.setOnLoadMoreListener {
            mCurrentPage++
            loadData()
        }
    }

    private fun initData() {
        layoutRefreshContentView?.customers_mMultiStateView?.startLoading()
        loadData()
    }

    private fun loadData() {
        mPresenter?.getHouseList(
            pageNo = mCurrentPage,
            floorRanges = mFloorRanges,
            priceRanges = mPriceRanges,
            sortReq = sortReq,
            moreReq = moreReq,
            villageIds = villageIds
        )
    }

    override fun onGetAreaBeanListResult(list: MutableList<District>?) {
        mRvAreaDistrictAdapter?.setData(list?: mutableListOf())
    }

    override fun startLoad(adapter: RvAreaDistrictAdapter?) {
        mRvAreaDistrictAdapter = adapter
        mPresenter?.getVillages()
    }

    override fun getModuleType(): Int {
        return SearchResultViewController.MODULE_HOUSE_FRAGMENT
    }

    override fun onDataIsNull() {
        layoutRefreshContentView?.customers_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onError(text: String) {
        layoutRefreshContentView?.customers_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_ERROR
    }

    override fun onGetHouseListResult(result: MutableList<House>?) {
        layoutRefreshContentView?.customers_mBGARefreshLayout?.finishRefresh()
        layoutRefreshContentView?.customers_mBGARefreshLayout?.finishLoadMore()
        hasMoreData = if (result != null) (result.size >= mPageSize) else false

        if (result != null && result.size > 0) {
            if (mCurrentPage == 1) {
                layoutRefreshContentView?.customers_mBGARefreshLayout?.resetNoMoreData()
                mGoodsAdapter?.setData(result)
            } else {
                mGoodsAdapter?.dataList?.addAll(result)
                mGoodsAdapter?.notifyDataSetChanged()
            }
            layoutRefreshContentView?.customers_mMultiStateView?.viewState =
                MultiStateView.VIEW_STATE_CONTENT
        } else {
            if (mCurrentPage == 1) {
                layoutRefreshContentView?.customers_mBGARefreshLayout?.finishRefreshWithNoMoreData()
                onDataIsNull()
            } else {
                layoutRefreshContentView?.customers_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
                layoutRefreshContentView?.customers_mMultiStateView?.viewState =
                    MultiStateView.VIEW_STATE_CONTENT
            }
        }
        if (!hasMoreData) {
            if (mCurrentPage == 1) {
                layoutRefreshContentView?.customers_mBGARefreshLayout?.finishRefreshWithNoMoreData()
            } else {
                layoutRefreshContentView?.customers_mBGARefreshLayout?.finishLoadMoreWithNoMoreData()
            }
        }
    }

    override fun refreshData() {
        loadData()
    }
}