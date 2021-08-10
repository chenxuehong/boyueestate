package com.huihe.usercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.usercenter.R
import com.huihe.usercenter.data.protocol.LookTaskDetailRep
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MineLookTaskDetailPresenter
import com.huihe.usercenter.presenter.view.MineLookTaskDetailView
import com.huihe.usercenter.ui.adapter.MineLookTaskDetailOperatorRvAdapter
import com.huihe.usercenter.ui.adapter.MineLookTaskDetailRvAdapter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.constant.UserConstant
import kotlinx.android.synthetic.main.fragment_mine_looktask_detail.*

class MineLookTaskDetailFragment :BaseMvpFragment<MineLookTaskDetailPresenter>(),MineLookTaskDetailView,
    MineLookTaskDetailRvAdapter.OnLookTaskListener {

    var id:String? = null
    var mineLookTaskDetailRvAdapter :MineLookTaskDetailRvAdapter?= null
    var mineLookTaskDetailOperatorRvAdapter : MineLookTaskDetailOperatorRvAdapter?= null
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initInflater(context!!, R.layout.fragment_mine_looktask_detail, container!!)
        super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        id = arguments?.getString(UserConstant.KEY_ID)
        initOperatorRvAdapter()
        initContentRvAdapter()
    }

    private fun initOperatorRvAdapter() {
        rvLookTaskOperator.vertical(3,DensityUtils.dp2px(context,15f))
        mineLookTaskDetailOperatorRvAdapter = MineLookTaskDetailOperatorRvAdapter(context!!)
        mineLookTaskDetailOperatorRvAdapter?.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<String>{
            override fun onItemClick(view: View, item: String, position: Int) {
                doOperation(item)
            }
        })
        rvLookTaskOperator.adapter = mineLookTaskDetailOperatorRvAdapter
    }

    private fun doOperation(item: String) {
        when(item){
            BaseApplication.context.resources.getString(R.string.looktask_insert_house)->{

            }
            BaseApplication.context.resources.getString(R.string.looktask_transfer)->{

            }
            BaseApplication.context.resources.getString(R.string.looktask_cancel)->{

            }
            BaseApplication.context.resources.getString(R.string.looktask_follow)->{

            }
            BaseApplication.context.resources.getString(R.string.looktask_AccompanyFollow)->{

            }
            BaseApplication.context.resources.getString(R.string.looktask_review)->{

            }
            BaseApplication.context.resources.getString(R.string.looktask_Submit_review)->{

            }
        }
    }

    private fun initContentRvAdapter() {
        rvContent.vertical(1,DensityUtils.dp2px(context,15f))
        mineLookTaskDetailRvAdapter = MineLookTaskDetailRvAdapter(context!!,this)
        rvContent.adapter = mineLookTaskDetailRvAdapter
    }

    private fun initData() {
        mPresenter.getLookTaskDetailOperator()
        mPresenter.getLookTaskDetail(id)
    }

    override fun onLookTaskDetailOperations(mOperationList: MutableList<String>) {
        mineLookTaskDetailOperatorRvAdapter?.setData(mOperationList!!)
    }

    override fun onLookTaskDetail(t: MutableList<LookTaskDetailRep>?) {
        mineLookTaskDetailRvAdapter?.setData(t!!)
    }

    override fun onItemCancel(index: Int, item: LookTaskDetailRep) {

    }

    override fun onTakeLookSign(index: Int, item: LookTaskDetailRep) {

    }

    override fun onAccompanySign(index: Int, item: LookTaskDetailRep) {

    }
}