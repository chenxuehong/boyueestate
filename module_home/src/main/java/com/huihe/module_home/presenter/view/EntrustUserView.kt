package com.huihe.module_home.presenter.view

import com.huihe.boyueentities.protocol.home.EntrustUserRep
import com.kotlin.base.presenter.view.BaseView

interface EntrustUserView :BaseView {
    fun onPutEntrustUserSuccess(t: EntrustUserRep?)
    fun onEntrustUserSuccess(t: EntrustUserRep?)
}