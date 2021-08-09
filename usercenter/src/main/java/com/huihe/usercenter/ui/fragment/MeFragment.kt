package com.huihe.usercenter.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.darsh.multipleimageselect.helpers.Constants
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.MeItemBean
import com.huihe.usercenter.data.protocol.SetUserInfoReq
import com.huihe.usercenter.data.protocol.UserInfo
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MePresenter
import com.huihe.usercenter.presenter.view.MeView
import com.huihe.usercenter.ui.activity.*
import com.huihe.usercenter.ui.adapter.MeGroupRvAdapter
import com.jph.takephoto.model.TResult
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.loadHeadUrl
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.fragment.BaseTakePhotoFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.router.RouterPath
import com.qiniu.android.storage.UploadManager
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MeFragment : BaseTakePhotoFragment<MePresenter>(), MeView,
    MeGroupRvAdapter.OnChildItemClickListener {

    var mLocalFilResult: TResult? = null
    var mUploadManager: UploadManager? = null
    var meContentRvAdapter : MeGroupRvAdapter? = null
    lateinit var setUserInfoReq:SetUserInfoReq
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_me, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        flMeScan.onClick {
            requestScan{
                val intent = Intent(context, CaptureActivity::class.java)
                startActivityForResult(intent, Constants.REQUEST_CODE)
            }
        }
        clMeUserInfo.onClick {
            showAlertView(true)
        }
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

                setUserInfoReq.avatar = mRemoteFileUrl
                LogUtils.d("test", mRemoteFileUrl)
                mPresenter.setUserInfo(setUserInfoReq)

            }, null
        )
    }

    override fun onSetUserInfo() {
        refreshUserInfo()
    }

    private fun initData() {
        initUpdateManager()
        setUserInfoReq = SetUserInfoReq()
        refreshUserInfo()
        initListData()
    }

    private fun initUpdateManager() {
        mUploadManager = UploadManager()
    }

    private fun refreshUserInfo() {
        mPresenter?.getUserInfo()
    }

    override fun onUserInfo(t: UserInfo?) {
        ivMeHead.loadHeadUrl(t?.avatarUrl ?: "")
        tvMeUserName.text = t?.userName ?: ""
        tvMeDeptName.text = t?.deptName ?: ""
    }

    private fun initListData() {
        rvMeContent.vertical(1,DensityUtils.dp2px(context,15f))
        meContentRvAdapter = MeGroupRvAdapter(context!!)
        meContentRvAdapter?.setOnChildItemClickListener(this)
        rvMeContent.adapter = meContentRvAdapter
        meContentRvAdapter?.init()
        mPresenter.getLookTaskStatic(0)
    }

    override fun onItemClick(view: View, item: MeItemBean.ItemData, position: Int) {
        when (item.title) {
            resources.getString(R.string.to_start),resources.getString(R.string.take_look),
            resources.getString(R.string.in_summary),resources.getString(R.string.under_review) -> {
                // 待开始(status=0),带看中(status=1),总结中(status=2),审核中(status=3),已完成(status=4)
                startActivity<MineLookTaskHomeActivity>(BaseConstant.KEY_STATUS to getStatus(item.title))
            }

            resources.getString(R.string.Store_data),
            resources.getString(R.string.Department_data),
            resources.getString(R.string.Employee_data) -> {
                startActivity<BehaviourActivity>(UserConstant.KEY_TITLE to item.title)
            }

            resources.getString(R.string.area_manager) -> {
                startActivity<CommunityManagerActivity>()
            }
            resources.getString(R.string.district_manager) -> {
                startActivity<DistrictActivity>()
            }
            resources.getString(R.string.address_book) -> {
                startActivity<AddressBookActivity>()
            }
            resources.getString(R.string.corporate_culture) -> {
                startActivity<CorporateCultureActivity>()
            }
            resources.getString(R.string.setting) -> {
                startActivity<SettingActivity>()
            }
        }
    }

    private fun getStatus(title: String): Int {
        var status = 0
        when (title){
            resources.getString(R.string.to_start)->{
                status =0
            }
            resources.getString(R.string.take_look)->{
                status =1
            }
            resources.getString(R.string.in_summary)->{
                status =2
            }resources.getString(R.string.under_review)->{
             status =3
            }
        }
        return status
    }

    override fun onLookTaskStatic(
        to_start: Int,
        take_look: Int,
        in_summary: Int,
        under_review: Int
    ) {
        meContentRvAdapter?.setTakeLookCount(to_start,take_look,in_summary,under_review)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                var bundle: Bundle? = data.extras ?: return
                if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    var result = bundle.getString(CodeUtils.RESULT_STRING);
//                    toast("解析结果:${result}")
                    result?.apply {
                        ARouter.getInstance()
                            .build(RouterPath.HomeCenter.PATH_HOUSE_DETIL)
                            .withString(HomeConstant.KEY_HOUSE_ID, this)
                            .navigation()
                    }
                } else if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    toast("解析二维码失败")
                }
            }
        }
    }

    override fun onDestroy() {
        meContentRvAdapter?.onDestory()
        super.onDestroy()
    }
}
