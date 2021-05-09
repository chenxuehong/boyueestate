package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseTakeLookRep
import com.huihe.module_home.injection.component.DaggerCustomersComponent
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.presenter.HouseTakeLookRecordInsertPresenter
import com.huihe.module_home.presenter.view.HouseTakeLookRecordInsertView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_house_take_look_record_insert.*
import org.jetbrains.anko.support.v4.toast

class HouseTakeLookRecordInsertFragment : BaseMvpFragment<HouseTakeLookRecordInsertPresenter>(),
    HouseTakeLookRecordInsertView {

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
        initView()
    }

    private fun initView() {
        nsvTakeLookInsert_house.onClick {
            // 选择房源
        }
        tvTakeLookInsert.onClick {
            if (checkInput()) {
                mPresenter.addHouseTakeLookRecord(
                    evaluate = EtTakeLookInsertEvaluation.text.toString().trim()
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

    override fun onAddHouseTakeLookResult(t: HouseTakeLookRep.HouseTakeLook?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
