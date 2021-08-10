package com.huihe.usercenter.presenter

import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.LookTaskDetailRep
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.view.MineLookTaskDetailView
import com.huihe.usercenter.service.UserService
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.AppPrefsUtils
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MineLookTaskDetailPresenter @Inject constructor() : BasePresenter<MineLookTaskDetailView>() {

    @Inject
    lateinit var service: UserService

    var mOperationList = mutableListOf<String>(
        BaseApplication.context.resources.getString(R.string.looktask_insert_house),
        BaseApplication.context.resources.getString(R.string.looktask_transfer),
        BaseApplication.context.resources.getString(R.string.looktask_cancel),
        BaseApplication.context.resources.getString(R.string.looktask_follow),
        BaseApplication.context.resources.getString(R.string.looktask_AccompanyFollow),
        BaseApplication.context.resources.getString(R.string.looktask_Submit_review)
    )

    fun getLookTaskDetailOperator() {
        var isAdministrators = AppPrefsUtils.getString(BaseConstant.KEY_LEVELS)
        if (isAdministrators.isNullOrEmpty()) {
            throw IllegalArgumentException("isAdministrators is null")
        } else {
            mOperationList.clear()
            mOperationList.add( BaseApplication.context.resources.getString(R.string.looktask_insert_house))
            mOperationList.add( BaseApplication.context.resources.getString(R.string.looktask_transfer))
            mOperationList.add( BaseApplication.context.resources.getString(R.string.looktask_cancel))
            mOperationList.add( BaseApplication.context.resources.getString(R.string.looktask_follow))
            mOperationList.add( BaseApplication.context.resources.getString(R.string.looktask_AccompanyFollow))
            if (isAdministrators == UserModule.UserLevels.Administrators.toString()) {
                mOperationList.add( BaseApplication.context.resources.getString(R.string.looktask_review))
            } else {
                mOperationList.add( BaseApplication.context.resources.getString(R.string.looktask_Submit_review))
            }
            mView?.onLookTaskDetailOperations(mOperationList)
        }
    }

    fun getLookTaskDetail(takeLookId: String?) {
        service.getLookTaskDetail(takeLookId)
            .execute(object : BaseSubscriber<MutableList<LookTaskDetailRep>?>(mView) {

                override fun onNext(t: MutableList<LookTaskDetailRep>?) {
                    mView.onLookTaskDetail(t)
                }

            }, lifecycleProvider)
    }

}