package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.example.zhouwei.library.CustomPopWindow
import com.huihe.usercenter.R
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.injection.module.UserModule.UserLevels.Companion.Administrators
import com.huihe.usercenter.injection.module.UserModule.UserLevels.Companion.Staff
import com.huihe.usercenter.presenter.MineLookTaskHomePresenter
import com.huihe.usercenter.presenter.view.MineLookTaskHomeView
import com.huihe.usercenter.ui.adapter.MineLookTaskHomeFragmentAdapter
import com.huihe.usercenter.ui.adapter.MoreTaskRvAdapter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.LookTaskEvent
import kotlinx.android.synthetic.main.fragment_mine_looktask_home.*
import kotlinx.android.synthetic.main.layout_mine_looktash_dialog.view.*

class MineLookTaskHomeFragment : BaseMvpFragment<MineLookTaskHomePresenter>(),
    MineLookTaskHomeView {

    val TAG = MineLookTaskHomeFragment::class.java.simpleName
    var titles  = mutableListOf(
        BaseApplication.context.resources.getString(R.string.to_start),
        BaseApplication.context.resources.getString(R.string.take_look),
        BaseApplication.context.resources.getString(R.string.in_summary),
        BaseApplication.context.resources.getString(R.string.under_review),
        BaseApplication.context.resources.getString(R.string.finished)
    )

    var mAdministratorsData: MutableList<String> = mutableListOf(
        BaseApplication.context.resources.getString(R.string.as_createUser),
        BaseApplication.context.resources.getString(R.string.as_takeLookUser),
        BaseApplication.context.resources.getString(R.string.as_AccompanyUser),
        BaseApplication.context.resources.getString(R.string.as_ManagerUser)
    )

    var mStaffData: MutableList<String> = mutableListOf(
        BaseApplication.context.resources.getString(R.string.as_createUser),
        BaseApplication.context.resources.getString(R.string.as_takeLookUser),
        BaseApplication.context.resources.getString(R.string.as_AccompanyUser)
    )

    var dataMap :MutableMap<Int,MutableList<String>> = mutableMapOf(
        Administrators to mAdministratorsData,
        Staff to mStaffData
    )

    var tabPosition:Int=0
    var mCustomPopWindow:CustomPopWindow?=null
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            tabPosition = savedInstanceState.getInt(
                UserConstant.KEY_TAB_POSITION,
                0
            )
        }
        LogUtils.d(
            TAG,
            "onCreate tabPosition:$tabPosition"
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (viewpager != null) {
            tabPosition = viewpager.getCurrentItem()
            outState.putInt(
                UserConstant.KEY_TAB_POSITION,
                viewpager.getCurrentItem()
            )
        }
        LogUtils.d(
            TAG,
            "onSaveInstanceState tabPosition:$tabPosition"
        )
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            tabPosition = savedInstanceState.getInt(
                UserConstant.KEY_TAB_POSITION,
                0
            )
        }
        LogUtils.d(
            TAG,
            "onViewStateRestored tabPosition:$tabPosition"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_mine_looktask_home, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        var curStatus = arguments?.getInt(BaseConstant.KEY_STATUS) ?: 0
        tabPosition = curStatus
        titleBar.getRightView().onClick {
            mPresenter.getUserLevels()
        }
    }

    override fun onIsAdministrators(isAdministrators: Boolean) {
        showPopWindow(titleBar.getRightView(),isAdministrators)
    }

    private fun showPopWindow(tvRightTitle: TextView, isAdministrators: Boolean) {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_mine_looktash_dialog, null, false)
        var mRvAdapter = MoreTaskRvAdapter(context!!)
        mRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View, item: String, position: Int) {
                tvRightTitle.text = item
                doItemClicked(item)
            }
        })
        (contentView.rvDialog).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRvAdapter
        }
        mRvAdapter.setData(dataMap[if (isAdministrators) Administrators else Staff]!!)
        mCustomPopWindow = CustomPopWindow.PopupWindowBuilder(context)
            .setView(contentView)
            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
            .setBgDarkAlpha(0.7f) // 控制亮度
            .setFocusable(true)
            .setOutsideTouchable(true)
            .create()
            .showAsDropDown(
                tvRightTitle,
                DensityUtils.dp2px(context, -50f),
                DensityUtils.dp2px(context, 0f)
            )
    }

    private fun doItemClicked(item: String) {
        Bus.send(LookTaskEvent(getType(item)))
        mCustomPopWindow?.dissmiss()
    }

    private fun getType(item: String): Int? {
        return when(item){
            BaseApplication.context.resources.getString(R.string.as_createUser) ->{
                0
            }
            BaseApplication.context.resources.getString(R.string.as_takeLookUser) ->{
                1
            }
            BaseApplication.context.resources.getString(R.string.as_AccompanyUser) ->{
                2
            }
            else ->{
                null
            }
        }
    }

    private fun initData() {
        initViewPager()
    }

    private fun initViewPager() {
        val fragments = mutableListOf<Fragment>()
        titles.forEachIndexed { status, item ->
            fragments.add(getFragment(status))
        }
        viewpager.adapter = MineLookTaskHomeFragmentAdapter(childFragmentManager, titles, fragments)
        tabLayout.setupWithViewPager(viewpager)
        viewpager.setOffscreenPageLimit(titles.size)
        viewpager.setCurrentItem(tabPosition)
    }

    private fun getFragment(status: Int): Fragment {
        var fragment = MineLookTaskFragment()
        val args = Bundle()
        args.putInt(BaseConstant.KEY_STATUS,status)
        fragment.arguments = args
        return fragment
    }

    override fun onDestroy() {
        try {
            mCustomPopWindow?.dissmiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

}