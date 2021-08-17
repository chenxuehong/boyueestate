package com.huihe.module_home.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.*
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HousePresenter
import com.huihe.module_home.presenter.view.SecondHandHouseView
import com.huihe.module_home.ui.activity.HouseDetailActivity
import com.huihe.module_home.ui.activity.SearchHouseActivity
import com.huihe.module_home.ui.adapter.RvAreaDistrictAdapter
import com.huihe.module_home.ui.adapter.SecondHandHouseAdapter
import com.huihe.module_home.ui.widget.ISearchResultListener
import com.huihe.module_home.ui.widget.SearchResultViewController
import com.kennyc.view.MultiStateView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.ReflectionUtil
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.data.protocol.District
import com.kotlin.provider.event.AddHouseEvent
import com.kotlin.provider.event.HouseSelectEvent
import com.kotlin.provider.event.ResetEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_secondhandhouse.*
import kotlinx.android.synthetic.main.layout_refresh.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult

@Route(path = RouterPath.HomeCenter.PATH_HOUSE_FRAGMENT)
class HouseFragment : BaseMvpFragment<HousePresenter>(), SecondHandHouseView,
    ISearchResultListener {

    private val TAG: String? = HouseFragment::class.java.simpleName
    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    private var hasMoreData = true
    private var mGoodsAdapter: SecondHandHouseAdapter? = null
    private lateinit var layoutRefreshContentView: View
    private lateinit var mSearchResultViewController: SearchResultViewController
    private var headers = arrayOf("区域", "楼层", "价格", "有效", "排序")
    private var mFloorRanges: MutableList<FloorReq>? = null
    private var mPriceRanges: MutableList<PriceReq>? = null
    private var sortReq: SortReq? = SortReq()
    private var moreReq: MoreReq? = MoreReq()
    private var villageIds: MutableList<String>? = null
    private var mRvAreaDistrictAdapter: RvAreaDistrictAdapter? = null
    private var  mAreaMultiStateView:MultiStateView?=null
    private var isHouseSelect: Boolean = false
    private var uiStatus: Int = BaseConstant.KEY_STATUS_DEFAULT
    var mSearchReq: SearchReq = SearchReq()
    var mSortModules = mutableListOf(
        CustomersModule.SearchType.FloorsType,
        CustomersModule.SearchType.PriceType,
        CustomersModule.SearchType.MoreType,
        CustomersModule.SearchType.SortType
    )

    init {
        hasMoreData = true
    }

    val RESULT_CODE_GET_HOUSE_CODE: Int = 1000
    val REQUEST_CODE_SEARCH: Int = 1001
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
        uiStatus = arguments?.getInt(BaseConstant.KEY_STATUS, BaseConstant.KEY_STATUS_DEFAULT)
            ?: BaseConstant.KEY_STATUS_DEFAULT
        var villageName = arguments?.getString(HomeConstant.KEY_VILLAGE_NAME)
        var schoolId = arguments?.getString(HomeConstant.KEY_SCHOOL_ID)
        var villageIdsStr = arguments?.getString(HomeConstant.KEY_VILLAGE_IDS) ?: ""
        villageIds = if (TextUtils.isEmpty(villageIdsStr)) {
            null
        } else {
            villageIdsStr.split(",").toMutableList()
        }
        mSearchReq = SearchReq()
        if (!TextUtils.isEmpty(villageName)) {
            mSearchReq.villageName = villageName
        }
        if (!TextUtils.isEmpty(schoolId)) {
            mSearchReq.schoolIds = mutableListOf(schoolId ?: "")
        }
        initEvent()
        initView()
        initRvAdapter()
        initDropDownMenu()
        initRefreshLayout()
        initData()
    }

    private fun initEvent() {
        Bus.observe<AddHouseEvent>()
            .subscribe {
                loadData()
            }.registerInBus(this)
        Bus.observe<ResetEvent>()
            .subscribe {
                mFloorRanges = null
                mPriceRanges = null
                villageIds = null
                sortReq = SortReq()
                moreReq = MoreReq()
                mSearchReq = SearchReq()
                showLoading()
                headers.forEachIndexed { index, item ->
                    setTabText(index, item)
                }
                loadData()
            }.registerInBus(this)
    }

    fun setTabText(position: Int, title: String?) {
        var tabMenuView: LinearLayout? = null
        try {
            tabMenuView = ReflectionUtil.getValue(dropDownMenu, "tabMenuView") as LinearLayout
            var tabs = getTab(tabMenuView)
            tabs[position].text = title

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var list = mutableListOf<TextView>()
    private fun getTab(tabMenuView: LinearLayout): MutableList<TextView> {
        if (list.size>0){
            return list
        }
        for (index in 0 until tabMenuView.childCount) {
            var childAt = tabMenuView.getChildAt(index)
            if (childAt is TextView) {
                list.add(childAt)
            }
        }
        return list
    }

    private fun initView() {
        layoutRefreshContentView =
            LayoutInflater.from(context).inflate(R.layout.layout_refresh, null)
        layoutRefreshContentView?.customersSearch?.onClick {
            startActivityForResult<SearchHouseActivity>(REQUEST_CODE_SEARCH)
        }
        showUI(uiStatus)
    }

    private fun initRvAdapter() {
        layoutRefreshContentView?.customers_mRecyclerView?.layoutManager =
            LinearLayoutManager(context)
        mGoodsAdapter = SecondHandHouseAdapter(context!!)
        layoutRefreshContentView?.customers_mRecyclerView?.adapter = mGoodsAdapter
        mGoodsAdapter?.setOnItemClickListener(object :

            BaseRecyclerViewAdapter.OnItemClickListener<House> {
            override fun onItemClick(view: View, item: House, position: Int) {
                if (isHouseSelect) {
                    Bus.send(HouseSelectEvent(item.houseCode))
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
    }

    private fun initDropDownMenu() {
        mSearchResultViewController =
            SearchResultViewController(context!!, dropDownMenu.isShowing)
        dropDownMenu.setDropDownMenu(
            headers.asList(),
            mSearchResultViewController.getAllViews(this),
            layoutRefreshContentView
        )
    }

    private fun showUI(uiStatus: Int) {
        when (uiStatus) {
            BaseConstant.KEY_STATUS_MAP -> {
                // 隐藏区域排序
                mSortModules?.remove(CustomersModule.SearchType.AreaType)
                headers = arrayOf("楼层", "价格", "有效", "排序")
                // 隐藏搜索按钮
                layoutRefreshContentView?.customersSearch?.setVisible(false)
            }
            else -> {
                // 显示区域排序
                mSortModules?.add(0, CustomersModule.SearchType.AreaType)
                headers = arrayOf("区域", "楼层", "价格", "有效", "排序")
                // 显示搜索按钮
                layoutRefreshContentView?.customersSearch?.setVisible(true)
            }
        }
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

    override fun onDestroy() {
        //退出activity前关闭菜单
        dropDownMenu?.closeMenu()
        list?.clear()
        mSearchResultViewController?.detach()
        Bus.unregister(this)
        super.onDestroy()
    }

    private fun initRefreshLayout() {
        layoutRefreshContentView?.customers_mBGARefreshLayout.setOnRefreshListener {
            mCurrentPage = 1
            loadData()
        }
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
            villageIds = villageIds,
            searchReq = mSearchReq
        )
    }

    override fun onGetAreaBeanListResult(list: MutableList<District>?) {
        mRvAreaDistrictAdapter?.setData(list ?: mutableListOf())
        mAreaMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_CONTENT
    }

    override fun startLoad(
        adapter: RvAreaDistrictAdapter?,
        mMultiStateView: MultiStateView
    ) {
        mRvAreaDistrictAdapter = adapter
        mPresenter?.getVillages()
       this.mAreaMultiStateView= mMultiStateView
    }

    override fun getSearchModules(): MutableList<Int> {
        return mSortModules
    }

    override fun onDataIsNull() {
        layoutRefreshContentView?.customers_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_EMPTY
//        mAreaMultiStateView?.viewState =
//            MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onError(text: String) {
        super.onError(text)
        layoutRefreshContentView?.customers_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_ERROR
        mAreaMultiStateView?.viewState =
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_SEARCH == requestCode) {
            if (data != null) {
                var json = data.getStringExtra(HomeConstant.KEY_SEARCH_REQ)
                if (!TextUtils.isEmpty(json)) {
                    mSearchReq = Gson().fromJson<SearchReq>(json, SearchReq::class.java)
                    mCurrentPage = 1
                    loadData()
                }
            }
        }
    }
}