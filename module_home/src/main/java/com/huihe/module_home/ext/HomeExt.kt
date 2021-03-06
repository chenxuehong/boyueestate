package com.huihe.module_home.ext

import android.content.Context
import android.text.TextUtils
import android.widget.Button
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.HouseDetail
import com.huihe.boyueentities.protocol.home.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.adapter.MoreSearchAdapter
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.widgets.picker.WheelPicker.entity.City
import com.kotlin.base.widgets.picker.WheelPicker.entity.County
import com.kotlin.base.widgets.picker.WheelPicker.entity.Province
import com.huihe.boyueentities.protocol.common.District


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
        // ?????????
        ItemHouseDetail(bannerList = imagUrls),
        // ????????????
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
                houseDetail?.ownerTel,
                houseDetail?.isCirculation
            )
        ),
        // ????????????
        ItemHouseDetail(
            detailInfoList = mutableListOf(

                ItemHouseDetail.DetailInfo(
                    "????????????",
                    "",
                    true
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.ownerName?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    "",
                    true
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.houseCode?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "??????",
                    "${houseDetail?.floor}/${houseDetail?.totalFloor}"
                ),
                ItemHouseDetail.DetailInfo(
                    "??????",
                    houseDetail?.housePropertyYear?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    "${houseDetail?.floorage}"
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.loanState?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.ownerLowPrice?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.propertyRightUserSize?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.orientation?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "?????????",
                    houseDetail?.buyPrice?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "??????",
                    "${houseDetail?.stairs ?: "0"}???${houseDetail?.houseHolds ?: "0"}???"
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.seeTime?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.villageInfoResponse?.schoolDistrictName?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "??????????????????",
                    houseDetail?.isOnlyHouse?:""
                ),
                ItemHouseDetail.DetailInfo(
                    "????????????",
                    houseDetail?.decorat?:""
                )
            )
            ,
            ownerInfoList = mutableListOf(
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.createUser ?: "",
                    "?????????",
                    "${ownerInfo?.createUserName} ${DateUtils.stringToString(
                        ownerInfo?.createTime!!,
                        DateUtils.datePattern,
                        DateUtils.FORMAT_SHORT
                    )}",
                    CustomersModule.OwnerInfoType.createUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.maintainUser ?: "",
                    "?????????",
                    "${ownerInfo?.maintainUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.maintainUser)) "????????? ?????????" else ""}"}",
                    CustomersModule.OwnerInfoType.maintainUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.imageUser ?: "",
                    "?????????",
                    "${ownerInfo?.imageUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.imageUser)) "????????? ?????????" else ""}"}",
                    CustomersModule.OwnerInfoType.imageUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.entrustUser ?: "",
                    "?????????",
                    "${ownerInfo?.entrustUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.entrustUser)) "????????? ?????????" else ""}"}",
                    CustomersModule.OwnerInfoType.entrustUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.haveKeyUser ?: "",
                    "?????????",
                    "${ownerInfo?.haveKeyUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.haveKeyUser)) "????????? ?????????" else ""}"}",
                    CustomersModule.OwnerInfoType.haveKeyUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.soleUser ?: "",
                    "?????????",
                    "${ownerInfo?.soleUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.soleUser)) "????????? ?????????" else ""}"}",
                    CustomersModule.OwnerInfoType.soleUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.bargainPriceUser ?: "",
                    "?????????",
                    "${ownerInfo?.bargainPriceUserName
                        ?: "${if (TextUtils.isEmpty(ownerInfo?.bargainPriceUser)) "????????? ?????????" else ""}"}",
                    CustomersModule.OwnerInfoType.bargainPriceUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.blockUser ?: "",
                    "?????????",
                    "${ownerInfo?.blockUserName ?: ""}",
                    CustomersModule.OwnerInfoType.blockUser
                ),
                ItemHouseDetail.OwnerInfo(
                    ownerInfo?.openUser ?: "",
                    "?????????",
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
//        // ??????
//        ItemHouseDetail(rewarks = houseDetail?.remarks),
//        // ???????????????
//        ItemHouseDetail(
//            ownerInfoList = mutableListOf(
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.createUser?:"",
//                    "?????????",
//                    "${ownerInfo?.createUserName} ${DateUtils.stringToString(
//                        ownerInfo?.createTime!!,
//                        DateUtils.datePattern,
//                        DateUtils.FORMAT_SHORT
//                    )}",
//                    CustomersModule.OwnerInfoType.createUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.maintainUser?:"",
//                    "?????????",
//                    "${ownerInfo?.maintainUserName?:"${if (TextUtils.isEmpty(ownerInfo?.maintainUser)) "????????? ?????????" else ""}"}",
//                    CustomersModule.OwnerInfoType.maintainUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.imageUser?:"",
//                    "?????????",
//                    "${ownerInfo?.imageUserName?:"${if (TextUtils.isEmpty(ownerInfo?.imageUser)) "????????? ?????????" else ""}"}",
//                    CustomersModule.OwnerInfoType.imageUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.entrustUser?:"",
//                    "?????????",
//                    "${ownerInfo?.entrustUserName?:"${if (TextUtils.isEmpty(ownerInfo?.entrustUser)) "????????? ?????????" else ""}"}",
//                    CustomersModule.OwnerInfoType.entrustUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.haveKeyUser?:"",
//                    "?????????",
//                    "${ownerInfo?.haveKeyUserName?:"${if (TextUtils.isEmpty(ownerInfo?.haveKeyUser)) "????????? ?????????" else ""}"}",
//                    CustomersModule.OwnerInfoType.haveKeyUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.soleUser?:"",
//                    "?????????",
//                    "${ownerInfo?.soleUserName?:"${if (TextUtils.isEmpty(ownerInfo?.soleUser)) "????????? ?????????" else ""}"}",
//                    CustomersModule.OwnerInfoType.soleUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.bargainPriceUser?:"",
//                    "?????????",
//                    "${ownerInfo?.bargainPriceUserName?:"${if (TextUtils.isEmpty(ownerInfo?.bargainPriceUser)) "????????? ?????????" else ""}"}",
//                    CustomersModule.OwnerInfoType.bargainPriceUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.blockUser?:"",
//                    "?????????",
//                    "${ownerInfo?.blockUserName?:""}",
//                    CustomersModule.OwnerInfoType.blockUser
//                ),
//                ItemHouseDetail.OwnerInfo(
//                    ownerInfo?.openUser?:"",
//                    "?????????",
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

