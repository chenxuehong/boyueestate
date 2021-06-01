package com.huihe.customercenter.ui.fragment

import android.content.Intent
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.qqtheme.framework.picker.SinglePicker
import com.alibaba.android.arouter.launcher.ARouter
import com.example.zhouwei.library.CustomPopWindow
import com.google.gson.Gson
import com.huihe.customercenter.R
import com.huihe.customercenter.data.protocol.CustomerDetailBean
import com.huihe.customercenter.data.protocol.CustomerDetailRep
import com.huihe.customercenter.injection.component.DaggerCustomersComponent
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.presenter.CustomerDetailPresenter
import com.huihe.customercenter.presenter.view.CustomerDetailView
import com.huihe.customercenter.ui.activity.CustomerFollowActivity
import com.huihe.customercenter.ui.activity.CustomerInfoEditActivity
import com.huihe.customercenter.ui.activity.CustomerLogActivity
import com.huihe.customercenter.ui.activity.SetCustomerInfoActivity
import com.huihe.customercenter.ui.adapter.CustomerDetailRvAdapter
import com.huihe.customercenter.ui.adapter.MoreRvAdapter
import com.huihe.customercenter.ui.adapter.TelRvAdapter
import com.kotlin.base.ext.callPhone
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseTitleRefreshFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.constant.CustomerConstant
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.layout_right_title_customer_detail.view.*
import kotlinx.android.synthetic.main.layout_tel_dialog.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

