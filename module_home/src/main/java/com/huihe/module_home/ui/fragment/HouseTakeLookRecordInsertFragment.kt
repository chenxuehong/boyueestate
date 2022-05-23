package com.huihe.module_home.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.AddTakeLookRecordReq
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseTakeLookRecordInsertPresenter
import com.huihe.module_home.presenter.view.HouseTakeLookRecordInsertView
import com.huihe.module_home.ui.activity.HouseSelectActivity
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.getString
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.base.widgets.picker.WheelPicker.picker.DateTimePicker
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.MeRefreshEvent
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_havekeyuser.*
import kotlinx.android.synthetic.main.fragment_house_take_look_record_insert.*
import org.jetbrains.anko.support.v4.toast

class HouseTakeLookRecordInsertFragment : BaseMvpFragment<HouseTakeLookRecordInsertPresenter>(),
    HouseTakeLookRecordInsertView {


    var REQUEST_CODE_SELECT_HOUSE: Int = 10001
    var houseCode: String? = null
    var houseCodeList = mutableListOf<String>()
    var code: String? = null
    var customerId: String? = null
    var customerName: String? = null
    var beginTimePicker: DateTimePicker? = null
    var req:AddTakeLookRecordReq?=null
    var isTakeLooked = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_take_look_record_insert, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseCodeList.clear()
        code = arguments?.getString(HomeConstant.KEY_CODE)
        customerId = arguments?.getString(HomeConstant.KEY_CUSTOMER_ID)
        customerName = arguments?.getString(HomeConstant.KEY_CUSTOMER_NAME)
        req = AddTakeLookRecordReq(customerCode = code,customerId = customerId,customerName = customerName)
        initView()
    }

    private fun initView() {
        Bus.observe<SearchHouseEvent>()
            .subscribe {
                if ("AddressBookFragment" == it.type) {
                    if (isTakeLooked){
                        req?.takeLookUser = it?.name
                        req?.takeLookUserId = it?.id
                        nsvTakeLookInsert_taker?.setContent(it?.name?:"")
                    }else{
                        req?.accompanyUser = it?.name
                        req?.accompanyUserId = it?.id
                        nsvTakeLookInsert_Accompany?.setContent(it?.name?:"")
                    }
                }
            }.registerInBus(this)
        nsvTakeLookInsert_house.onClick {
            // 选择房源
            val intent = Intent(context, HouseSelectActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SELECT_HOUSE)
        }
        nsvTakeLookInsert_time.onClick {
            // 选择时间
            showTimeDialog()
        }
        nsvTakeLookInsert_taker.onClick {
            // 选择带看人
            isTakeLooked = true
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        nsvTakeLookInsert_Accompany.onClick {
            // 选择陪看人
            isTakeLooked = false
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        tvTakeLookInsert.onClick {
            if (checkInput()) {
                req?.follow = EtTakeLookInsertFollowContent.text.toString()
                mPresenter.addHouseTakeLookRecord(
                    req
                )
            }
        }
        var customer_evaluation = String.format(
            "<font color=\"#ff0000\">%s</font>%s",
            "*",
            resources.getString(R.string.customer_follow)
        )
        tvTakeLookFollowContent.text = Html.fromHtml(customer_evaluation)
    }

    private fun showTimeDialog() {
        beginTimePicker =
            DateTimePicker(activity, DateTimePicker.YEAR_MONTH_DAY, DateTimePicker.HOUR_24)
        val dateStr = DateUtils.getCurDateStr(DateUtils.FORMAT_SHORT)
        var weekStr = DateUtils.getChineseWeek(dateStr, DateUtils.FORMAT_SHORT)
        beginTimePicker?.setTitleText("${DateUtils.getCurYear()} (${weekStr})")
        beginTimePicker?.setOnDateTimePickListener(object :
            DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                val start_time = "$year-$month-$day $hour:$minute:00"
                req?.lookTime = start_time
                nsvTakeLookInsert_time.setContent("$year-$month-$day $hour:$minute")
            }

        })
        beginTimePicker?.setResetWhileWheel(false)
        beginTimePicker?.setDateRangeStart(
            DateUtils.getCurYear(),
            DateUtils.getCurMonth(),
            DateUtils.getCurDay()
        )
        val endTime = DateUtils.getSpecifiedDayAfter(
            DateUtils.getCurDateStr(DateUtils.datePattern),
            DateUtils.datePattern,
            15
        )
        val year = DateUtils.getYear(endTime, DateUtils.datePattern)
        val month = 12
        val day = DateUtils.getMonthOfDay(year, 12)
        beginTimePicker?.setDateRangeEnd(year + 4, month, day)
        beginTimePicker?.setSelectedItem(
            DateUtils.getCurYear(),
            DateUtils.getCurMonth(),
            DateUtils.getCurDay(),
            DateUtils.getCurHour(),
            DateUtils.getCurMinute()
        )
        beginTimePicker?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_SELECT_HOUSE == requestCode) {
            houseCode = data?.getStringExtra(HomeConstant.KEY_HOUSE_CODE)
            if (!TextUtils.isEmpty(houseCode)) {
                houseCodeList.add(houseCode!!)
                nsvTakeLookInsert_house.setContent(houseCodeList.getString(","))
                req?.houseCode = nsvTakeLookInsert_house.getContent()
            }
        }
    }

    private fun checkInput(): Boolean {
        var childCount = llLookRecordInsert.childCount
        for (i in 0 until childCount) {
            var childView = llLookRecordInsert.getChildAt(i)
            if (childView is NecessaryTitleSelectView) {
                if (childView.isNecessary && TextUtils.isEmpty(childView.getContent())) {
                    var tipContentText = childView.getTipContentText()
                    toast(tipContentText)
                    return false
                }
            } else if (childView is NecessaryTitleInputView) {
                if (childView.isNecessary && TextUtils.isEmpty(childView.getContent())) {
                    var tipContentText = childView.getHintContent()
                    toast(tipContentText)
                    return false
                }
            }
        }
        if (TextUtils.isEmpty(EtTakeLookInsertFollowContent.text.toString().trim())) {
            toast(resources.getString(R.string.customer_follow_input))
            return false
        }
        return true
    }

    override fun onAddHouseTakeLookResult() {
        Bus.send(MeRefreshEvent())
        activity?.finish()
    }

    override fun onDestroy() {
        try {
            beginTimePicker?.dismiss()
            Bus.unregister(this)
        } catch (e: Exception) {
        } finally {
            beginTimePicker = null
        }
        super.onDestroy()
    }
}
