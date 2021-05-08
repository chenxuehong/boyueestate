package com.huihe.module_home.presenter

import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.OwnerInfo
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.data.protocol.SetHouseInfoReq
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class HouseDetailPresenter @Inject constructor() : BasePresenter<HouseDetailView>() {

    @Inject
    lateinit var mHouseService: HouseService

    fun getHouseDetailById(id: String?) {
        if (!checkNetWork()) {
            return
        }
        mHouseService.getHouseDetailById(id)
            .compose(lifecycleProvider.bindToLifecycle())
            .flatMap(Function<HouseDetail?, Observable<OwnerInfo?>> {
                mView?.onGetHouseDetailResult(it)
                return@Function mHouseService.getHouseDetailRelationPeople(id)
            })
            .execute(object : BaseSubscriber<OwnerInfo?>(mView) {
                override fun onNext(t: OwnerInfo?) {
                    super.onNext(t)
                    mView?.onGetOwnerResult(t)
                }
            }, lifecycleProvider)
    }

    fun reqCollection(id: String?) {
        mHouseService.reqCollection(id)
            .execute(object : BaseSubscriber<Any?>(mView) {
                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException) {
                        mView?.onReqCollectionResult(true)
                    }
                }
            }, lifecycleProvider)
    }

    fun reqDeleteCollection(id: String?) {
        mHouseService.reqDeleteCollection(id)
            .execute(object : BaseSubscriber<Any?>(mView) {
                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException) {
                        mView?.onReqCollectionResult(false)
                    }
                }
            }, lifecycleProvider)
    }

    fun setHouseInfo(
        id: String?,
        hFlag: Int? = null,
        isCirculation: Int? = null,
        ownerTel: String? = null,
        imageUser: Int? = null
    ) {
        mView?.showLoading("正在修改")
        mHouseService.setHouseInfo(
            SetHouseInfoReq(
                id,
                hFlag,
                circulation = isCirculation,
                ownerTel = ownerTel,
                imageUser = imageUser
            )
        )
            .execute(object : BaseSubscriber<SetHouseInfoRep?>(mView) {
                override fun onNext(t: SetHouseInfoRep?) {
                    super.onNext(t)
                    mView?.onHouseStatus(t)
                }
            }, lifecycleProvider)
    }

    fun getUploadToken() {
        mView?.showLoading("正在上传...")
        mHouseService.getUploadToken()
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onGetUploadTokenResult(t)
                }
            }, lifecycleProvider)
    }

    fun postHouseImage(
        id: String?,
        imagUrl: String? = null
    ) {
        mHouseService.postHouseImage(
            SetHouseInfoReq(id = id,imagUrl = imagUrl))
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onUploadSuccessResult(t)
                }
            }, lifecycleProvider)
    }

    fun postReferImage(
        id: String?,
        houseCode: String? = null,
        referUrl: String? = null
    ) {
        mHouseService.postReferImage(
            SetHouseInfoReq(id = id,houseCode = houseCode,referUrl = referUrl))
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onUploadSuccessResult(t)
                }
            }, lifecycleProvider)
    }
}