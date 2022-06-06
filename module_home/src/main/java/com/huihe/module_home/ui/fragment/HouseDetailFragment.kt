package com.huihe.module_home.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.example.zhouwei.library.CustomPopWindow
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.*
import com.huihe.module_home.ext.getConvertHouseDetailData
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseDetailPresenter
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.ui.activity.*
import com.huihe.module_home.ui.adapter.HouseDetailRvAdapter
import com.huihe.module_home.ui.adapter.MoreRvAdapter
import com.huihe.module_home.ui.adapter.TelRvAdapter
import com.jph.takephoto.model.TResult
import com.kennyc.view.MultiStateView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ext.viewPhoto
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseTakePhotoFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.base.widgets.picker.WheelPicker.picker.SinglePicker
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.OwnerInfoPutEvent
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.event.ShareEvent
import com.kotlin.provider.router.RouterPath
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.fragment_house_detail.*
import kotlinx.android.synthetic.main.layout_right_title_house_detail.*
import kotlinx.android.synthetic.main.layout_tel_dialog.view.*
import kotlinx.android.synthetic.main.pop_dialog_share.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

/**
 * 房源详情
 */
class HouseDetailFragment : BaseTakePhotoFragment<HouseDetailPresenter>(), HouseDetailView,
    HouseDetailRvAdapter.OnListener {

    lateinit var houseDetailTvAdapter: HouseDetailRvAdapter
    var mLocalFilResult: TResult? = null
    var id: String? = null
    var houseDetail: HouseDetail? = null
    var ownerInfo: OwnerInfo? = null
    var rightContentView: View? = null
    var isCollect = false
    var requestCollecting = true
    var ivCollection: ImageView? = null
    var mMoreList: MutableList<String>? = null
    var updateStatusPicker: SinglePicker<String>? = null
    var mCirculatePicker: SinglePicker<String>? = null
    val request_code_get_house_picture: Int = 100
    val request_code_get_refer_picture: Int = 101
    var REQUEST_CODE_PUTHOUSE_INFO: Int = 102
    var requestCode: Int = request_code_get_house_picture
    var imageUser: String? = null
    lateinit var mUploadManager: UploadManager

    var mMorePopWindow: CustomPopWindow? = null
    var mShareCustomPopWindow: CustomPopWindow? = null
    var mCallPopWindow: CustomPopWindow? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_detail, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString(HomeConstant.KEY_HOUSE_ID)
        initView()
        initRvHouseDetailAdapter()
        initListener()
        initData()
    }

    private fun initView() {
        rightContentView = house_detail_titleBar.getRightContentView()
        ivCollection = rightContentView?.findViewById<ImageView>(R.id.ivHouseDetailStar)
        requestCollecting = false
        mMoreList = mutableListOf(
            context!!.resources.getString(R.string.more_update),
            context!!.resources.getString(R.string.more_house_photo),
            context!!.resources.getString(R.string.more_refers),
            context!!.resources.getString(R.string.more_update_status),
            context!!.resources.getString(R.string.more_circulate),
            context!!.resources.getString(R.string.more_edit_info),
            context!!.resources.getString(R.string.more_new_phone),
            context!!.resources.getString(R.string.more_customer),
            context!!.resources.getString(R.string.more_share)
        )
        mUploadManager = UploadManager()
    }

    private fun initRvHouseDetailAdapter() {
//        house_detail_rvList.layoutManager = LinearLayoutManager(context)
//        houseDetailTvAdapter = HouseDetailRvAdapter(context, this)
//        houseDetailTvAdapter.setRecyclerview(house_detail_rvList)
//        house_detail_rvList.adapter = houseDetailTvAdapter
    }

    private fun initListener() {
        rightContentView.apply {
            ivHouseDetailStar.onClick {
                if (!requestCollecting)
                    if (isCollect) {
                        mPresenter?.reqDeleteCollection(id)
                    } else {
                        mPresenter?.reqCollection(id)
                    }
            }
            ivHouseDetailMore.onClick {
                showMoreDialog(ivHouseDetailMore)
            }
            ivHouseDetailMore.isEnabled = false
        }
        Bus.observe<OwnerInfoPutEvent>().subscribe {
            mPresenter?.getHouseDetailById(id)
        }.registerInBus(this)

        Bus.observe<SearchHouseEvent>()
            .subscribe {
                setOwnerInfo(it)
            }.registerInBus(this)
    }

    private fun showMoreDialog(ivHouseDetailMore: TextView) {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_tel_dialog, null, false)
        var moreRvAdapter = MoreRvAdapter(context!!)
        moreRvAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(view: View, item: String, position: Int) {
                doItemClicked(item)
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

    private fun doItemClicked(item: String) {
        when (item) {
            context!!.resources.getString(R.string.more_update) -> {
                val intent = Intent(context, SetHouseInfoActivity::class.java)
                intent.putExtra(HomeConstant.KEY_HOUSE_DETAIL, Gson().toJson(houseDetail))
                startActivityForResult(intent, REQUEST_CODE_PUTHOUSE_INFO)
            }
            context!!.resources.getString(R.string.more_house_photo) -> {
                requestCode = request_code_get_house_picture
                showSetImageUerDialog()
            }
            context!!.resources.getString(R.string.more_refers) -> {
                requestCode = request_code_get_refer_picture
                showAlertView()
            }
            context!!.resources.getString(R.string.more_update_status) -> {
                showUpdateStatusDialog()
            }
            context!!.resources.getString(R.string.more_circulate) -> {
                showCirculateSelectDialog()
            }
            context!!.resources.getString(R.string.more_edit_info) -> {
                startActivity<SetOwnerInfoActivity>(
                    HomeConstant.KEY_HOUSE_ID to id,
                    HomeConstant.KEY_OWNER_NAME to houseDetail?.ownerName
                )
            }
            context!!.resources.getString(R.string.more_new_phone) -> {
                showNewPhoneDialog()
            }
            context!!.resources.getString(R.string.more_customer) -> {
                startActivity<CustomerProfileActivity>(HomeConstant.KEY_HOUSE_ID to id)
            }
            context!!.resources.getString(R.string.more_share) -> {

                showShare()
            }
        }
        mMorePopWindow?.dissmiss()
    }

    private fun showShare() {
        var decorView = activity?.window?.decorView
        val contentView = View.inflate(context, R.layout.pop_dialog_share, null)
        contentView.pop_dialog_share_fl_Wechat.onClick {
            if (mShareCustomPopWindow != null) {
                mShareCustomPopWindow?.dissmiss()
            }
            if (houseDetail != null) {
                var imagUrls = houseDetail?.imagUrls ?: mutableListOf()
                var imgUrl = ""
                if (imagUrls.size > 0) {
                    imgUrl = imagUrls[0].url ?: ""
                }
                Bus.send(
                    ShareEvent(
                        0,
                        "${houseDetail?.villageInfoResponse?.name ?: ""}",
                        "推荐好房",
                        "",
                        imgUrl,
                        "http://billion.housevip.cn/#/house/${houseDetail?.id ?: ""}/uid/1/ip/1",
                        ""
                    )
                )
            }
        }
        contentView.pop_dialog_share_fl_wechatmoments.onClick {
            if (mShareCustomPopWindow != null)
                mShareCustomPopWindow?.dissmiss()
            if (houseDetail != null) {
                var imagUrls = houseDetail?.imagUrls ?: mutableListOf()
                var imgUrl = ""
                if (imagUrls.size > 0) {
                    imgUrl = imagUrls[0].url ?: ""
                }
                Bus.send(
                    ShareEvent(
                        1,
                        "${houseDetail?.villageInfoResponse?.name ?: ""}-${houseDetail?.building
                            ?: ""}-${houseDetail?.hNum ?: ""}",
                        "",
                        "",
                        imgUrl,
                        "http://billion.housevip.cn/#/house/${houseDetail?.id ?: ""}/uid/1/ip/1",
                        ""
                    )
                )
            }
        }
        contentView.pop_dialog_share_tv_cancel.onClick {
            if (mShareCustomPopWindow != null)
                mShareCustomPopWindow?.dissmiss()
        }
        mShareCustomPopWindow = CustomPopWindow.PopupWindowBuilder(context)
            .setView(contentView)
            .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
            .setBgDarkAlpha(0.7f) // 控制亮度
            .setFocusable(true)
            .setOutsideTouchable(true)
            .create().showAtLocation(decorView, Gravity.BOTTOM, 0, 0)

    }

    private fun showSetImageUerDialog() {
        val mLDialog = LDialog.init(childFragmentManager)
            .setLayoutRes(R.layout.layout_set_imageuser_dialog)
            .setBackgroundDrawableRes(R.drawable.new_phone_dialog_bg)
            .setGravity(Gravity.CENTER)
            .setWidthScale(0.75f)
            .setViewHandlerListener(object : ViewHandlerListener() {
                override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                    holder.getView<TextView>(R.id.tvSetImageUserYes).onClick {
                        dialog.dismiss()
                        imageUser = "1"
                        showAlertView()
                    }
                    holder.getView<TextView>(R.id.tvSetImageUserNo).onClick {
                        imageUser = "0"
                        showAlertView()
                        dialog.dismiss()

                    }
                    holder.getView<ImageView>(R.id.ivSetImageUserClose).onClick {
                        dialog.dismiss()
                    }
                }
            }).show()

    }

    private fun showNewPhoneDialog() {
        LDialog.init(childFragmentManager)
            .setLayoutRes(R.layout.layout_phone_dialog)
            .setBackgroundDrawableRes(R.drawable.new_phone_dialog_bg)
            .setGravity(Gravity.CENTER)
            .setWidthScale(0.75f)
            .setVerticalMargin(0.015f)
//            .setAnimStyle(R.style.LDialogBottomAnimation)
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
                        mPresenter?.setHouseInfo(
                            SetHouseInfoReq(
                                id = id,
                                ownerTel = tel
                            )
                        )
                    }
                }
            })
            .show()
    }

    private fun showCirculateSelectDialog() {
        var data = mutableListOf<String>()
        data.add(resources.getString(R.string.Circulate))
        data.add(resources.getString(R.string.NoCirculate))
        mCirculatePicker = SinglePicker(activity, data);
        mCirculatePicker?.setCanceledOnTouchOutside(true)
        mCirculatePicker?.selectedIndex = 1
        mCirculatePicker?.setCycleDisable(true)
        mCirculatePicker?.setOnItemPickListener { index, item ->
            mPresenter?.setHouseInfo(
                SetHouseInfoReq(
                    id,
                    isCirculation = if (item == resources.getString(R.string.Circulate)) {
                        1
                    } else {
                        0
                    }
                )

            )
        };
        mCirculatePicker?.show()
    }

    private fun showUpdateStatusDialog() {
        var data = mutableListOf<String>()
        data.add(resources.getString(R.string.heSold))
        data.add(resources.getString(R.string.selfSold))
        data.add(resources.getString(R.string.effective))
        data.add(resources.getString(R.string.defer))
        updateStatusPicker = SinglePicker(activity, data);
        updateStatusPicker?.setCanceledOnTouchOutside(true)
        updateStatusPicker?.selectedIndex = 1
        updateStatusPicker?.setCycleDisable(true)
        updateStatusPicker?.setOnItemPickListener { index, item ->
            // 有效0 我售 2 他售 3 暂缓 1
            mPresenter?.setHouseInfo(
                SetHouseInfoReq(
                    id = id,
                    hFlag = getHFlag(item)
                )

            )
        };
        updateStatusPicker?.show()
    }

    private fun getHFlag(item: String): Int? {
        return when (item) {
            resources.getString(R.string.heSold) -> {
                3
            }
            resources.getString(R.string.selfSold) -> {
                2
            }
            resources.getString(R.string.effective) -> {
                0
            }
            else -> {
                1
            }
        }
    }

    override fun onHouseStatus(t: SetHouseInfoRep?) {
        try {
            mCirculatePicker?.dismiss()
            updateStatusPicker?.dismiss()
        } catch (e: Exception) {
        }
        toast("设置成功")
        mPresenter.getHouseDetailById(id)
    }

    override fun onGetUploadTokenResult(result: String?) {
        mUploadManager.put(
            mLocalFilResult?.image?.originalPath, null, result,
            { key, info, response ->
                var mRemoteFileUrl = response?.get("hash") as String

                LogUtils.d("test", mRemoteFileUrl)
                if (requestCode == request_code_get_house_picture) {
                    mPresenter.setHouseInfo(
                        SetHouseInfoReq(id = id, imageUser = imageUser)
                    )
                    mPresenter.postHouseImage(id = id, imagUrl = mRemoteFileUrl)
                } else if (requestCode == request_code_get_refer_picture) {
                    mPresenter.postReferImage(
                        id = id,
                        referUrl = mRemoteFileUrl,
                        houseCode = houseDetail?.houseCode
                    )
                }
            }, null
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_PUTHOUSE_INFO == requestCode) {
            mPresenter?.getHouseDetailById(id)
        }
    }

    override fun onUploadSuccessResult(result: String?) {

    }

    private fun initData() {
        house_detail_mMultiStateView?.startLoading()
        mPresenter?.getHouseDetailById(id)
    }

    override fun onGetHouseDetailResult(houseDetail: HouseDetail?) {
        this.houseDetail = houseDetail
        houseDetail?.ownerInfo = this.ownerInfo
        var data: MutableList<ItemHouseDetail> = getConvertHouseDetailData(houseDetail)
        houseDetailTvAdapter.setId(houseDetail?.id)
        houseDetailTvAdapter?.setData(data)
        var collect = houseDetail?.isCollect
        isCollect = !(collect == null || collect == 0)
        ivCollection?.setBackgroundResource(
            if (isCollect) {
                R.drawable.star_checked
            } else {
                R.drawable.star_common
            }
        )
        house_detail_mMultiStateView?.viewState =
            MultiStateView.VIEW_STATE_CONTENT
        requestCollecting = false
        ivHouseDetailMore.isEnabled = true
    }

    override fun onGetOwnerResult(ownerInfo: OwnerInfo?) {
        this.ownerInfo = ownerInfo
    }

    override fun onError(text: String) {
        super.onError(text)
        requestCollecting = false
    }

    override fun onReqCollectionResult(isInsert: Boolean?) {
        isCollect = isInsert ?: false
        ivCollection?.setBackgroundResource(
            if (isCollect) {
                R.drawable.star_checked
            } else {
                R.drawable.star_common
            }
        )
    }

    override fun takeSuccess(result: TResult?) {
        super.takeSuccess(result)
        this.mLocalFilResult = result
        mPresenter.getUploadToken()
    }

    override fun onStart() {
        houseDetailTvAdapter?.onStart()
        super.onStart()
    }

    override fun onStop() {
        houseDetailTvAdapter?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        try {
            houseDetailTvAdapter?.onDestroy()
            Bus.unregister(this)
            mMorePopWindow?.dissmiss()
            updateStatusPicker?.dismiss()
            mCirculatePicker?.dismiss()
            mShareCustomPopWindow?.dissmiss()
            mCallPopWindow?.dissmiss()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    private fun getTel(tel: String?): String {
        var ownerTel = houseDetail?.ownerTel
        var sb = StringBuffer()
        if (!TextUtils.isEmpty(ownerTel)) {
            sb.append(ownerTel).append("*").append(tel)
        } else {
            sb.append(tel)
        }
        return sb.toString().trim()
    }

    override fun onResume() {
        houseDetailTvAdapter?.onResume()
        super.onResume()
    }

    override fun onPause() {
        houseDetailTvAdapter?.onPause()
        super.onPause()
    }

    var ownerType: Int = -1
    override fun onUserClicked(item: ItemHouseDetail.OwnerInfo) {
        ownerType = item.type
        if (TextUtils.isEmpty(item.uid)) {
            when (item.type) {
                CustomersModule.OwnerInfoType.maintainUser,
                CustomersModule.OwnerInfoType.imageUser,
                CustomersModule.OwnerInfoType.bargainPriceUser -> {
                    ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                        .withBoolean(BaseConstant.KEY_ISSELECT, true)
                        .navigation(context)
                }

                CustomersModule.OwnerInfoType.entrustUser -> {
                    startActivity<EntrustUserActivity>(HomeConstant.KEY_HOUSE_ID to houseDetail?.id)
                }
                CustomersModule.OwnerInfoType.haveKeyUser -> {
                    startActivity<HaveKeyUserActivity>(HomeConstant.KEY_HOUSE_ID to houseDetail?.id)
                }
                CustomersModule.OwnerInfoType.soleUser -> {
                    startActivity<SoleUserActivity>(HomeConstant.KEY_HOUSE_ID to houseDetail?.id)
                }
            }
        } else {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_DEPTINFO)
                .withString(UserConstant.KEY_USER_ID, item.uid)
                .navigation()
        }
    }

    private fun setOwnerInfo(it: SearchHouseEvent?) {
        when (ownerType) {
            CustomersModule.OwnerInfoType.createUser -> {
                mPresenter.pathHouseCreateUser(id, it?.id)
            }
            CustomersModule.OwnerInfoType.maintainUser -> {
                mPresenter?.setHouseInfo(
                    SetHouseInfoReq(
                        id = id,
                        maintainUser = it?.id
                    )
                )
            }
            CustomersModule.OwnerInfoType.imageUser -> {
                mPresenter?.setHouseInfo(
                    SetHouseInfoReq(
                        id = id,
                        imageUser = it?.id
                    )
                )
            }
            CustomersModule.OwnerInfoType.bargainPriceUser -> {
                mPresenter?.setHouseInfo(
                    SetHouseInfoReq(
                        id = id,
                        bargainPriceUser = it?.id
                    )
                )
            }
            CustomersModule.OwnerInfoType.blockUser -> {
                mPresenter.putCapping(CappingReq(id = id, blockUser = it?.id))
            }
        }
    }

    override fun onHouseCreateUserResult(t: HouseCreateUserRep?) {
        mPresenter.getHouseDetailById(id)
        toast("设置成功")
    }

    override fun onPutCappingResult(t: CappingRep?) {
        mPresenter.getHouseDetailById(id)
        toast("设置成功")
    }

    override fun onViewPhoto(
        photo: String,
        photoList: List<String>,
        position: Int,
        itemView: View
    ) {
        itemView.viewPhoto(context, position, photoList)
    }

    lateinit var tvTel: TextView
    override fun onShowTelListDialog(houseId: String?, tvTel: TextView) {
        this.tvTel = tvTel
        mPresenter.getHouseMobile(houseId)
    }

    override fun onShowTelListDialog(ownerTel: String?) {
        var split = ownerTel?.split("*") ?: mutableListOf()
        split = split?.toMutableList()
        if (split?.isEmpty()) {
            toast("暂无电话")
            return
        }
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_tel_dialog, null, false)
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
            split
        )
        mCallPopWindow = CustomPopWindow.PopupWindowBuilder(context)
            .setView(contentView)
            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
            .setBgDarkAlpha(0.7f) // 控制亮度
            .setFocusable(true)
            .setOutsideTouchable(true)
            .create()
            .showAsDropDown(tvTel, 0, 10)
    }

}
