package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.FollowRep
import com.kotlin.base.presenter.view.BaseView

interface HouseFollowView : BaseView {
    fun getFollowRepResult(t: FollowRep?)
}