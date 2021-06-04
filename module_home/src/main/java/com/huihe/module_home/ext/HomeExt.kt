package com.huihe.module_home.ext

import android.content.Context
import android.text.TextUtils
import android.widget.Button
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.adapter.MoreSearchAdapter
import com.kotlin.base.utils.DateUtils
import com.kotlin.provider.data.protocol.District


fun getConvertProvinceList(data: MutableList<District>?): ArrayList<Province> {
    var dataList: ArrayList<Province> = ArrayList()
    data?.apply {
        forEach { item ->
            val province = Province(item.id, item.name)
            province.cities = getCities(item.zones)
            dataList.add(province)
        }
    }
    return dataList
}

fun getCities(zones: MutableList<District.ZoneBean>): MutableList<City>? {
    var dataList: ArrayList<City> = ArrayList()
    zones?.forEach { item ->
        var city = City(item.id, item.name)
        city.counties = getCounties(item.villages)
        dataList.add(city)
    }
    return dataList
}

fun getCounties(villages: MutableList<District.ZoneBean.VillageBean>?): MutableList<County>? {
    var dataList: ArrayList<County> = ArrayList()
    villages?.forEach { item ->
        dataList.add(County(item.id, item.name))
    }
    return dataList
}

var list: MutableList<District.ZoneBean.VillageBean> = mutableListOf()

fun clearAreaCheckList() {
    list.clear()
}

fun getVillageIds(): MutableList<String> {
    var villageIds: MutableList<String> = mutableListOf()
    list?.forEach { item ->
        villageIds.add(item?.id!!)
    }
    return villageIds
}

var mCheckedItem: District? = null

fun setItemAreaChecked(
    zoneBean: District.ZoneBean.VillageBean?,
    isChecked: Boolean,
    checkedItem: District?
): MutableList<District.ZoneBean.VillageBean> {
    if (isChecked) {
        list.add(zoneBean!!)
    } else {
        list.remove(zoneBean!!)
    }
    if (list.size > 0) {
        mCheckedItem = checkedItem
    } else {
        mCheckedItem = null
    }
    return list
}

fun Button.enable(mContext: Context, adapter: MoreSearchAdapter) {
    var btnMoreReset = this
    var dataList = adapter?.getCheckedData()
    if (dataList?.size > 0) {
        btnMoreReset.isEnabled = true
        btnMoreReset.setTextColor(mContext.resources.getColor(R.color.color_333333))
    } else {
        btnMoreReset.setTextColor(mContext.resources.getColor(R.color.color_999999))
        btnMoreReset.isEnabled = false
    }
}

fun Button.enable(mContext: Context) {
    var btnMoreReset = this
    var dataList = list
    if (dataList?.size > 0) {
        btnMoreReset.isEnabled = true
        btnMoreReset.setTextColor(mContext.resources.getColor(R.color.color_333333))
    } else {
        btnMoreReset.setTextColor(mContext.resources.getColor(R.color.color_999999))
        btnMoreReset.isEnabled = false
    }
}


fun getConvertHouseDetailData(houseDetail: HouseDetail?): MutableList<ItemHouseDetail> {

    var imagUrls = houseDetail?.imagUrls
    var referUrls = houseDetail?.referUrls
    var ownerInfo = houseDetail?.ownerInfo
    var houseList: MutableList<ItemHouseDetail> = mutableListOf(
        // 轮播图
        ItemHouseDetail(bannerList = imagUrls),
        // 基础信息
        ItemHouseDetail(
            houseCode = houseDetail?.houseCode,
            basicInfo = ItemHouseDetail.BasicInfo(
                houseDetail?.villageInfoResponse?.name,
                houseDetail?.building,
                houseDetail?.hNum,
                houseDetail?.price,
                houseDetail?.floorage,
                houseDetail?.hShape,
                houseDetail?.label,
                houseDetail?.ownerTel
            )
        ),
        // 房源详情
        ItemHouseDetail(
            detailInfoList = mutableListOf(

                ItemHouseDetail.DetailInfo(
                    "业主信息",
                    "",
                    true
                ),
                ItemHouseDetail.DetailInfo(
                    "业主姓名",
                    houseDetail?.ownerName?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "房源详情",
                    "",
                    true
                ),
                ItemHouseDetail.DetailInfo(
                    "房源编号",
                    houseDetail?.houseCode?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "楼层",
                    "${houseDetail?.floor}/${houseDetail?.totalFloor}"
                ),
                ItemHouseDetail.DetailInfo(
                    "房龄",
                    houseDetail?.housePropertyYear?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "建筑面积",
                    "${houseDetail?.floorage}"
                ),
                ItemHouseDetail.DetailInfo(
                    "是否抵押",
                    houseDetail?.loanState?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "房东底价",
                    houseDetail?.ownerLowPrice?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "产权人数",
                    houseDetail?.propertyRightUserSize?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "房屋朝向",
                    houseDetail?.orientation?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "买入价",
                    houseDetail?.buyPrice?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "梯户",
                    "${houseDetail?.stairs ?: "0"}梯${houseDetail?.houseHolds ?: "0"}户"
                ),
                ItemHouseDetail.DetailInfo(
                    "看房时间",
                    houseDetail?.seeTime?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "对口学区",
                    houseDetail?.villageInfoResponse?.schoolDistrictName?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "是否满五唯一",
                    houseDetail?.isOnlyHouse?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "装修情况",
                    houseDetail?.decorat?:""
                )
            )
            ,
            ownerInfoList = mutableListOf(
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.createUser ?: "",
                    "录入人",
                    "${ownerInfo?.createUserName} ${DateUtils.stringToString(
                        ownerInfo?.createTime!!,
                        DateUtils.datePattern,
                        DateUtils.FORMAT_SHORT
                    )}",
                    CustomersModule.OwnerInfoType.createUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.maintainUser ?: "",
                    "维护人",
                    "${ownerInfo?.maintainUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.maintainUser)) "请选择 维护人" else ""}"}",
                    CustomersModule.OwnerInfoType.maintainUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.imageUser ?: "",
                    "图片人",
                    "${ownerInfo?.imageUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.imageUser)) "请选择 图片人" else ""}"}",
                    CustomersModule.OwnerInfoType.imageUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.entrustUser ?: "",
                    "委托人",
                    "${ownerInfo?.entrustUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.entrustUser)) "请选择 委托人" else ""}"}",
                    CustomersModule.OwnerInfoType.entrustUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.haveKeyUser ?: "",
                    "钥匙人",
                    "${ownerInfo?.haveKeyUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.haveKeyUser)) "请选择 钥匙人" else ""}"}",
                    CustomersModule.OwnerInfoType.haveKeyUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.soleUser ?: "",
                    "独家人",
                    "${ownerInfo?.soleUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.soleUser)) "请选择 独家人" else ""}"}",
                    CustomersModule.OwnerInfoType.soleUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.bargainPriceUser ?: "",
                    "推荐人",
                    "${ownerInfo?.bargainPriceUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.bargainPriceUser)) "请选择 推荐人" else ""}"}",
                    CustomersModule.OwnerInfoType.bargainPriceUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.blockUser ?: "",
                    "封盘人",
                    "${ownerInfo?.blockUserName ?: ""}",
                    CustomersModule.OwnerInfoType.blockUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.openUser ?: "",
                    "开盘人",
                    "${ownerInfo?.openUserName ?: ""}",
                    CustomersModule.OwnerInfoType.openUser
                )
            ),
            imagUrls = imagUrls!!,
            referUrls = referUrls!!,
            mapInfo = ItemHouseDetail.MapInfo(
                houseDetail?.villageInfoResponse?.latitude,
                houseDetail?.villageInfoResponse?.longitude,
                houseDetail?.villageInfoResponse?.name
            ),
            rewarks = houseDetail?.remarks
        )
