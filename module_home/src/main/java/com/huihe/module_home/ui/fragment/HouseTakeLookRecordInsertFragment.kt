package com.huihe.module_home.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.module_home.R
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseTakeLookRecordInsertPresenter
import com.huihe.module_home.presenter.view.HouseTakeLookRecordInsertView
import com.huihe.module_home.ui.activity.HouseSelectActivity
import com.kotlin.base.ext.getString
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.constant.HomeConstant
import kotlinx.android.synthetic.main.fragment_house_take_look_record_insert.*
import org.jetbrains.anko.support.v4.toast

class HouseTakeLookRecordInsertFragment : BaseMvpFragment<HouseTakeLookRecordInsertPresenter>(),
    HouseTakeLookRecordInsertView {


    var REQUEST_CODE_SELECT_HOUSE: Int = 10001
    var houseCode: String? = null
    var houseCodeList = mutableListOf<String>()
    var code: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_house_take_look_record_insert, container, false)
    }

    override fun injectComponent() {
        DaggerCustomersComponent.builder().activityComponent(mActivityComponent).customersModule(
            CustomersModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseCodeList.clear()
        code = arguments?.getString(HomeConstant.KEY_CODE)
        initView()
    }

    private fun initView() {
        nsvTakeLookInsert_house.onClick {
            // 选择房源
            val intent = Intent(context, HouseSelectActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SELECT_HOUSE)
        }
        tvTakeLookInsert.onClick {
            if (checkInput()) {
                mPresenter.addHouseTakeLookRecord(
                    evaluate = EtTakeLookInsertEvaluation.text.toString().trim(),
                    houseCodeList = houseCodeList,
                    code = code
                )
            }
        }
        var customer_evaluation = String.format(
            "<font color=\"#ff0000\">%s</font>%s",
            "*",
            resources.getString(R.string.customer_evaluation)
        )
        tvTakeLookEvaluation.text = Html.fromHtml(customer_evaluation)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_SELECT_HOUSE == requestCode) {
            houseCode = data?.getStringExtra(HomeConstant.KEY_HOUSE_CODE)
            if (!TextUtils.isEmpty(houseCode)) {
                houseCodeList.add(houseCode!!)
                nsvTakeLookInsert_house.setContent(houseCodeList.getString(","))
            }
        }
    }

    private fun checkInput(): Boolean {
        if (TextUtils.isEmpty(nsvTakeLookInsert_house.getContent().toString().trim())) {
            toast(resources.getString(R.string.takelook_house_input))
            return false
        }
        if (TextUtils.isEmpty(EtTakeLookInsertEvaluation.text.toString().trim())) {
            toast(resources.getString(R.string.customer_evaluation_input))
            return false
        }
        return true
    }

    override fun onAddHouseTakeLookResult() {
        activity?.finish()
    }
}
