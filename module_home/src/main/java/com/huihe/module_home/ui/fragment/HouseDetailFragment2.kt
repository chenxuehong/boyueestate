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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cn.qqtheme.framework.picker.SinglePicker
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.example.zhouwei.library.CustomPopWindow
import com.google.gson.Gson
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.*
import com.huihe.module_home.ext.getConvertHouseDetailData
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseDetailPresenter
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.ui.activity.*
import com.huihe.module_home.ui.adapter.HouseDetailFragmentAdapter
import com.huihe.module_home.ui.adapter.ImageAdapter
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
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.OwnerInfoPutEvent
import com.kotlin.provider.event.ShareEvent
import com.kotlin.provider.utils.UserPrefsUtils
import com.qiniu.android.storage.UploadManager
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RectangleIndicator
import kotlinx.android.synthetic.main.fragment_house_detail.*
import kotlinx.android.synthetic.main.layout_house_basic_info_item.*
import kotlinx.android.synthetic.main.layout_house_detail_banner_item.*
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
class HouseDetailFragment2 : BaseTakePhotoFragment<HouseDetailPresenter>(), HouseDetailView{

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
    var banner: Banner<HouseDetail.ImagUrlsBean, ImageAdapter>? = null
    var mMorePopWindow: CustomPopWindow? = null
    var mShareCustomPopWindow: CustomPopWindow? = null
    var mCallPopWindow: CustomPopWindow? = null

