package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.FollowRep
import com.kotlin.base.presenter.view.BaseView

interface AddFollowView : BaseView {
    fun onAddFollowResult(t: FollowRep.FollowBean?)
}