//        // 备注
//        ItemHouseDetail(rewarks = houseDetail?.remarks),
//        // 房源相关人
//        ItemHouseDetail(
//            ownerInfoList = mutableListOf(
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.createUser?:"",
//                    "录入人",
//                    "${ownerInfo?.createUserName} ${DateUtils.stringToString(
//                        ownerInfo?.createTime!!,
//                        DateUtils.datePattern,
//                        DateUtils.FORMAT_SHORT
//                    )}",
//                    CustomersModule.OwnerInfoType.createUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.maintainUser?:"",
//                    "维护人",
//                    "${ownerInfo?.maintainUserName?:"${if (TextUtils.isEmpty(ownerInfo?.maintainUser)) "请选择 维护人" else ""}"}",
//                    CustomersModule.OwnerInfoType.maintainUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.imageUser?:"",
//                    "图片人",
//                    "${ownerInfo?.imageUserName?:"${if (TextUtils.isEmpty(ownerInfo?.imageUser)) "请选择 图片人" else ""}"}",
//                    CustomersModule.OwnerInfoType.imageUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.entrustUser?:"",
//                    "委托人",
//                    "${ownerInfo?.entrustUserName?:"${if (TextUtils.isEmpty(ownerInfo?.entrustUser)) "请选择 委托人" else ""}"}",
//                    CustomersModule.OwnerInfoType.entrustUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.haveKeyUser?:"",
//                    "钥匙人",
//                    "${ownerInfo?.haveKeyUserName?:"${if (TextUtils.isEmpty(ownerInfo?.haveKeyUser)) "请选择 钥匙人" else ""}"}",
//                    CustomersModule.OwnerInfoType.haveKeyUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.soleUser?:"",
//                    "独家人",
//                    "${ownerInfo?.soleUserName?:"${if (TextUtils.isEmpty(ownerInfo?.soleUser)) "请选择 独家人" else ""}"}",
//                    CustomersModule.OwnerInfoType.soleUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.bargainPriceUser?:"",
//                    "推荐人",
//                    "${ownerInfo?.bargainPriceUserName?:"${if (TextUtils.isEmpty(ownerInfo?.bargainPriceUser)) "请选择 推荐人" else ""}"}",
//                    CustomersModule.OwnerInfoType.bargainPriceUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.blockUser?:"",
//                    "封盘人",
//                    "${ownerInfo?.blockUserName?:""}",
//                    CustomersModule.OwnerInfoType.blockUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.openUser?:"",
//                    "开盘人",
//                    "${ownerInfo?.openUserName?:""}",
//                    CustomersModule.OwnerInfoType.openUser
//                )
//            )
//        ),
//        ItemHouseDetail(
//            imagUrls = imagUrls!!
//        ),
//        ItemHouseDetail(
//            referUrls = referUrls!!
//        ),
//        ItemHouseDetail(
//            mapInfo = ItemHouseDetail.MapInfo(
//                houseDetail?.villageInfoResponse?.latitude,
//                houseDetail?.villageInfoResponse?.longitude,
//                houseDetail?.villageInfoResponse?.name
//            )
//        )
    )
    return houseList
}

fun MutableList<String>.getString(split: String): String {
    var stringBuffer = StringBuffer()
    forEach {
        stringBuffer.append(split)
        stringBuffer.append(it)
    }
    return if (stringBuffer.length > 1) {
        stringBuffer.substring(1)
    } else {
        ""
    }
}
