package com.huihe.usercenter.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.SearchBean
import com.huihe.usercenter.ui.activity.ResultCommunityActivity
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.data.db.MySQLiteDBDao
import com.kotlin.base.data.db.MySQLiteOpenHelper
import com.kotlin.provider.event.VillageEvent
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.vertical
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.provider.constant.UserConstant
import kotlinx.android.synthetic.main.fragment_search_community.*
import org.jetbrains.anko.support.v4.startActivity

class SearchCommunityFragment : BaseFragment(), SearchHistoryRvAdapter.OnListener {

    var searchHistoryRvAdapter: SearchHistoryRvAdapter? = null
    var sqLiteDBDao: MySQLiteDBDao? = null
    var isSelect = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_search_community, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sqLiteDBDao = MySQLiteDBDao.getInstance(context)
        isSelect = arguments?.getBoolean(BaseConstant.KEY_ISSELECT) ?: false
        initView()
        initData()
    }

    private fun initView() {
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(history: String): Boolean {
                onSearch(history)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        if (isSelect) {
            Bus.observe<VillageEvent>()
                .subscribe {
                    activity?.finish()
                }.registerInBus(this)
        }
    }

    override fun onSearch(history: String) {
        if (!containHistory(history)) {

            val data = searchHistoryRvAdapter?.dataList
            if (data != null && data!!.size > 5) {
                val deleteHistory = data!!.get(0)
                sqLiteDBDao?.deleteHistory(
                    MySQLiteOpenHelper.table.HistoryTable.TABLE_NAME,
                    deleteHistory.content
                )
            }
            sqLiteDBDao?.insertHistory(
                MySQLiteOpenHelper.table.HistoryTable.TABLE_NAME,
                history
            )
        }
        initData()
        startActivity<ResultCommunityActivity>(
            UserConstant.KEY_VILLAGE to history,
            BaseConstant.KEY_ISSELECT to isSelect
        )
    }

    private fun initData() {
        rvSearchHistory.vertical()
        searchHistoryRvAdapter = SearchHistoryRvAdapter(context!!, this)
        rvSearchHistory.adapter = searchHistoryRvAdapter
        var historyList = getHistoryList()
        searchHistoryRvAdapter?.setData(historyList!!)
    }

    private fun containHistory(history: String): Boolean {
        if (searchHistoryRvAdapter == null) {
            return false
        }
        val dataList = searchHistoryRvAdapter?.dataList

        dataList?.forEach { item ->
            if (item.content == history) {
                return true
            }
        }
        return false
    }

    private fun getHistoryList(): MutableList<SearchBean> {
        var list =
            sqLiteDBDao?.queryHistoryList(MySQLiteOpenHelper.table.HistoryTable.TABLE_NAME)
                ?: mutableListOf()
        var data = mutableListOf<SearchBean>()
        data.add(SearchBean(0, "历史记录"))
        data.add(SearchBean(1, "清除记录"))
        list.forEach { item ->
            data.add(SearchBean(2, item))
        }
        return data
    }

    override fun onDelete(content: String) {
        sqLiteDBDao?.deleteHistory(
            MySQLiteOpenHelper.table.HistoryTable.TABLE_NAME,
            content
        )
        initData()
    }

    override fun clearHistory() {
        try {
            val historyList = getHistoryList()
            for (i in historyList.indices) {
                sqLiteDBDao?.deleteHistory(
                    MySQLiteOpenHelper.table.HistoryTable.TABLE_NAME,
                    historyList[i].content
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        initData()
    }

    override fun onDestroy() {
        try {
            if (isSelect) {
                Bus.unregister(this)
            }
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
