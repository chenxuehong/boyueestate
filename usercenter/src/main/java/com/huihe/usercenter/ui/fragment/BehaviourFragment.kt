package com.huihe.usercenter.ui.fragment

import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.BehaviourPresenter
import com.huihe.usercenter.presenter.view.BehaviourView
import com.kotlin.base.ui.fragment.BaseMvpFragment

class BehaviourFragment : BaseMvpFragment<BehaviourPresenter>(),BehaviourView{
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

}