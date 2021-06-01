package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.UserInfo
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.DeptInfoPresenter
import com.huihe.usercenter.presenter.view.DeptInfoView
import com.kotlin.base.ext.*
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.ChatEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_dept_info.*
import org.jetbrains.anko.support.v4.toast

class DeptInfoFragment : BaseMvpFragment<DeptInfoPresenter>(), DeptInfoView {

    var userId: String? = null
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_dept_info, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString(UserConstant.KEY_USER_ID)
        initView()
        iniData()
    }

    private fun initView() {

        ivDeptCallPhone.onClick {
            if (userInfo != null){
                var mobile = userInfo?.mobile ?: ""
                if (!TextUtils.isEmpty(mobile)){
                    callPhone(userInfo?.mobile?:"")
                }else{
                    toast("暂无电话")
                }
            }
        }
        ivDeptChat.onClick {
            Bus.send(ChatEvent(userInfo?.uid?:"",userInfo?.userName?:""))
        }
    }

    private fun iniData() {

        mPresenter.getUserInfo(userId)
    }

    var userInfo: UserInfo? = null
    override fun onUserInfo(t: UserInfo?) {
        userInfo = t
        ivDeptHead.loadHeadUrl(t?.avatarUrl ?: "")
        ivDeptStaffName.text = t?.userName ?: ""
        ivDeptName.text = t?.deptName ?: ""
        ivDeptPhone.text = t?.mobile ?: ""
    }
}
