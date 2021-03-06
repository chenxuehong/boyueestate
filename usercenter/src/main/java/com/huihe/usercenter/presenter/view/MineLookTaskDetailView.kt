package com.huihe.usercenter.presenter.view

import com.huihe.boyueentities.protocol.user.LookTaskDetailRep
import com.kotlin.base.presenter.view.BaseView

interface MineLookTaskDetailView :BaseView{
    fun onLookTaskDetail(t: MutableList<LookTaskDetailRep>?)
    fun onLookTaskDetailOperations(mOperationList: MutableList<String>)
    fun onDeleteLookHouseSuccess(index: Int)
    fun onTransferSuccess()
    fun onDeleteLookTaskSuccess()
    fun onLookHouseReviewSuccess()

}