class CustomerDetailFragment :
    BaseTitleRefreshFragment<CustomerDetailPresenter, CustomerDetailRvAdapter, CustomerDetailBean>(),
    CustomerDetailView, CustomerDetailRvAdapter.OnCustomerListener {

    var callPopWindow: CustomPopWindow? = null
    var inflateRightContentView: View? = null
    var is_collection: Boolean = false
    var mMorePopWindow: CustomPopWindow? = null
    var mMoreList: MutableList<String>? = null
    var isLoadFinish = false
    var mCustomerDetailRep: CustomerDetailRep? = null
    var updateStatusPicker: SinglePicker<String>? = null
    val REQUEST_CODE_EDIT_INFO = 1000
    val REQUEST_CODE_SET_INFO = 1001
    var is_top = 0

    lateinit var mPhoneView:View
    init {
        is_collection = false
        isLoadFinish = false
    }

    override fun onPhoneCall(view: View, mobile: MutableList<String>?) {
        mPhoneView = view
        mPresenter.getCustomerMobile(id)

    }

    override fun onCustomerMobile(mobile: String) {
        var split = mobile?.split("*")
        split = split?.toMutableList()
        showTelListDialog(split, mPhoneView)
    }

    override fun onFollow(view: View, customerCode: String?) {
        startActivity<CustomerFollowActivity>(CustomerConstant.KEY_CUSTOMER_CODE to customerCode!!)
    }

    override fun onTakeLook(view: View, customerCode: String?) {
        ARouter.getInstance()
            .build(RouterPath.HomeCenter.PATH_TAKELOOK_RECORD)
            .withString(HomeConstant.KEY_CODE, customerCode)
            .withBoolean(HomeConstant.KEY_IS_ADD, true)
            .navigation(context)
    }

    override fun onLog(view: View, customerCode: String?) {
        startActivity<CustomerLogActivity>(CustomerConstant.KEY_CUSTOMER_CODE to customerCode!!)
    }

    private fun showTelListDialog(
        mobile: MutableList<String>?,
        view: View
    ) {
        var mobile = mobile ?: mutableListOf()
        if (mobile?.size == 0) {
            toast("暂无电话")
            return
        }
        val contentView =
            View.inflate(context, R.layout.layout_tel_dialog, null)
        var telRvAdapter = TelRvAdapter(context!!)
        telRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View, tel: String, position: Int) {
                callPhone(tel)
            }
        })
        (contentView.rvTelDialog).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = telRvAdapter
        }
        telRvAdapter.setData(
            mobile
        )
        callPopWindow = CustomPopWindow.PopupWindowBuilder(context)
            .setView(contentView)
            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
            .setBgDarkAlpha(0.7f) // 控制亮度
            .setFocusable(true)
            .setOutsideTouchable(true)
            .create()
            .showAsDropDown(view, 0, 10)
    }

    var id: String? = null
    var mobileList: MutableList<String> = mutableListOf()
    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun enableRefresh(): Boolean {
        return false
    }

    override fun getAdapter(): CustomerDetailRvAdapter {
        return CustomerDetailRvAdapter(context!!, this)
    }

    override fun initView() {
        mobileList.clear()
        isLoadFinish = false
        id = arguments?.getString(CustomerConstant.KEY_CUSTOMER_ID) ?: ""
        inflateRightContentView =
            inflateRightContentView(R.layout.layout_right_title_customer_detail)
        var mId = id
        inflateRightContentView?.apply {
            ivCustomerDetailStar.onClick {
                if (!is_collection) {
                    mPresenter.reqCollection(mId)
                } else {
                    mPresenter.reqDeleteCollection(mId)
                }
            }
            ivCustomerDetailMore.onClick {
                if (isLoadFinish)
                    showMoreDialog(ivCustomerDetailMore)
            }
        }
        mMoreList = mutableListOf(
            context!!.resources.getString(R.string.more_update),
            context!!.resources.getString(R.string.more_update_status),
            context!!.resources.getString(R.string.new_insert_tel),
            context!!.resources.getString(R.string.set_top),
            context!!.resources.getString(R.string.customer_info),
            context!!.resources.getString(R.string.more_edit_info)
        )
    }

    private fun showMoreDialog(ivHouseDetailMore: TextView) {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_tel_dialog, null, false)
        var moreRvAdapter = MoreRvAdapter(context!!)
        moreRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View, item: String, position: Int) {
                doItemClicked(position)
            }
        })
        (contentView.rvTelDialog).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moreRvAdapter
        }
        moreRvAdapter.setData(mMoreList!!)
        mMorePopWindow = CustomPopWindow.PopupWindowBuilder(context)
            .setView(contentView)
            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
            .setBgDarkAlpha(0.7f) // 控制亮度
            .setFocusable(true)
            .setOutsideTouchable(true)
            .create()
            .showAsDropDown(
                ivHouseDetailMore,
                DensityUtils.dp2px(context, -50f),
                DensityUtils.dp2px(context, 0f)
            )
    }

    private fun doItemClicked(item: Int) {
        when (item) {
            0 -> {
                if (mCustomerDetailRep != null) {
                    var data = Gson().toJson(
                        mCustomerDetailRep
                    )
                    startActivityForResult<CustomerInfoEditActivity>(
                        REQUEST_CODE_EDIT_INFO,
                        CustomerConstant.KEY_CUSTOMER_INFO to
                                data
                    )
                }

            }
            1 -> {
                showUpdateStatusDialog()
            }
            2 -> {
                showNewPhoneDialog()
            }
            3 -> {
                if (is_top == 1) {
                    // 取消置顶
                    mPresenter?.setCustomerStatus(
                        id,
                        1,
                        "0"
                    )
                } else {
                    // 置顶
                    mPresenter?.setCustomerStatus(
                        id,
                        1,
                        "1"
                    )
                }
            }
            4 -> {
                ARouter.getInstance()
                    .build(RouterPath.HomeCenter.PATH_CUSTOMER_PROFILE)
                    .withString(HomeConstant.KEY_HOUSE_ID, id)
                    .navigation(context)
            }
            5 -> {
                startActivityForResult<SetCustomerInfoActivity>(
                    REQUEST_CODE_SET_INFO,
                    CustomerConstant.KEY_CUSTOMER_ID to id,
                    CustomerConstant.KEY_CUSTOMER_NAME to mCustomerDetailRep?.customerName,
                    CustomerConstant.KEY_CUSTOMER_MOBILE to mCustomerDetailRep?.mobile
                )

            }
        }
        mMorePopWindow?.dissmiss()
    }

    private fun showUpdateStatusDialog() {
        var data = mutableListOf<String>()
        data.add(resources.getString(R.string.effective))
        data.add(resources.getString(R.string.bargain))
        data.add(resources.getString(R.string.defer))
        data.add(resources.getString(R.string.delete))
        updateStatusPicker = SinglePicker(activity, data)
        updateStatusPicker?.setCanceledOnTouchOutside(true)
        updateStatusPicker?.selectedIndex = 1
        updateStatusPicker?.setCycleDisable(true)
        updateStatusPicker?.setOnItemPickListener { index, item ->
            // 状态（1-有效；0-失效(成交）；2-删除；3-暂缓）
            mPresenter?.setCustomerStatus(
                id,
                4,
                getHFlag(item)
            )
        }
        updateStatusPicker?.show()
    }

    private fun getHFlag(item: String?): String {
        return when (item) {
            resources.getString(R.string.effective) -> {
                "1"
            }
            resources.getString(R.string.bargain) -> {
                "0"
            }
            resources.getString(R.string.defer) -> {
                "3"
            }
            else -> {
                "2"
            }
        }
    }

    private fun showNewPhoneDialog() {
        LDialog.init(childFragmentManager)
            .setLayoutRes(R.layout.layout_phone_dialog)
            .setBackgroundDrawableRes(R.drawable.new_phone_dialog_bg)
            .setGravity(Gravity.CENTER)
            .setWidthScale(0.75f)
            .setVerticalMargin(0.015f)
            .setAnimStyle(R.style.LDialogBottomAnimation)
            .setViewHandlerListener(object : ViewHandlerListener() {
                override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                    val etPhone = holder.getView<EditText>(R.id.etPhoneContent)

                    holder.getView<TextView>(R.id.tvPhoneCancel).setOnClickListener {
                        dialog.dismiss()
                    }
                    holder.getView<TextView>(R.id.tvPhoneInsert).setOnClickListener {
                        if (TextUtils.isEmpty(etPhone.text.toString().trim())) {
                            toast(resources.getString(R.string.input_phone_tip))
                            return@setOnClickListener
                        }
                        val tel = getTel(etPhone.text.toString().trim())
                        dialog.dismiss()
                        mPresenter?.setCustomerStatus(
                            id,
                            2,
                            tel
                        )
                    }
                }
            })
            .show()
    }

    private fun getTel(tel: String?): String {
        var ownerTel = mCustomerDetailRep?.mobile
        var sb = StringBuffer()
        if (!TextUtils.isEmpty(ownerTel)) {
            sb.append(ownerTel).append("*").append(tel)
        } else {
            sb.append(tel)
        }
        return sb.toString().trim()
    }

    override fun loadData() {
        mPresenter?.getCustomerDetail(id)
    }

    override fun onCustomerDetailResult(t: CustomerDetailRep?) {
        mCustomerDetailRep = t
        initTitle(t?.customerCode ?: "")
        var collection = t?.isCollection
        is_collection = !(collection == null || collection == 0)
        inflateRightContentView?.ivCustomerDetailStar?.setImageResource(
            if (is_collection) {
                R.drawable.star_checked
            } else {
                R.drawable.star_common
            }
        )
        is_top = t?.isTop ?: 0
        if (is_top == 1) {
            mMoreList?.set(3, context!!.resources.getString(R.string.noset_top))
        } else {
            mMoreList?.set(3, context!!.resources.getString(R.string.set_top))
        }
        var list = getDataModule(t)
        refreshDataList(list)
        isLoadFinish = true
    }

    override fun onReqCollectionResult(isCollection: Boolean) {
        inflateRightContentView?.ivCustomerDetailStar?.setImageResource(
            if (isCollection) {
                R.drawable.star_checked
            } else {
                R.drawable.star_common
            }
        )
        is_collection = isCollection
    }

    private fun getDataModule(data: CustomerDetailRep?): MutableList<CustomerDetailBean> {
        return mutableListOf(
            CustomerDetailBean(
                type = CustomersModule.CustomerDetailType.MAIN_INFO,
                mainInfo = CustomerDetailBean.CustomerMainInfo(
                    data?.customerType,
                    data?.customerName,
                    data?.isTop,
                    data?.isCornucopia,
                    data?.takeLookCount,
                    data?.demandBeat,
                    data?.demandBudget
                )
            ),
            CustomerDetailBean(
                type = CustomersModule.CustomerDetailType.HELPER_INFO,
                helperInfo = CustomerDetailBean.HelperInfo(
                    getMobile(data?.mobile),
                    data?.customerCode
                )
            ),
            CustomerDetailBean(
                type = CustomersModule.CustomerDetailType.BASIC_INFO,
                basicInfo = CustomerDetailBean.CustomerBasicInfo(
                    data?.customerName,
                    data?.source,
                    data?.demandFloor,
                    data?.demandHouseType,
                    data?.demandArea,
                    data?.demandBeat,
                    data?.isSubstitution,
                    data?.demandBudget,
                    data?.viewHouseDate,
                    data?.isFirstHouse,
                    data?.successDate,
                    data?.isStyle
                )
            ),
            CustomerDetailBean(
                type = CustomersModule.CustomerDetailType.REWARKS,
                rewarks = data?.remarks
            ),
            CustomerDetailBean(
                type = CustomersModule.CustomerDetailType.CREATE_USER_INFO,
                createUserInfo = CustomerDetailBean.CreateUserInfo(
                    data?.createUserName,
                    data?.createDate
                )
            )
        )
    }

    private fun getMobile(mobile: String?): MutableList<String> {
        var mobile = mobile ?: ""
        if (!mobile.trim().isNullOrEmpty()) {
            var toMutableList = mobile.split("*").toMutableList()
            return toMutableList
        }
        return mobileList
    }

    override fun addAllData(
        mRvAdapter: CustomerDetailRvAdapter,
        list: MutableList<CustomerDetailBean>
    ) {
        mRvAdapter.dataList.addAll(list)
    }

    override fun setData(
        mRvAdapter: CustomerDetailRvAdapter,
        list: MutableList<CustomerDetailBean>
    ) {
        mRvAdapter.setData(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_EDIT_INFO == requestCode) {
            loadData()
        } else if (REQUEST_CODE_SET_INFO == requestCode) {
            loadData()
        }
    }

    override fun onSetCustomerStatusSuccess() {
        toast("修改成功")
        loadData()
    }

    override fun onDestroy() {
        try {
            callPopWindow?.dissmiss()
            mMorePopWindow?.dissmiss()
            updateStatusPicker?.dismiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
