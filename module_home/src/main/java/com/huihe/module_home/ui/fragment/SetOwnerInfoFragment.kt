package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.SetHouseInfoRep
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.SetOwnerInfoPresenter
import com.huihe.module_home.presenter.view.SetOwnerInfoView
import com.huihe.module_home.ui.adpter.RvPhoneAdapter
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.layout_fragment_set_owner_info.*
import org.jetbrains.anko.support.v4.toast

class SetOwnerInfoFragment : BaseMvpFragment<SetOwnerInfoPresenter>(), SetOwnerInfoView {

    var mRvPhoneAdapter: RvPhoneAdapter? = null
    var id: String? = null
    var ownerName: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_fragment_set_owner_info, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString(HomeConstant.KEY_HOUSE_ID)
        ownerName = arguments?.getString(HomeConstant.KEY_OWNER_NAME)?:""
        initView()
        initRvPhoneAdapter()
        initData()
    }

    private fun initData() {
        set_owner_info_et_OwnerNick.setText(ownerName)
        mPresenter.getHouseMobile(id)
    }

    private fun initView() {
        set_owner_info_titleBar.getRightView()?.onClick {
            if (checkInput()) {
                var dataList = mRvPhoneAdapter?.dataList
                var telStr: String = getTelList(dataList)
                mPresenter.setHouseInfo(
                    id = id,
                    ownerTel = telStr,
                    ownerName = set_owner_info_et_OwnerNick.text.toString().trim()
                )
            }
        }
        set_owner_info_tv_insert.onClick {
            mRvPhoneAdapter?.insertItem()
        }
        set_owner_info_tv_delete.onClick {
            mRvPhoneAdapter?.deleteItem()
        }
    }

    private fun getTelList(dataList: MutableList<String?>?): String {
        var sb = StringBuffer()
        dataList?.forEach { item ->
            if (!TextUtils.isEmpty(item)) {
                sb.append("*").append(item)
            }
        }
        if (sb.length > 1) {
            return sb.substring(1).toString().trim()
        }
        return ""
    }

    private fun initRvPhoneAdapter() {
        mRvPhoneAdapter = RvPhoneAdapter(context)
        set_owner_info_rv_OwnerPhone.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRvPhoneAdapter
        }
        mRvPhoneAdapter?.setData(mutableListOf(""))
    }

    private fun checkInput(): Boolean {
        if (TextUtils.isEmpty(set_owner_info_et_OwnerNick.text.toString().trim())) {
            toast(resources.getString(R.string.inputOwnerNick))
            return false
        }
        return true
    }

    override fun onHouseInfo(t: SetHouseInfoRep?) {
        toast("修改成功")
        activity?.finish()
    }

    override fun onMobile(ownerTel: String?) {
        var split = ownerTel?.split("*") ?: mutableListOf()
        split = split?.toMutableList()
        if (split?.isEmpty()) {
            return
        }
        mRvPhoneAdapter?.setData(split.toMutableList())
    }
}