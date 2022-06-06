package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.CommunityManagerView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.huihe.boyueentities.protocol.common.District
import com.kotlin.provider.utils.UserPrefsUtils
import javax.inject.Inject

class CommunityManagerPresenter @Inject constructor(): BasePresenter<CommunityManagerView>(){

    @Inject
    lateinit var service: UserService

    fun getVillages(
        latitude: Double?= null,
        longitude: Double?= null
    ) {
        if (!checkNetWork()) {
            return
        }
        var gettVillages = UserPrefsUtils.getVillages()
        if (gettVillages!=null){
            mView.onGetAreaBeanListResult(gettVillages)
            return
        }
        mView?.showLoading()
        service?.getVillages(
            latitude,
            longitude
        )
            .execute(object : BaseSubscriber<MutableList<District>?>(mView) {
                override fun onNext(t: MutableList<District>?) {
                    UserPrefsUtils.putVillages(t)
                    mView.onGetAreaBeanListResult(t)
                }
            }, lifecycleProvider)

    }
}
