package com.huihe.module_home.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE
import com.eightbitlab.rxbus.Bus
import com.example.zhouwei.library.CustomPopWindow
import com.huihe.module_home.R
import com.huihe.module_home.ui.activity.AddHouseActivity
import com.huihe.module_home.ui.adapter.MoreRvAdapter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.OnRefreshListener
import com.kotlin.base.ext.doRefreshFragments
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.ResetEvent
import com.kotlin.provider.router.RouterPath
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.layout_fragment_home.*
import kotlinx.android.synthetic.main.layout_tel_dialog.view.*
import org.jetbrains.anko.support.v4.toast


@Route(path = RouterPath.HomeCenter.PATH_HOME)
class HomeFragment : BaseFragment(), OnRefreshListener {

    val fragments = ArrayList<Fragment>()
    val data: MutableList<String> = mutableListOf(
        BaseApplication.context.resources.getString(R.string.insert_house),
        BaseApplication.context.resources.getString(R.string.scan)
    )
    var mCustomPopWindow:CustomPopWindow?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        home_tabLayout?.setupWithViewPager(home_viewPager)
        val titles = ArrayList<String>()
        fragments.clear()
        titles.add("??????")
        fragments.add(HouseFragment())
        home_viewPager.adapter = BaseFragmentStatePageAdapter(
            childFragmentManager,
            titles,
            fragments
        )
        flClearRefresh.onClick {
            Bus.send(ResetEvent())
        }
        home_fl_add.onClick {
            showPopWindow(home_fl_add)
        }
    }

    override fun onRefresh() {
        fragments.doRefreshFragments()
    }

    private fun showPopWindow(homeFlAdd: FrameLayout) {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_tel_dialog, null, false)
        var mRvAdapter = MoreRvAdapter(context!!)
        mRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View, item: String, position: Int) {
                doItemClicked(item)
            }
        })
        (contentView.rvTelDialog).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRvAdapter
        }
        mRvAdapter.setData(data)
        mCustomPopWindow = CustomPopWindow.PopupWindowBuilder(context)
            .setView(contentView)
            .enableBackgroundDark(true) //??????popWindow????????????????????????
            .setBgDarkAlpha(0.7f) // ????????????
            .setFocusable(true)
            .setOutsideTouchable(true)
            .create()
            .showAsDropDown(
                homeFlAdd,
                DensityUtils.dp2px(context, -50f),
                DensityUtils.dp2px(context, 0f)
            )
    }

    private fun doItemClicked(item: String) {
        when (item) {
            BaseApplication.context.resources.getString(R.string.insert_house) -> {
                val intent = Intent(context, AddHouseActivity::class.java)
                startActivity(intent)
            }
            BaseApplication.context.resources.getString(R.string.scan) -> {
                requestScan{
                    val intent = Intent(context, CaptureActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }
        mCustomPopWindow?.dissmiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            //??????????????????????????????????????????
            if (null != data) {
                var bundle: Bundle? = data.extras ?: return
                if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    var result = bundle.getString(CodeUtils.RESULT_STRING);
//                    toast("????????????:${result}")
                    result?.apply {
                        ARouter.getInstance()
                            .build(RouterPath.HomeCenter.PATH_HOUSE_DETIL)
                            .withString(HomeConstant.KEY_HOUSE_ID ,this)
                            .navigation()
                    }
                } else if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    toast("?????????????????????")
                }
            }
        }
    }

    override fun onDestroy() {
        mCustomPopWindow?.dissmiss()
        super.onDestroy()
    }
}
