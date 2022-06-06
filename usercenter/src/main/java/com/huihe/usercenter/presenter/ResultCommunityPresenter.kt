package com.huihe.usercenter.presenter

import com.huihe.usercenter.presenter.view.ResultCommunityView
import com.kotlin.base.presenter.BasePresenter
import com.huihe.boyueentities.protocol.common.District
import com.kotlin.provider.utils.UserPrefsUtils
import javax.inject.Inject

class ResultCommunityPresenter @Inject constructor() : BasePresenter<ResultCommunityView>() {

    var data :MutableList<District.ZoneBean.VillageBean> = mutableListOf()
    fun getVillages(keyword: String) {
        var villages = UserPrefsUtils.getVillages()
        var data = getConvertData(villages,keyword)
        mView?.onGetAreaBeanListResult(data)
    }

    private fun getConvertData(
        villages: MutableList<District>?,
        keyword: String
    ): MutableList<District.ZoneBean.VillageBean> {

        data.clear()
        villages?.forEach { item->
            item.zones?.forEach { item->

                item.villages?.forEach {
                    var name = it.name?:""
                    if (name.contains(keyword)){
                        data.add(it)
                    }
                }
            }
        }
        return data
    }

}