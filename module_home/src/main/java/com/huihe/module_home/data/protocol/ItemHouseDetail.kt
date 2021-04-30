package com.huihe.module_home.data.protocol

data class ItemHouseDetail(
    val bannerList:MutableList<BannerBean>?,
    val basicInfo:BasicInfo?,
    val detailInfoList:MutableList<DetailInfo>?,
    val ownerInfoList:MutableList<OwnerInfo>?,
    val photoInfoList:MutableList<PhotoInfo>?,
    val mapInfo:MapInfo?
){
    class BannerBean()
    class BasicInfo()
    class DetailInfo()
    class OwnerInfo()
    class PhotoInfo()
    class MapInfo()
}
