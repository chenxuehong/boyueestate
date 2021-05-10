package com.huihe.module_home.ext

import android.content.Context
import android.text.TextUtils
import android.widget.Button
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.AreaBean
import com.huihe.module_home.data.protocol.AreaBeanConvertModel
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.ui.adpter.MoreSearchAdapter
import com.kotlin.base.utils.DateUtils
import java.math.BigDecimal


fun getConvertData(data: MutableList<AreaBean>?): MutableList<AreaBeanConvertModel> {
    var dataList: MutableList<AreaBeanConvertModel> = mutableListOf()
    data?.apply {
        forEach { item ->
            var hasItem = hasItem(item, dataList)
            if (hasItem == null) {
                val zoneBeanList = mutableListOf<AreaBeanConvertModel.ZoneBean>()
                addZoneBeanList(zoneBeanList, item)
                var areaBeanConvertModel = AreaBeanConvertModel(
                    item.districtId!!,
                    item.districtName!!,
                    zoneBeanList
                )
                dataList?.add(areaBeanConvertModel)
            } else {
                var zoneBeanList = hasItem.zoneBean
                addZoneBeanList(zoneBeanList, item)
            }
        }
    }
    return dataList
}

fun getConvertProvinceList(data: MutableList<AreaBean>?): ArrayList<Province> {
    var dataList: ArrayList<Province> = ArrayList()
    data?.apply {
        forEach { item ->
            var hasItem = hasProvinceItem(item, dataList)
            if (hasItem == null) {
                val cities = mutableListOf<City>()
                addCityList(cities, item)
                var province = Province(
                    item.districtId!!,
                    item.districtName!!
                )
                province.cities = cities
                dataList?.add(province)
            } else {
                var cities = hasItem.cities
                addCityList(cities, item)
            }
        }
    }
    return dataList
}

fun addCityList(cities: MutableList<City>, item: AreaBean) {
    var hasCityItem = hasCityItem(item, cities)
    if (hasCityItem == null) {
        var counties = mutableListOf<County>()
        addCountyItem(item, counties)
        var city =
            City(item.zoneId, item.zoneName)
        city.counties = counties
        city.provinceId = item.districtId
        cities?.add(city)
    } else {
        var counties = hasCityItem.counties
        addCountyItem(item, counties)
        var city =
            City(item.zoneId, item.zoneName)
        city.counties = counties
        cities?.add(city)
    }
}

fun addCountyItem(item: AreaBean, counties: MutableList<County>) {
    var hasDistrictItem = hasCountyItem(item, counties)
    if (hasDistrictItem == null) {
        var county = County(item.id, item.name)
        county.cityId = item.zoneId
        counties?.add(county)
    }
}

fun hasCountyItem(areaBean: AreaBean, counties: MutableList<County>): County? {
    counties?.forEach { item ->
        if (item?.areaId?.equals(areaBean?.id)!!) {
            return item
        }
    }
    return null
}

fun hasCityItem(areaBean: AreaBean, cities: MutableList<City>): City? {
    cities?.forEach { item ->
        if (item?.areaId.equals(areaBean?.zoneId)) {
            return item
        }
    }
    return null
}

fun hasProvinceItem(areaBean: AreaBean, dataList: MutableList<Province>): Province? {
    dataList?.forEach { item ->
        if (areaBean?.districtId?.equals(item?.areaId)!!) {
            return item
        }
    }
    return null
}

fun hasItem(
    areaBean: AreaBean,
    dataList: MutableList<AreaBeanConvertModel>
): AreaBeanConvertModel? {
    dataList.forEach { item ->
        if (item?.districtId?.equals(areaBean.districtId)!!) {
            return item
        }
    }
    return null
}

fun hasZoneItem(
    areaBean: AreaBean?,
    zoneBeanList: MutableList<AreaBeanConvertModel.ZoneBean>?
): AreaBeanConvertModel.ZoneBean? {
    zoneBeanList?.forEach { item ->
        if (item?.zoneId?.equals(areaBean?.zoneId)!!) {
            return item
        }
    }
    return null
}

fun hasDistrictItem(
    areaBean: AreaBean?,
    districtBeanList: MutableList<AreaBeanConvertModel.DistrictBean>?
): AreaBeanConvertModel.DistrictBean? {
    districtBeanList?.forEach { item ->
        if (item?.id?.equals(areaBean?.id)!!) {
            return item
        }
    }
    return null
}

private fun addZoneBeanList(
    zoneBeanList: MutableList<AreaBeanConvertModel.ZoneBean>?,
    item: AreaBean
) {
    var hasZoneItem = hasZoneItem(item, zoneBeanList)
    if (hasZoneItem == null) {
        var districtBeanList = mutableListOf<AreaBeanConvertModel.DistrictBean>()
        addDistrictItem(item, districtBeanList)
        var zoneBean =
            AreaBeanConvertModel.ZoneBean(item.zoneId, item.zoneName, districtBeanList)
        zoneBeanList?.add(zoneBean)
    } else {
        var districtBeanList = hasZoneItem.districtBean
        addDistrictItem(item, districtBeanList)
        var zoneBean =
            AreaBeanConvertModel.ZoneBean(item.zoneId, item.zoneName, districtBeanList)
        zoneBeanList?.add(zoneBean)
    }
}

