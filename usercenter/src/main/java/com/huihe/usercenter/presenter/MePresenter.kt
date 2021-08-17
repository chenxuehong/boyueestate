package com.huihe.usercenter.presenter

import com.huihe.usercenter.data.protocol.*
import com.huihe.usercenter.injection.module.UserModule.UserLevels.Companion.Administrators
import com.huihe.usercenter.injection.module.UserModule.UserLevels.Companion.Staff
import com.huihe.usercenter.presenter.view.MeView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.utils.UserPrefsUtils
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class MePresenter @Inject constructor() : BasePresenter<MeView>() {

    @Inject
    lateinit var service: UserService

    fun getUserInfo() {
        var userInfo = UserPrefsUtils.getUserInfo()
        service.getUserInfo(userInfo?.uid)
            .execute(object : BaseSubscriber<UserInfo?>(mView) {

                override fun onNext(t: UserInfo?) {
                    super.onNext(t)
                    mView?.onUserInfo(t)
                }
            }, lifecycleProvider)
    }

    fun setUserInfo(userInfoReq: SetUserInfoReq) {
        var userInfo = UserPrefsUtils.getUserInfo()
        userInfoReq.uid = userInfo?.uid
        service.setUserInfo(userInfoReq)
            .execute(object : BaseSubscriber<SetUserInfoRep?>(mView) {

                override fun onNext(t: SetUserInfoRep?) {
                    super.onNext(t)
                    mView?.onSetUserInfo()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException) {
                        mView?.onSetUserInfo()
                    }
                }
            }, lifecycleProvider)
    }


    fun getUploadToken() {
        mView?.showLoading("正在上传...")
        service.getUploadToken()
            .execute(object : BaseSubscriber<String?>(mView) {
                override fun onNext(t: String?) {
                    super.onNext(t)
                    mView?.onGetUploadTokenResult(t)
                }
            }, lifecycleProvider)
    }

    /**
     * @desc 带看任务统计
     */
    fun getLookTaskStatic(type: Int) {
        service.getUserLevels()
            .flatMap(Function<Int?, Observable<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?>> {
                AppPrefsUtils.putString(BaseConstant.KEY_LEVELS,"${if (it != null && it <= 3) Administrators else Staff}")
                return@Function if (it != null && it > 3) {
                    service.getLookTaskStaffStatic(type)
                } else {
                    service.getLookTaskAdministratorsStatic(type)
                }
            }).flatMap(Function<MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>?, Observable<LookTaskStaffStaticRep.StaffStatic?>> {
                if (it == null || it.size == 0) {
                    return@Function Observable.just(LookTaskStaffStaticRep.StaffStatic(0, 0, 0, 0))
                }
                return@Function Observable.just(
                    LookTaskStaffStaticRep.StaffStatic(
                        getStaticByStatus(
                            it,
                            0
                        ),
                        getStaticByStatus(it, 1),
                        getStaticByStatus(it, 2),
                        getStaticByStatus(it, 3)
                    )
                )
            }).execute(object : BaseSubscriber<LookTaskStaffStaticRep.StaffStatic?>(mView) {
                override fun onNext(t: LookTaskStaffStaticRep.StaffStatic?) {
                    super.onNext(t)
                    mView?.onLookTaskStatic(
                        t?.to_start ?: 0,
                        t?.take_look ?: 0,
                        t?.in_summary ?: 0,
                        t?.under_review ?: 0
                    )
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException) {
                        mView?.onLookTaskStatic(0, 0, 0, 0)
                    }
                }
            }, lifecycleProvider)
    }

    private fun getStaticByStatus(
        list: MutableList<LookTaskStaffStaticRep.LookTaskStaffStatic>,
        status: Int
    ): Int {
        if (list == null) {
            return 0
        }
        list.forEach { item ->
            if (item.status == status) {
                return item.count
            }
        }
        return 0
    }
}