    val titles = mutableListOf<String>("跟进","基本资料","相关人","图片")

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
        var title = "${houseDetail?.villageInfoResponse?.name ?: ""}"
        var content = "推荐好房"
        var imagePath = ""
        var imgUrl = getImgUrl()
        var shareUrl = getShareUrl()
        contentView.pop_dialog_share_fl_Wechat.onClick {
            if (mShareCustomPopWindow != null) {
                mShareCustomPopWindow?.dissmiss()
            }
            Bus.send(
                ShareEvent(
                    0,
                    title,
                    content,
                    imagePath,
                    imgUrl,
                    shareUrl,
                    ""
                )
            )
        }
        contentView.pop_dialog_share_fl_wechatmoments.onClick {
            if (mShareCustomPopWindow != null)
                mShareCustomPopWindow?.dissmiss()
            Bus.send(
                ShareEvent(
                    1,
                    title,
                    content,
                    imagePath,
                    imgUrl,
                    shareUrl,
                    ""
                )
            )
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

    private fun getShareUrl(): String? {
        var userInfo = UserPrefsUtils.getUserInfo()
        return String.format(resources.getString(R.string.houseDetailURL),
            BaseConstant.HouseDetail_BASE_URL,
            houseDetail?.id,
            userInfo?.uid?:"",
            BaseConstant.ip
        )
    }

    private fun getImgUrl():String {
        if (houseDetail != null) {
            var imagUrls = houseDetail?.imagUrls ?: mutableListOf()
            var imgUrl = ""
            if (imagUrls.size > 0) {
                imgUrl = imagUrls[0].url ?: ""
            }
            return imgUrl
        }
        return ""
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
                    circulation = if (item == resources.getString(R.string.Circulate)) {
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
        toast("上传成功")
    }

    private fun initData() {
        house_detail_mMultiStateView?.startLoading()
        mPresenter?.getHouseDetailById(id)
    }

    override fun onGetHouseDetailResult(houseDetail: HouseDetail?) {
        this.houseDetail = houseDetail
        houseDetail?.ownerInfo = this.ownerInfo
        var data: MutableList<ItemHouseDetail> = getConvertHouseDetailData(houseDetail)
        bindBanner(data[0].bannerList)
        bindBasicInfo(data[1].basicInfo)
        initRvAdapter(data[2])
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

    private fun bindBanner(bannerList: MutableList<HouseDetail.ImagUrlsBean>?) {
        banner = houseDetailBanner as Banner<HouseDetail.ImagUrlsBean, ImageAdapter>?
        banner?.apply {
            adapter = ImageAdapter(context!!, bannerList)
            indicator = RectangleIndicator(context)
            setIndicatorSelectedColorRes(R.color.white)
            setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            setBannerGalleryMZ(
              resources.getDimensionPixelOffset(R.dimen.dp_6),
                0.8f
            )

            setOnBannerListener { data, position ->
               onViewPhoto(
                    (data as HouseDetail.ImagUrlsBean).url ?: "",
                    getPhotoList(bannerList?: mutableListOf()),
                    position,
                   banner!!
                )
            }

            setBannerRound(resources.getDimension(R.dimen.dp_10))
        }
    }

    private fun bindBasicInfo(basicInfo: ItemHouseDetail.BasicInfo?) {
        tvHouseDetailTitle.text =
            "${basicInfo?.villageName}-${basicInfo?.building}-${basicInfo?.hNum}"
        tvHouseDetailPrice.text = "${basicInfo?.price}万"
        var amount =  basicInfo?.floorage?.toBigDecimal()
            ?.let { basicInfo?.price?.div(it) }
        var argePrice = YuanFenConverter.getRoundFloor(amount)
        tvHouseDetailArgePrice.text = "${argePrice}万/m²"
        tvHouseDetailFloorageValue.text = "${basicInfo?.floorage}m²"
        tvHouseDetailHShapeValue.text = "${basicInfo?.hShape}"
        var split = basicInfo?.label?.split(";")
        var toMutableList = split?.toMutableList()
        if (basicInfo?.isCirculation ==1){
            toMutableList?.add("流通")
            split  = toMutableList?.toList()
        }
        houseDetailTags.tags =
            if (split != null && split.size > 5) split?.subList(0, 4) else getNotNullData(
                split
            )

        tvHouseDetailTel.onClick {
           onShowTelListDialog(id,tvHouseDetailTel)
        }
        tvHouseDetailFollow.onClick {
            startActivity<AddFollowActivity>(HomeConstant.KEY_HOUSE_ID to id)
        }
        tvHouseDetailTakeLook.onClick {
            startActivity<HouseTakeLookRecordActivity>(HomeConstant.KEY_CODE to houseDetail?.houseCode)
        }
        tvHouseDetailLog.onClick {
            startActivity<HouseLogHomeActivity>(HomeConstant.KEY_HOUSE_CODE to houseDetail?.houseCode)
        }
    }

    private fun initRvAdapter(itemHouseDetail: ItemHouseDetail) {
        houseDetailItemTabLayout.setupWithViewPager(houseDetailItemViewpager)
        val fragments = mutableListOf<Fragment>()
        // 跟进
        var houseFollowFragment = HouseFollowFragment()
        val args = Bundle()
        args.putString(HomeConstant.KEY_HOUSE_CODE,houseDetail?.houseCode)
        args.putString(HomeConstant.KEY_HOUSE_ID,id)
        houseFollowFragment.arguments = args
        fragments.add(houseFollowFragment)
        // 基础资料
        var houseBasicInfoFragment = HouseBasicInfoFragment()
        val houseBasicInfoArgs = Bundle()
        houseBasicInfoArgs.putString(HomeConstant.KEY_HOUSE_DETAIL,Gson().toJson(itemHouseDetail))
        houseBasicInfoFragment.arguments = houseBasicInfoArgs
        fragments.add(houseBasicInfoFragment)
        // 相关人
        var houseOwnerInfoFragment = HouseOwnerInfoFragment()
        val houseOwnerInfoArgs = Bundle()
        houseOwnerInfoArgs.putString(HomeConstant.KEY_HOUSE_DETAIL,Gson().toJson(itemHouseDetail))
        houseOwnerInfoArgs.putString(HomeConstant.KEY_HOUSE_ID,id)
        houseOwnerInfoFragment.arguments = houseOwnerInfoArgs
        fragments.add(houseOwnerInfoFragment)
        // 图片
        var housePhotoInfoFragment = HousePhotoInfoFragment()
        val housePhotoInfoArgs = Bundle()
        housePhotoInfoArgs.putString(HomeConstant.KEY_HOUSE_DETAIL,Gson().toJson(itemHouseDetail))
        housePhotoInfoFragment.arguments = housePhotoInfoArgs
        fragments.add(housePhotoInfoFragment)

        houseDetailItemViewpager.adapter = HouseDetailFragmentAdapter(childFragmentManager,titles,fragments)
    }

    private fun getNotNullData(split: List<String>?): List<String> {
        return split ?: mutableListOf()
    }

    var photoList = mutableListOf<String>()
    private fun getPhotoList(dataList: MutableList<HouseDetail.ImagUrlsBean>): List<String> {
        photoList?.clear()
        dataList.forEach { item ->
            photoList.add(item.url ?: "")
        }
        return photoList
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
        try {
            banner?.start()
        } catch (e: Exception) {
        }
        super.onStart()
    }

    override fun onStop() {
        try {
            banner?.stop()
        } catch (e: Exception) {
        }
        super.onStop()
    }

    override fun onDestroy() {
        try {
            banner?.destroy()
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

    override fun onHouseCreateUserResult(t: HouseCreateUserRep?) {
        mPresenter.getHouseDetailById(id)
        toast("设置成功")
    }

    override fun onPutCappingResult(t: CappingRep?) {
        mPresenter.getHouseDetailById(id)
        toast("设置成功")
    }

    fun onViewPhoto(
        photo: String,
        photoList: List<String>,
        position: Int,
        itemView: View
    ) {
        itemView.viewPhoto(context, position, photoList)
    }

    lateinit var tvTel: TextView
    fun onShowTelListDialog(houseId: String?, tvTel: TextView) {
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
