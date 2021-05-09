package com.huihe.module_home.presenter.view

import com.huihe.module_home.data.protocol.FollowRep
import com.kotlin.base.presenter.view.BaseView

interface AddFollowView : BaseView {
    fun onAddFollowResult(t: FollowRep.FollowBean?)
}
