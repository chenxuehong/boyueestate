package com.huihe.usercenter.presenter.view

import com.huihe.boyueentities.protocol.user.MineLookTaskRep
import com.kotlin.base.presenter.view.BaseView

interface MineLookTaskView :BaseView{
    fun onLookTaskList(list: MutableList<MineLookTaskRep.MineLookTask>?)

}