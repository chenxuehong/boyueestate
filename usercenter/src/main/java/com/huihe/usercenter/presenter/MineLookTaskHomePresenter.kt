package com.huihe.usercenter.presenter

import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.view.MineLookTaskHomeView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.AppPrefsUtils
import javax.inject.Inject

class MineLookTaskHomePresenter @Inject constructor() : BasePresenter<MineLookTaskHomeView>() {
    @Inject
    lateinit var service: UserService


    /**
     * @desc 用户等级
     */
    fun getUserLevels() {
        var isAdministrators = AppPrefsUtils.getString(BaseConstant.KEY_LEVELS)
        if (isAdministrators.isNullOrEmpty()) {
            service.getUserLevels()
                .execute(object : BaseSubscriber<Int?>(mView) {

                    override fun onNext(t: Int?) {
                        super.onNext(t)
                        AppPrefsUtils.putString(BaseConstant.KEY_LEVELS,"${if (t != null && t <= 3) UserModule.UserLevels.Administrators else UserModule.UserLevels.Staff}")
                        mView?.onIsAdministrators(t != null && t <= 3)
                    }

                }, lifecycleProvider)
        } else {
            mView?.onIsAdministrators(isAdministrators == UserModule.UserLevels.Administrators.toString())
        }
    }
}