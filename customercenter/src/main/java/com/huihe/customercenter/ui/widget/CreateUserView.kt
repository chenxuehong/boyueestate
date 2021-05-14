package com.huihe.customercenter.ui.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.huihe.customercenter.data.protocol.DeptUserRep
import com.huihe.customercenter.data.protocol.DeptUserResult
import com.huihe.customercenter.ext.enableList
import com.huihe.customercenter.injection.module.CustomersModule
import com.huihe.customercenter.ui.adapter.DeptUsersRvAdapter
import com.huihe.customercenter.ui.adapter.DeptUsersRvAdapter2
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_search_by_create_user.view.*

class CreateUserView {
    private var mListener: ISearchResultListener? = null

    var mCheckList: MutableList<DeptUserRep.DeptUser>? = null
    var mCheckedDeptUserRep: DeptUserRep? = null
    fun initCreateUserView(
        mContext: Context,
        listener: ISearchResultListener?,
        view: View
    ) {
        mListener = listener
        view?.apply {
            btnAreaReset.setOnClickListener {
                mCheckList?.clear()
                initRvlabel1(mContext)
                initRvLabel2(mContext, mutableListOf())
            }
            btnAreaSure.setOnClickListener {

                var createUserList = getCreateUserList()
                mListener?.onSearchResult(
                    DeptUserResult(createUserList),
                    if (mCheckList?.size!! > 0) {
                        "${mCheckedDeptUserRep?.label}+${mCheckList?.size ?: 0}"
                    } else {
                        "录入人"
                    },
                    CustomersModule.SearchType.CreateUserType
                )
            }
            initRvlabel1(mContext)
        }
    }

    fun getCreateUserList(): String {
        var sb = StringBuffer()
        mCheckList?.forEach { item ->
            sb.append(",").append(item.uid)
        }
        if (sb.length > 0) {
            return sb.substring(1).toString()
        }
        return ""
    }

    fun View.initRvlabel1(mContext: Context) {
        var adapter = DeptUsersRvAdapter(mContext)
        rvlabel.layoutManager = LinearLayoutManager(mContext)
        adapter?.setOnItemClickListener(
            object : BaseRecyclerViewAdapter.OnItemClickListener<DeptUserRep?> {
                override fun onItemClick(view: View, item: DeptUserRep?, position: Int) {
                    mCheckedDeptUserRep = item
                    initRvLabel2(mContext, item?.children)
                }
            }
        )
        rvlabel.adapter = adapter
        mListener?.startLoadDeptUsers(adapter)
    }

    private fun View.initRvLabel2(
        mContext: Context,
        children: MutableList<DeptUserRep.DeptUser>?
    ) {
        var adapter = DeptUsersRvAdapter2(mContext, object : DeptUsersRvAdapter2.OnCheckListener {
            override fun onChecked(
                view: View,
                item: DeptUserRep.DeptUser?,
                position: Int,
                checkList: MutableList<DeptUserRep.DeptUser>
            ) {
                btnAreaReset.enableList(mContext, checkList)
                mCheckList = checkList
            }

        })
        rvlabel2.layoutManager = LinearLayoutManager(mContext)
        rvlabel2.adapter = adapter
        adapter.setData(children!!)
    }
}
