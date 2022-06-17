package com.huihe.customercenter.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.gson.Gson
import com.huihe.customercenter.R
import com.huihe.boyueentities.protocol.customer.*
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.CustomerListPresenter
import com.huihe.customercenter.presenter.view.TransactionView
import com.huihe.customercenter.ui.activity.CustomerDetailActivity
import com.huihe.customercenter.ui.activity.CustomerSearchActivity
import com.huihe.customercenter.ui.adapter.DeptUsersRvAdapter
import com.huihe.customercenter.ui.adapter.StatusRvAdapter
import com.huihe.customercenter.ui.adapter.TransactionRvAdapter
import com.huihe.customercenter.ui.widget.ISearchResultListener
import com.huihe.customercenter.ui.widget.SearchResultViewController
import com.kennyc.view.MultiStateView
import com.kotlin.base.common.OnRefreshListener
import com.kotlin.base.event.LogoutEvent
import com.kotlin.base.ext.*
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.ReflectionUtil
import com.kotlin.provider.constant.CustomerConstant
import com.kotlin.provider.event.AddCustomerEvent
import com.kotlin.provider.event.ResetCustomerEvent
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.layout_fragment_trannsaction.*
import kotlinx.android.synthetic.main.layout_refresh.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast

class CustomerListFragment : BaseMvpFragment<CustomerListPresenter>(), TransactionView,
    ISearchResultListener, OnRefreshListener {

    private var mCurrentPage: Int = 1
    private var mPageSize: Int = 30
    var createUserList: String? = null
    private val headers = arrayOf("录入人", "有效", "更多", "排序")
    private lateinit var layoutRefreshContentView: View
    var transactionRvAdapter: TransactionRvAdapter? = null
    var customerType :Int?= null
    var isCornucopia: Int? = null
    var status: Int? = 1
    var mMoreReq: MoreReq? = null
    var mSortReq: SortReq? = null
    var mSearchResultViewController: SearchResultViewController? = null
    val REQUEST_CODE_CUSTOMER_SEARCH = 10000
    var mCustomerSearchReq: CustomerSearchReq = CustomerSearchReq()
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
        return inflater.inflate(R.layout.layout_fragment_trannsaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerType = arguments?.getInt(CustomerConstant.KEY_CUSTOMERTYPE, 1) ?: 1
        isCornucopia = arguments?.getInt(CustomerConstant.KEY_IS_CORNUCOPIA)
        Bus.observe<AddCustomerEvent>()
            .subscribe {
                toast("新增成功")
                mCurrentPage = 1
                loadData()
            }.registerInBus(this)
        initView()
        initRefreshLayout()
        initData()
    }

    private fun initView() {

        mSearchResultViewController =
            SearchResultViewController(context!!, dropDownMenu.isShowing)
        layoutRefreshContentView =
            LayoutInflater.from(context).inflate(R.layout.layout_refresh, null)
        layoutRefreshContentView?.customersSearch?.setVisible(true)
        layoutRefreshContentView?.customersSearch.onClick {
            startActivityForResult<CustomerSearchActivity>(REQUEST_CODE_CUSTOMER_SEARCH)
        }
        dropDownMenu.setDropDownMenu(
            headers.asList(),
            mSearchResultViewController?.getAllViews(this)!!,
            layoutRefreshContentView
        )

        transactionRvAdapter = TransactionRvAdapter(context!!)
        transactionRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<CustomerRep.Customer> {
            override fun onItemClick(view: View, item: CustomerRep.Customer, position: Int) {
                startActivity<CustomerDetailActivity>(CustomerConstant.KEY_CUSTOMER_ID to item.id)
            }
        })
        layoutRefreshContentView?.customers_mRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionRvAdapter
        }
        Bus.observe<LogoutEvent>()
            .subscribe {
                mCurrentPage = 1
                loadData()
                activity?.finish()
            }.registerInBus(this)
        Bus.observe<ResetCustomerEvent>()
            .subscribe {
                status = 1
                mMoreReq = MoreReq()
                mSortReq = SortReq()
                mCustomerSearchReq = CustomerSearchReq()
                isCornucopia = null
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
        mPresenter?.getCustomerList(
            CustomerReq(
                page = mCurrentPage,
                customerType = customerType,
                createUserList = createUserList,
                status = status,
                userType = mMoreReq?.userType,
                isOwn = mMoreReq?.isOwn,
                isTakeLook = mMoreReq?.isTakeLook,
                isCollection = mMoreReq?.isCollection,
                createDateAsc = mSortReq?.createDateAsc,
                followUpDateAsc = mSortReq?.followUpDateAsc,
                customerCode = mCustomerSearchReq.customerCode,
                mobile = mCustomerSearchReq.mobile,
                customerName = mCustomerSearchReq.customerName,
                demandBeat = mCustomerSearchReq.demandBeat,
                remarks = mCustomerSearchReq.remarks,
                isCornucopia = isCornucopia
            )
        )
    }

    override fun onRefresh() {
        mCurrentPage = 1
        layoutRefreshContentView?.apply {
            customers_mBGARefreshLayout?.triggerAutoRefresh {
                customers_mMultiStateView?.isLoading()?:true
            }
        }
    }

    override fun onSearchResult(iSearchResult: ISearchResult?, showTip: String, type: Int) {
        showLoading()
        dropDownMenu.setTabText(showTip)
        resetData()
        when (type) {
            CustomersModule.SearchType.CreateUserType -> {
                if (iSearchResult != null) {
                    var req = iSearchResult as DeptUserResult
                    createUserList = req.createUserList
                }
            }
            CustomersModule.SearchType.StatusType -> {
                if (iSearchResult != null) {
                    var req = iSearchResult as StatusRep
                    status = req.dataKey
                }
            }

            CustomersModule.SearchType.MoreType -> {

                if (iSearchResult != null) {
                    mMoreReq = iSearchResult as MoreReq
                }
            }
            CustomersModule.SearchType.SortType -> {
                if (iSearchResult != null) {
                    mSortReq = iSearchResult as SortReq
                }
            }
        }
        loadData()
        dropDownMenu.closeMenu()
    }

    var adapter: DeptUsersRvAdapter? = null
    var mSortRvAdapter: StatusRvAdapter? = null
    override fun startLoadDeptUsers(adapter: DeptUsersRvAdapter) {
        this.adapter = adapter
        mPresenter?.getDeptUserList()
    }

    override fun startLoadStatus(mSortRvAdapter: StatusRvAdapter) {
        this.mSortRvAdapter = mSortRvAdapter
        mPresenter?.getCustomerStatusList("customer_status")
    }

    override fun onDeptUserListResult(t: MutableList<DeptUserRep>?) {
        adapter?.setData(t!!)
    }

    override fun onCustomerStatusListResult(t: MutableList<StatusRep>?) {
        var mutableList = t ?: mutableListOf()
        mutableList.add(0, StatusRep(null, "customer_status", "全部", "客户状态", "342", "0"))
        mSortRvAdapter?.setData(t!!)
    }

    private fun resetData() {
        mCurrentPage = 1
    }

    override fun onCustomerListResult(result: MutableList<CustomerRep.Customer>?) {

        layoutRefreshContentView?.customers_mBGARefreshLayout?.finishRefresh()
        layoutRefreshContentView?.customers_mBGARefreshLayout?.finishLoadMore()
        var hasMoreData = if (result != null) (result.size >= mPageSize) else false

        if (result != null && result.size > 0) {
            if (mCurrentPage == 1) {
                layoutRefreshContentView?.customers_mBGARefreshLayout?.resetNoMoreData()
                transactionRvAdapter?.setData(result)
            } else {
                transactionRvAdapter?.dataList?.addAll(result)
                transactionRvAdapter?.notifyDataSetChanged()
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

    override fun onDataIsNull() {
        layoutRefreshContentView?.customers_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CUSTOMER_SEARCH) {
            if (data != null) {
                var json = data.getStringExtra(CustomerConstant.KEY_CUSTOMER_SEARCH)
                if (!TextUtils.isEmpty(json)) {
                    mCustomerSearchReq =
                        Gson().fromJson<CustomerSearchReq>(json, CustomerSearchReq::class.java)
                    initData()
                }
            }
        }
    }

    override fun onError(text: String) {
        super.onError(text)
        layoutRefreshContentView?.customers_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_ERROR
    }

    override fun onDestroy() {
        try {
            mSearchResultViewController?.detach()
            Bus.unregister(this)
            list?.clear()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}