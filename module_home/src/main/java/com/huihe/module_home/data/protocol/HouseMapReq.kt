package com.huihe.module_home.data.protocol

data class HouseMapReq(
    val longitudeRanges:MutableList<LongitudeBean>?=null,
    val latitudeRanges:MutableList<LatitudeBean>?=null,
    val floorRanges :MutableList<FloorReq>?=null,
    val floorageRanges: MutableList<FloorageReq>?=null,
    val priceRanges: MutableList<PriceReq>??=null,
    val roomNumRanges: MutableList<RoomNumReq>?=null,
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