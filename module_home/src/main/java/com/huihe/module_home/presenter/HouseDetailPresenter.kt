package com.huihe.module_home.presenter

import android.annotation.SuppressLint
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.OwnerInfo
import com.huihe.module_home.presenter.view.HouseDetailView
import com.huihe.module_home.service.HouseService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class HouseDetailPresenter @Inject constructor() : BasePresenter<HouseDetailView>() {

    @Inject
    lateinit var mHouseService: HouseService

    @SuppressLint("CheckResult")
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

}