package com.huihe.usercenter.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.LookTaskDetailRep
import com.huihe.boyueentities.protocol.user.LookHouseReviewReq
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.MineLookTaskDetailPresenter
import com.huihe.usercenter.presenter.view.MineLookTaskDetailView
import com.huihe.usercenter.ui.activity.*
import com.huihe.usercenter.ui.adapter.MineLookTaskDetailOperatorRvAdapter
import com.huihe.usercenter.ui.adapter.MineLookTaskDetailRvAdapter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.LookTaskEvent
import com.kotlin.provider.event.MeRefreshEvent
import com.kotlin.provider.event.RefreshLookTaskDetailEvent
import com.kotlin.provider.event.SearchHouseEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_mine_looktask_detail.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

class MineLookTaskDetailFragment :BaseMvpFragment<MineLookTaskDetailPresenter>(),MineLookTaskDetailView,
    MineLookTaskDetailRvAdapter.OnLookTaskListener {

    var takeLookId:String? = null
    var customerCode:String? = null
    var mineLookTaskDetailRvAdapter :MineLookTaskDetailRvAdapter?= null
    var mineLookTaskDetailOperatorRvAdapter : MineLookTaskDetailOperatorRvAdapter?= null
    var mItemTaskCancelDialog : DialogInterface ?=null
    var mTransferDialog : DialogInterface ?=null
    var mLookTaskCancelDialog : DialogInterface ?=null
    var mLDialog:LDialog?=null
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
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_mine_looktask_detail, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        takeLookId = arguments?.getString(UserConstant.KEY_ID)
        customerCode = arguments?.getString(UserConstant.KEY_CUSTOMER_CODE)
        initListener()
        initOperatorRvAdapter()
        initContentRvAdapter()
    }

    private fun initListener() {
        Bus.observe<RefreshLookTaskDetailEvent>()
            .subscribe {
                refreshData()
            }.registerInBus(this)
        Bus.observe<SearchHouseEvent>()
            .subscribe {
                if (isOnResume && "AddressBookFragment" == it.type) {
                    mPresenter.doTransfer(takeLookId,it?.id)
                }
            }.registerInBus(this)
    }

    var isOnResume = true
    override fun onResume() {
        super.onResume()
        isOnResume = true
    }

    override fun onPause() {
        super.onPause()
        isOnResume = false
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

    private fun initContentRvAdapter() {
        rvContent.vertical(1,DensityUtils.dp2px(context,15f))
        mineLookTaskDetailRvAdapter = MineLookTaskDetailRvAdapter(context!!,this)
        rvContent.adapter = mineLookTaskDetailRvAdapter
    }

    private fun doOperation(item: String) {
        when(item){
            BaseApplication.context.resources.getString(R.string.looktask_insert_house)->{
                startActivity<InsertMineLookHouseActivity>(UserConstant.KEY_ID to takeLookId)
            }
            BaseApplication.context.resources.getString(R.string.looktask_transfer)->{
                transfer()
            }
            BaseApplication.context.resources.getString(R.string.looktask_cancel)->{
                doCancelLookTask()
            }
            BaseApplication.context.resources.getString(R.string.looktask_follow)->{
                startActivity<MineLookHouseFollowActivity>(UserConstant.KEY_ID to takeLookId,UserConstant.KEY_CUSTOMER_CODE to customerCode)
            }
            BaseApplication.context.resources.getString(R.string.looktask_AccompanyFollow)->{
                startActivity<AccompanyFollowActivity>(UserConstant.KEY_ID to takeLookId,UserConstant.KEY_CUSTOMER_CODE to customerCode)
            }
            BaseApplication.context.resources.getString(R.string.looktask_review)->{
                startActivity<LookTaskAuditActivity>(UserConstant.KEY_ID to takeLookId,UserConstant.KEY_CUSTOMER_CODE to customerCode)
            }
            BaseApplication.context.resources.getString(R.string.looktask_Submit_review)->{
                showCommitReview()
            }
        }
    }

    private fun showCommitReview() {

        mLDialog = LDialog.init(childFragmentManager)
            .setLayoutRes(R.layout.layout_commit_review_dialog)
            .setBackgroundDrawableRes(R.drawable.commit_review_dialog_bg)
            .setGravity(Gravity.CENTER)
            .setWidthScale(0.75f)
            .setViewHandlerListener(object : ViewHandlerListener() {
                override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                    holder.getView<TextView>(R.id.tvYes).onClick {
                        dialog.dismiss()
                        mPresenter.lookHouseReview(LookHouseReviewReq(takeLookId))
                    }
                    holder.getView<TextView>(R.id.tvNo).onClick {
                        dialog.dismiss()

                    }
                    holder.getView<ImageView>(R.id.ivSetImageUserClose).onClick {
                        dialog.dismiss()
                    }
                }
            }).show()
    }

    private fun transfer() {
        mTransferDialog = alert(R.string.task_transfer_dialog_content, R.string.task_transfer_dialog_title) {
            positiveButton(R.string.task_sure) {
                it.dismiss()
                ARouter.getInstance().build(RouterPath.UserCenter.PATH_ADDRESSBOOK)
                    .withBoolean(BaseConstant.KEY_ISSELECT, true)
                    .navigation(context)
            }
            negativeButton(R.string.task_cancel){
                it.dismiss()
            }
        }.show()
    }

    private fun doCancelLookTask() {
        mLookTaskCancelDialog = alert(R.string.task_cancel_dialog_content, R.string.task_cancel_dialog_title) {
            positiveButton(R.string.task_sure) {
                it.dismiss()
                mPresenter?.deleteLookTask(takeLookId)
            }
            negativeButton(R.string.task_cancel){
                it.dismiss()
            }
        }.show()
    }

    private fun initData() {
        mPresenter.getLookTaskDetailOperator()
        mPresenter.getLookTaskDetail(takeLookId)
    }

    fun refreshData(){
        mPresenter?.getLookTaskDetail(takeLookId)
    }

    override fun onLookTaskDetailOperations(mOperationList: MutableList<String>) {
        mineLookTaskDetailOperatorRvAdapter?.setData(mOperationList!!)
    }

    override fun onLookTaskDetail(t: MutableList<LookTaskDetailRep>?) {
        mineLookTaskDetailRvAdapter?.setData(t!!)
    }

    override fun onItemCancel(index: Int, item: LookTaskDetailRep) {
        mItemTaskCancelDialog = alert(R.string.task_cancel_dialog_content, R.string.task_cancel_dialog_title) {
            positiveButton(R.string.task_sure) {
                it.dismiss()
                mPresenter.deleteLookHouse(item.id,index)
            }
           negativeButton(R.string.task_cancel){
               it.dismiss()
           }
        }.show()
    }

    override fun onDeleteLookHouseSuccess(index: Int) {
        mPresenter?.getLookTaskDetail(takeLookId)
    }

    override fun onTransferSuccess() {
        toast(resources.getString(R.string.task_transfer_successful))
        Bus.send(LookTaskEvent(null))
        Bus.send(MeRefreshEvent())
        activity?.finish()
    }

    override fun onDeleteLookTaskSuccess() {
        toast(resources.getString(R.string.task_cancel_successful))
        Bus.send(LookTaskEvent(null))
        Bus.send(MeRefreshEvent())
        activity?.finish()
    }

    override fun onTakeLookSign(index: Int, item: LookTaskDetailRep) {
        startActivity<SignActivity>(UserConstant.KEY_ID to item.id,
            UserConstant.KEY_IS_TAKE_LOOK to true,
            UserConstant.KEY_LATITUDE to item.latitude,
            UserConstant.KEY_LONGITUDE to item.longitude
        )
    }

    override fun onAccompanySign(index: Int, item: LookTaskDetailRep) {

        startActivity<SignActivity>(UserConstant.KEY_ID to item.id,
            UserConstant.KEY_IS_TAKE_LOOK to false,
            UserConstant.KEY_LATITUDE to item.latitude,
            UserConstant.KEY_LONGITUDE to item.longitude
            )
    }

    override fun onDestroy() {
        try {
            mPresenter?.release()
            Bus.unregister(this)
            mItemTaskCancelDialog?.dismiss()
            mTransferDialog?.dismiss()
            mLookTaskCancelDialog?.dismiss()
            mLDialog?.dismiss()
            hideLoading()
        } catch (e: Exception) {
        } finally {
            mItemTaskCancelDialog = null
            mTransferDialog = null
            mLookTaskCancelDialog = null
        }
        super.onDestroy()
    }

    override fun onLookHouseReviewSuccess() {
        toast(resources.getString(R.string.commitSuccess))
        Bus.send(LookTaskEvent(null))
        Bus.send(MeRefreshEvent())
        activity?.finish()
    }
}