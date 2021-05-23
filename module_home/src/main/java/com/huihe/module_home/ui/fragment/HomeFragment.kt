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
import com.example.zhouwei.library.CustomPopWindow
import com.huihe.module_home.R
import com.huihe.module_home.ui.activity.AddHouseActivity
import com.huihe.module_home.ui.adpter.MoreRvAdapter
import com.huihe.module_home.ui.inter.RefreshListener
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.layout_fragment_home.*
import kotlinx.android.synthetic.main.layout_tel_dialog.view.*
import org.jetbrains.anko.support.v4.startActivity
import com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE
import com.huihe.module_home.ui.activity.HouseDetailActivity
import com.kotlin.provider.constant.HomeConstant
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.trello.rxlifecycle3.RxLifecycle.bindUntilEvent
import com.uuzuche.lib_zxing.activity.CodeUtils
import org.jetbrains.anko.support.v4.toast


@Route(path = RouterPath.HomeCenter.PATH_HOME)
class HomeFragment : BaseFragment() {

    val REQUEST_CODE_ADD_HOUSE = 1000
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
        titles.add("二手房")
        titles.add("地图找房")
        fragments.add(HouseFragment())
        fragments.add(HouseMapFragment())
        home_viewPager.adapter = BaseFragmentStatePageAdapter(
            childFragmentManager,
            titles,
            fragments
        )
        home_fl_add.onClick {
            showPopWindow(home_fl_add)
        }
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
            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
            .setBgDarkAlpha(0.7f) // 控制亮度
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
                startActivityForResult(intent, REQUEST_CODE_ADD_HOUSE)
            }
            BaseApplication.context.resources.getString(R.string.scan) -> {
                val intent = Intent(context, CaptureActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
        mCustomPopWindow?.dissmiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_HOUSE) {
            fragments?.forEach { fragment ->
                if (fragment is RefreshListener){
                    (fragment as RefreshListener).refreshData()
                }
            }
        }else if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                var bundle: Bundle? = data.extras ?: return
                if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    var result = bundle.getString(CodeUtils.RESULT_STRING);
//                    toast("解析结果:${result}")
                    result?.apply {
                        ARouter.getInstance()
                            .build(RouterPath.HomeCenter.PATH_HOUSE_DETIL)
                            .withString(HomeConstant.KEY_HOUSE_ID ,this)
                            .navigation()
                    }
                } else if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    toast("解析二维码失败")
                }
            }
        }
    }

    override fun onDestroy() {
        mCustomPopWindow?.dissmiss()
        super.onDestroy()
    }
}
