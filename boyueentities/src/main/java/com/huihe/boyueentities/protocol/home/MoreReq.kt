package com.huihe.boyueentities.protocol.home

data class MoreReq(
    var hFlag: Int?=0,
    var days: Int?=null,
    var myHouse: Int?=null,
    var hasKey: Int?=null,
    var hasSole: Int?=null,
    var myMaintain: Int?=null,
    var isCirculation: Int?=null,
    var entrustHouse: Int?=null,
    var myCollect: Int?=null,
    var floorageRanges: MutableList<FloorageReq>?=null,
    var roomNumRanges: MutableList<RoomNumReq>?=null
) : ISearchResult
