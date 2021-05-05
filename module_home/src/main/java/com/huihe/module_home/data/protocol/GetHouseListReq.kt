package com.huihe.module_home.data.protocol


data class GetHouseListReq(
    val pageNo: Int? = 1,
    val pageSize: Int? = 30,
    val floorOrder: Int? = 0, //楼层升降排序，0 升序，1 降序
    val latestFollowTimeOrder: Int? = 0, //最后跟进时间排序：0升序1降序
    val defaultOrder: Int? = 0, //当前是否默认排序：0否1是
    val createTimeOrder: Int? = 0, //录入日期排序：0升序1降序
    val buildingOrder: Int? = 0, //座栋排序：0升序1降序
    val floorageOrder: Int? = 0, //面积排序：0升序1降序
    val priceOrder: Int? = 0, //售价排序：0升序1降序
    val floorRanges :MutableList<FloorReq>?,
    val priceRanges: MutableList<PriceReq>?,
    val hFlag: Int?,
    val days: Int?,
    val myHouse: Int?,
    val hasKey: Int?,
    val hasSole: Int?,
    val myMaintain: Int?,
    val isCirculation: Int?,
    val entrustHouse: Int?,
    val myCollect: Int?,
    val floorageRanges: MutableList<FloorageReq>?,
    val roomNumRanges: MutableList<RoomNumReq>?,
    val villageIds: MutableList<String>?
)
