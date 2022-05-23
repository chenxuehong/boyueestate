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
import com.huihe.module_home.data.protocol.HaveKeyUserRep
import com.huihe.module_home.data.protocol.HaveKeyUserReq
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HaveKeyUserPresenter
import com.huihe.module_home.presenter.view.HaveKeyUserView
import com.huihe.module_home.ui.adapter.PhotoRvAdapter
import com.jph.takephoto.model.TResult
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.fragment.BaseTakePhotoFragment
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.DensityUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.base.widgets.NecessaryTitleInputView
import com.kotlin.base.widgets.NecessaryTitleSelectView
import com.kotlin.base.widgets.picker.WheelPicker.picker.DateTimePicker
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.OwnerInfoPutEvent
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.router.RouterPath
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.fragment_havekeyuser.*
import org.jetbrains.anko.support.v4.toast

class HaveKeyUserFragment : BaseTakePhotoFragment<HaveKeyUserPresenter>(), HaveKeyUserView {

    var houseId: String? = null
    lateinit var req: HaveKeyUserReq
    var mUploadManager: UploadManager? = null
    var mLocalFilResult: TResult? = null
    lateinit var mPhotoRvAdapter: PhotoRvAdapter
    var beginTimePicker: DateTimePicker? = null
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
        return initInflater(context!!, R.layout.fragment_havekeyuser, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseId = arguments?.getString(HomeConstant.KEY_HOUSE_ID) ?: ""
        req = HaveKeyUserReq()
        req.houseId = houseId
        initUpdateManager()
        initPhotoRvAdapter()
        initView()
    }

    private fun initUpdateManager() {
        mUploadManager = UploadManager()
    }

    private fun initPhotoRvAdapter() {
        rvHavekeyUser.isNestedScrollingEnabled = false
        rvHavekeyUser.vertical(3, DensityUtils.dp2px(context, 15f))
        mPhotoRvAdapter = PhotoRvAdapter(context!!)
        rvHavekeyUser.adapter = mPhotoRvAdapter
    }

    fun addPhoto(photo: String) {
        if (mPhotoRvAdapter != null) {
            mPhotoRvAdapter.addData(photo)
        }
    }

    fun getRemoveImgUrlList(): MutableList<String?> {
        if (mPhotoRvAdapter != null) {
            return mPhotoRvAdapter.dataList
        }
        return mutableListOf()
    }

    private fun initView() {
        Bus.observe<SearchHouseEvent>()
            .subscribe {
                setOwnerInfo(it)
            }.registerInBus(this)
        haveKeyUser.onClick {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                .withBoolean(BaseConstant.KEY_ISSELECT, true)
                .navigation(context)
        }
        haveKeyTime.onClick {
            showKeyTimeDialog()
        }
        keyImage.onClick {
            showAlertView()
        }
        tvHaveKeyuserSetting.onClick {
            if (checkInput()){
                req.keyCode = keyCode.getContent()
                req.keyPassword = keyPassword.getContent()
                req.receipt = receipt.getContent()

                mPresenter.putHouseKey(req)
            }
        }
    }

    private fun checkInput(): Boolean {
        var childCount = llhaveKeyUser.childCount
        for (i in 0 until childCount) {
            var childView = llhaveKeyUser.getChildAt(i)
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

    private fun showKeyTimeDialog() {
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
                req.haveKeyTime = start_time
                haveKeyTime.setContent("$year-$month-$day $hour:$minute")
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

    private fun setOwnerInfo(it: SearchHouseEvent?) {
        haveKeyUser?.setContent(it?.name ?: "")
        req?.haveKeyUser = it?.id
    }

    override fun takeSuccess(result: TResult?) {
        super.takeSuccess(result)
        this.mLocalFilResult = result
        mPresenter.getUploadToken()
    }

    override fun onGetUploadTokenResult(result: String?) {
        mUploadManager?.put(
            mLocalFilResult?.image?.originalPath, null, result,
            { key, info, response ->
                var mRemoteFileUrl = response?.get("hash") as String

                req.keyImage = mRemoteFileUrl
                LogUtils.d("test", mRemoteFileUrl)
                addPhoto(mLocalFilResult?.image?.originalPath ?: "")
            }, null
        )
    }

    override fun onPutHouseKeySuccess(t: HaveKeyUserRep?) {
        toast("设置成功")
        Bus.send(OwnerInfoPutEvent())
        activity?.finish()
    }

    override fun onDestroy() {
        try {
            beginTimePicker?.dismiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}