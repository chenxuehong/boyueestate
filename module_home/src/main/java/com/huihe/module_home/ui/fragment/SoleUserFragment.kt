package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.SoleUserRep
import com.huihe.module_home.data.protocol.SoleUserReq
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.SoleUserPresenter
import com.huihe.module_home.presenter.view.SoleUserView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.base.widgets.picker.WheelPicker.picker.DateTimePicker
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.OwnerInfoPutEvent
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_soleuser.*
import org.jetbrains.anko.support.v4.toast

class SoleUserFragment : BaseMvpFragment<SoleUserPresenter>(), SoleUserView {

    var houseId: String? = null
    var req: SoleUserReq? = null
    var startTimePicker: DateTimePicker? = null
    var endTimePicker: DateTimePicker? = null
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
        return initInflater(context!!, R.layout.fragment_soleuser, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseId = arguments?.getString(HomeConstant.KEY_HOUSE_ID) ?: ""
        req = SoleUserReq()
        initView()
    }

    private fun initView() {
        Bus.observe<SearchHouseEvent>()
            .subscribe {
                setOwnerInfo(it)
            }.registerInBus(this)

        tvSoleUsersSetting.onClick {
            if (checkInput()) {

                req?.houseId = houseId
                req?.soleCode = soleCode.getContent()
                req?.soleTotalPrice = soleTotalPrice.getContent()
                mPresenter.postSole(req)
            }
        }
        soleUser.onClick {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        startTime.onClick {
            showStartTimeDialog()
        }
        endTime.onClick {
            showEndTimeDialog()
        }
    }

    private fun showEndTimeDialog() {
        endTimePicker =
            DateTimePicker(activity, DateTimePicker.YEAR_MONTH_DAY, DateTimePicker.HOUR_24)
        val dateStr = DateUtils.getCurDateStr(DateUtils.FORMAT_SHORT)
        var weekStr = DateUtils.getChineseWeek(dateStr, DateUtils.FORMAT_SHORT)
        endTimePicker?.setTitleText("${DateUtils.getCurYear()} (${weekStr})")
        endTimePicker?.setOnDateTimePickListener(object :
            DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                val end_time = "$year-$month-$day $hour:$minute:00"
                req?.endTime = end_time
                endTime.setContent("$year-$month-$day $hour:$minute")
            }

        })
        endTimePicker?.setResetWhileWheel(false)
        endTimePicker?.setDateRangeStart(
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
        endTimePicker?.setDateRangeEnd(year + 4, month, day)
        endTimePicker?.setSelectedItem(
            DateUtils.getCurYear(),
            DateUtils.getCurMonth(),
            DateUtils.getCurDay(),
            DateUtils.getCurHour(),
            DateUtils.getCurMinute()
        )
        endTimePicker?.show()
    }

    private fun showStartTimeDialog() {
        startTimePicker =
            DateTimePicker(activity, DateTimePicker.YEAR_MONTH_DAY, DateTimePicker.HOUR_24)
        val dateStr = DateUtils.getCurDateStr(DateUtils.FORMAT_SHORT)
        var weekStr = DateUtils.getChineseWeek(dateStr, DateUtils.FORMAT_SHORT)
        startTimePicker?.setTitleText("${DateUtils.getCurYear()} (${weekStr})")
        startTimePicker?.setOnDateTimePickListener(object :
            DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                val start_time = "$year-$month-$day $hour:$minute:00"
                req?.startTime = start_time
                startTime.setContent("$year-$month-$day $hour:$minute")
            }

        })
        startTimePicker?.setResetWhileWheel(false)
        startTimePicker?.setDateRangeStart(
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
        startTimePicker?.setDateRangeEnd(year + 4, month, day)
        startTimePicker?.setSelectedItem(
            DateUtils.getCurYear(),
            DateUtils.getCurMonth(),
            DateUtils.getCurDay(),
            DateUtils.getCurHour(),
            DateUtils.getCurMinute()
        )
        startTimePicker?.show()
    }

    private fun setOwnerInfo(it: SearchHouseEvent?) {
        soleUser?.setContent(it?.name ?: "")
        req?.soleUser = it?.id
    }

    private fun checkInput(): Boolean {
        var childCount = llsoleUser.childCount
        for (i in 0 until childCount) {
            var childView = llsoleUser.getChildAt(i)
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
        return true
    }

    override fun onPostSoleResult(t: SoleUserRep?) {
        toast("设置成功")
        Bus.send(OwnerInfoPutEvent())
        activity?.finish()
    }

    override fun onDestroy() {
        try {
            startTimePicker?.dismiss()
            endTimePicker?.dismiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
