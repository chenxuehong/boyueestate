package com.huihe.module_home.data.protocol

data class HouseMapReq(
    val longitudeRanges:MutableList<LongitudeBean>?=null,
    val latitudeRanges:MutableList<LatitudeBean>?=null,
    val floorRanges :MutableList<FloorReq>?=null,
    val priceRanges: MutableList<PriceReq>??=null,
    var hFlag: Int?=null,
    var days: Int?=null,
    var myHouse: Int?=null,
    var hasKey: Int?=null,
    var hasSole: Int?=null,
    var myMaintain: Int?=null,
    var isCirculation: Int?=null,
    var entrustHouse: Int?=null,
    var myCollect: Int?=null,
    var floorageRanges: MutableList<FloorageReq>?=null,
    var roomNumRanges: MutableList<RoomNumReq>?=null,
    var villageIds: MutableList<String>?=null,
    val type:Int?=0
){
    data class LongitudeBean(
        val minLongitude:Double?=0.0,
        val maxLongitude:Double?=0.0
    )
    data class LatitudeBean(
        val minLatitude:Double?=0.0,
        val maxLatitude:Double?=0.0
    )
}