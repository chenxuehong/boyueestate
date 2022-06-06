package com.huihe.module_home.presenter

import com.huihe.boyueentities.protocol.home.*
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.commonservice.service.house.HouseService
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
        mHouseService.getHouseDetailRelationPeople(id)
            .compose(lifecycleProvider.bindToLifecycle())
            .flatMap(Function<OwnerInfo?, Observable<HouseDetail?>> {
                mView?.onGetOwnerResult(it)
                return@Function mHouseService.getHouseDetailById(id)
            })
            .execute(object : BaseSubscriber<HouseDetail?>(mView) {
                override fun onNext(t: HouseDetail?) {
                    super.onNext(t)
                    mView?.onGetHouseDetailResult(t)
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
        req: SetHouseInfoReq
    ) {
        mView?.showLoading("正在修改")
        mHouseService.setHouseInfo(
            req
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
            SetHouseInfoReq(id = id, imagUrl = imagUrl)
        )
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
            SetHouseInfoReq(id = id, houseCode = houseCode, referUrl = referUrl)
        )
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onUploadSuccessResult(t)
                }
            }, lifecycleProvider)
    }

    fun pathHouseCreateUser(id: String?, createUser: String?) {
        mView?.showLoading()
        mHouseService.pathHouseCreateUser(id, createUser)
            .execute(object : BaseSubscriber<HouseCreateUserRep?>(mView) {
                override fun onNext(t: HouseCreateUserRep?) {
                    super.onNext(t)
                    mView?.onHouseCreateUserResult(t)
                }
            }, lifecycleProvider)
    }

    fun putCapping(req: CappingReq) {
        mView?.showLoading()
        mHouseService.putCapping(req)
            .execute(object : BaseSubscriber<CappingRep?>(mView) {

                override fun onNext(t: CappingRep?) {
                    super.onNext(t)
                    mView?.onPutCappingResult(t)
                }
            }, lifecycleProvider)
    }

    fun getHouseMobile(houseCode: String?) {
        mHouseService.getHouseMobile(houseCode)
            .execute(object : BaseSubscriber<String?>(mView) {

                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onShowTelListDialog(t ?: "")
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    mView?.onShowTelListDialog("")
                }
            }, lifecycleProvider)
    }
}