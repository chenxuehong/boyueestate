package com.huihe.module_home.data.protocol

import java.math.BigDecimal

data class ItemHouseDetail(
    val bannerList: MutableList<HouseDetail.ImagUrlsBean>? = null,
    val basicInfo: BasicInfo? = null,
    val detailInfoList: MutableList<DetailInfo> = mutableListOf(),
    val rewarks: String? = null,
    val ownerInfoList: MutableList<OwnerInfo> = mutableListOf(),
    val imagUrls: MutableList<HouseDetail.ImagUrlsBean> = mutableListOf(),
    val referUrls: MutableList<HouseDetail.ReferUrlsBean> = mutableListOf(),
    val mapInfo: MapInfo? = null
) {
    data class BasicInfo(
        var villageName: String?,
        var building: String?,
        var hNum: String?,
        var price: BigDecimal?,
        var floorage: Int?,
        var hShape: String?,
        var label: String?,
        var ownerTel: String?,
        var ownerName: String?
    )

    data class DetailInfo(
        var leftTitle: String?,
        var content: String?
    )

    data class OwnerInfo(
        var leftTitle: String?,
        var content: String?
    )

   data class MapInfo(
       var latitude: Double?,
       var longitude: Double?
   )
}
