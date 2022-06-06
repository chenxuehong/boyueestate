package com.huihe.usercenter.presenter

import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.LookHouseReviewReq
import com.huihe.boyueentities.protocol.user.LookTaskDetailRep
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.view.MineLookTaskDetailView
import com.huihe.commonservice.service.user.UserService
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.rx.DataNullException
import com.kotlin.base.utils.AppPrefsUtils
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MineLookTaskDetailPresenter @Inject constructor() : BasePresenter<MineLookTaskDetailView>() {

    @Inject
    lateinit var service: UserService

    var mOperationList :MutableList<String>?= mutableListOf<String>()

    fun getLookTaskDetailOperator() {
        var isAdministrators = AppPrefsUtils.getString(BaseConstant.KEY_LEVELS)
        if (isAdministrators.isNullOrEmpty()) {
            throw IllegalArgumentException("isAdministrators is null") as Throwable
        } else {
            mOperationList?.clear()
            mOperationList?.add( BaseApplication.context.resources.getString(R.string.looktask_insert_house))
            mOperationList?.add( BaseApplication.context.resources.getString(R.string.looktask_transfer))
            mOperationList?.add( BaseApplication.context.resources.getString(R.string.looktask_cancel))
            mOperationList?.add( BaseApplication.context.resources.getString(R.string.looktask_follow))
            mOperationList?.add( BaseApplication.context.resources.getString(R.string.looktask_AccompanyFollow))
            if (isAdministrators == UserModule.UserLevels.Administrators.toString()) {
                mOperationList?.add( BaseApplication.context.resources.getString(R.string.looktask_review))
            } else {
                mOperationList?.add( BaseApplication.context.resources.getString(R.string.looktask_Submit_review))
            }
            mView?.onLookTaskDetailOperations(mOperationList!!)
        }
    }

    fun getLookTaskDetail(takeLookId: String?) {
        mView.showLoading()
        service.getLookTaskDetail(takeLookId)
            .execute(object : BaseSubscriber<MutableList<LookTaskDetailRep>?>(mView) {

                override fun onNext(t: MutableList<LookTaskDetailRep>?) {
                    mView.onLookTaskDetail(t)
                }

            }, lifecycleProvider)
    }

    override fun release() {
        if (mOperationList!=null){
            mOperationList?.clear()
            mOperationList = null
        }
    }

    fun deleteLookHouse(id: String?, index: Int) {
        mView.showLoading(BaseApplication.context.resources.getString(R.string.task_canceling))
        service.deleteLookHouse(id)
            .execute(object : BaseSubscriber<Any>(mView) {

                override fun onNext(t: Any) {
                    mView.onDeleteLookHouseSuccess(index)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView.onDeleteLookHouseSuccess(index)
                    }
                }
            }, lifecycleProvider)
    }

    fun doTransfer(id: String?, changeUserId: String?) {
        mView.showLoading(BaseApplication.context.resources.getString(R.string.task_transferring))
        service.doTransfer(id,changeUserId)
            .execute(object : BaseSubscriber<Any>(mView) {

                override fun onNext(t: Any) {
                    super.onNext(t)
                    mView.onTransferSuccess()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView.onTransferSuccess()
                    }
                }
            }, lifecycleProvider)
    }

    fun deleteLookTask(id: String?) {
        mView.showLoading(BaseApplication.context.resources.getString(R.string.task_canceling))
        service.deleteLookTask(id)
            .execute(object : BaseSubscriber<Any>(mView) {

                override fun onNext(t: Any) {
                    mView.onDeleteLookTaskSuccess()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView.onDeleteLookTaskSuccess()
                    }
                }
            }, lifecycleProvider)
    }

    fun lookHouseReview(req: LookHouseReviewReq) {
        mView.showLoading(BaseApplication.context.resources.getString(R.string.committing))
        service.lookHouseReview(req)
            .execute(object : BaseSubscriber<Any>(mView) {

                override fun onNext(t: Any) {
                    mView.onLookHouseReviewSuccess()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is DataNullException){
                        mView.onLookHouseReviewSuccess()
                    }
                }
            }, lifecycleProvider)
    }

}