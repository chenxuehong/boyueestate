package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.DeptUserRep
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.AddressBookPresenter
import com.huihe.usercenter.presenter.view.AddressBookView
import com.huihe.usercenter.ui.activity.DeptInfoActivity
import com.huihe.usercenter.ui.adapter.DeptChildRvAdapter
import com.huihe.usercenter.ui.adapter.DeptUserRvAdapter
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.UserConstant
import kotlinx.android.synthetic.main.fragment_adddress_book.*
import org.jetbrains.anko.support.v4.startActivity

class AddressBookFragment : BaseMvpFragment<AddressBookPresenter>(), AddressBookView {

    var mDeptUserRvAdapter: DeptUserRvAdapter? = null
    var mDeptChildRvAdapter: DeptChildRvAdapter? = null
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
        return initInflater(context!!, R.layout.fragment_adddress_book, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mDeptUserRvAdapter = DeptUserRvAdapter(context!!)
        mDeptUserRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<DeptUserRep> {
            override fun onItemClick(view: View, item: DeptUserRep, position: Int) {
                initCityAdapter(item)
            }
        })
        rvAddressBookLeft.layoutManager = LinearLayoutManager(context)
        rvAddressBookLeft.adapter = mDeptUserRvAdapter
        mDeptChildRvAdapter = DeptChildRvAdapter(context!!)
    }

    override fun onDeptUsersResult(t: MutableList<DeptUserRep>?) {
        mDeptUserRvAdapter?.setData(t!!)
    }

    private fun initCityAdapter(item: DeptUserRep) {
        rvAddressBookRight.layoutManager = LinearLayoutManager(context)
        mDeptChildRvAdapter?.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<DeptUserRep.DeptUser> {
            override fun onItemClick(view: View, item: DeptUserRep.DeptUser, position: Int) {
                startActivity<DeptInfoActivity>(UserConstant.KEY_USER_ID to item.uid)
            }
        })
        mDeptChildRvAdapter?.resetData()
        rvAddressBookRight.adapter = mDeptChildRvAdapter
        mDeptChildRvAdapter?.setData(item.children!!)
    }

    private fun initData() {
        mPresenter.getDeptUsers()
    }
}