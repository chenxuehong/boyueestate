package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HaveKeyUserRep
import com.huihe.module_home.data.protocol.HaveKeyUserReq
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HaveKeyUserPresenter
import com.huihe.module_home.presenter.view.HaveKeyUserView
import com.huihe.module_home.ui.adpter.PhotoRvAdapter
import com.jph.takephoto.model.TResult
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.fragment.BaseTakePhotoFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.event.OwnerInfoPutEvent
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.fragment_havekeyuser.*

class HaveKeyUserFragment : BaseTakePhotoFragment<HaveKeyUserPresenter>(), HaveKeyUserView {

    var houseId: String? = null
    lateinit var req: HaveKeyUserReq
    var mUploadManager:UploadManager?=null
    var mLocalFilResult: TResult? = null
    lateinit var mPhotoRvAdapter:PhotoRvAdapter
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
        initUpdateManager()
        initPhotoRvAdapter()
        initView()
    }

    private fun initUpdateManager() {
        mUploadManager = UploadManager()
    }

    private fun initPhotoRvAdapter() {
        rvHavekeyUser.isNestedScrollingEnabled = false
        rvHavekeyUser.vertical(3,DensityUtils.dp2px(context,15f))
        mPhotoRvAdapter = PhotoRvAdapter(context!!)
        rvHavekeyUser.adapter = mPhotoRvAdapter
    }

    fun addPhoto(photo:String){
        if (mPhotoRvAdapter!=null){
            mPhotoRvAdapter.addData(photo)
        }
    }

    fun getRemoveImgUrlList():MutableList<String?>{
        if (mPhotoRvAdapter!=null){
            return mPhotoRvAdapter.dataList
        }
        return mutableListOf()
    }

    private fun initView() {

        haveKeyUser.onClick {

        }
        haveKeyTime.onClick {

        }
        keyImage.onClick {
            showAlertView()
        }
        tvHaveKeyuserSetting.onClick {
            req.keyCode = keyCode.getContent()
            req.keyPassword = keyPassword.getContent()
            req.receipt = receipt.getContent()

            mPresenter.putHouseKey(req)
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

                req.keyImage = mRemoteFileUrl
                LogUtils.d("test", mRemoteFileUrl)
                addPhoto(mRemoteFileUrl)
            }, null
        )
    }

    override fun onPutHouseKeySuccess(t: HaveKeyUserRep?) {
        Bus.send(OwnerInfoPutEvent())
    }
}