private fun addDistrictItem(
    item: AreaBean,
    districtBeanList: MutableList<AreaBeanConvertModel.DistrictBean>?
) {
    var hasDistrictItem = hasDistrictItem(item, districtBeanList)
    if (hasDistrictItem == null) {
        districtBeanList?.add(AreaBeanConvertModel.DistrictBean(item.id, item.name))
    }
}

var list: MutableList<AreaBeanConvertModel.DistrictBean> = mutableListOf()

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

var mCheckedItem: AreaBeanConvertModel? = null

fun setItemAreaChecked(
    zoneBean: AreaBeanConvertModel.DistrictBean?,
    isChecked: Boolean,
    checkedItem: AreaBeanConvertModel?
): MutableList<AreaBeanConvertModel.DistrictBean> {
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
                houseDetail?.ownerTel,
                houseDetail?.ownerName
            )
        ),
        // 房源详情
        ItemHouseDetail(
            detailInfoList = mutableListOf(
                ItemHouseDetail.DetailInfo(
                    "房源编号",
                    houseDetail?.houseCode
                ),
                ItemHouseDetail.DetailInfo(
                    "楼层",
                    "${houseDetail?.floor}/${houseDetail?.totalFloor}"
                ),
                ItemHouseDetail.DetailInfo(
                    "房龄",
                    houseDetail?.housePropertyYear
                ),
                ItemHouseDetail.DetailInfo(
                    "建筑面积",
                    "${houseDetail?.floorage}"
                ),
                ItemHouseDetail.DetailInfo(
                    "是否抵押",
                    houseDetail?.loanState
                ),
                ItemHouseDetail.DetailInfo(
                    "房东底价",
                    houseDetail?.ownerLowPrice
                ),
                ItemHouseDetail.DetailInfo(
                    "产权人数",
                    houseDetail?.propertyRightUserSize
                ),
                ItemHouseDetail.DetailInfo(
                    "房屋朝向",
                    houseDetail?.orientation
                ),
                ItemHouseDetail.DetailInfo(
                    "买入价",
                    houseDetail?.buyPrice
                ),
                ItemHouseDetail.DetailInfo(
                    "梯户",
                    "${houseDetail?.stairs ?: "0"}梯${houseDetail?.houseHolds ?: "0"}户"
                ),
                ItemHouseDetail.DetailInfo(
                    "看房时间",
                    houseDetail?.seeTime
                ),
                ItemHouseDetail.DetailInfo(
                    "对口学区",
                    houseDetail?.villageInfoResponse?.schoolDistrictName
                ),
                ItemHouseDetail.DetailInfo(
                    "是否满五唯一",
                    houseDetail?.isOnlyHouse
                ),
                ItemHouseDetail.DetailInfo(
                    "装修情况",
                    houseDetail?.decorat
                )
            )
        ),
        // 备注
        ItemHouseDetail(rewarks = houseDetail?.remarks),
        // 房源相关人
        ItemHouseDetail(
            ownerInfoList = mutableListOf(
                ItemHouseDetail.OwnerInfo(
                    "录入人",
                    "${ownerInfo?.createUserName} ${DateUtils.stringToString(
                        ownerInfo?.createTime!!,
                        DateUtils.datePattern,
                        DateUtils.FORMAT_SHORT
                    )}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "维护人",
                    "${ownerInfo?.maintainUserName}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "图片人",
                    "${ownerInfo?.imageUserName}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "委托人",
                    "${ownerInfo?.entrustUserName}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "钥匙人",
                    "${ownerInfo?.haveKeyUserName}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "独家人",
                    "${ownerInfo?.soleUserName}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "推荐人",
                    "${ownerInfo?.bargainPriceUserName}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "封盘人",
                    "${ownerInfo?.blockUserName}"
                ),
                ItemHouseDetail.OwnerInfo(
                    "开盘人",
                    "${ownerInfo?.openUserName}"
                )
            )
        ),
        ItemHouseDetail(
            imagUrls = imagUrls!!
        ),
        ItemHouseDetail(
            referUrls = referUrls!!
        ),
        ItemHouseDetail(
            mapInfo = ItemHouseDetail.MapInfo(
                houseDetail?.villageInfoResponse?.latitude,
                houseDetail?.villageInfoResponse?.longitude
            )
        )
    )
    return houseList
}

fun MutableList<String>.getString(split:String):String{
    var stringBuffer = StringBuffer()
    forEach {
        stringBuffer.append(split)
        stringBuffer.append(it)
    }
    return if (stringBuffer.length>1){
        stringBuffer.substring(1)
    }else{
        ""
    }
}
