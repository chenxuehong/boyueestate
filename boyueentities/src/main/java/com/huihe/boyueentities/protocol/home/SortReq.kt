package com.huihe.boyueentities.protocol.home

data class SortReq( val floorOrder: Int? = null, //楼层升降排序，0 升序，1 降序
                    val latestFollowTimeOrder: Int? = null, //最后跟进时间排序：0升序1降序
                    val defaultOrder: Int = 1, //当前是否默认排序：0否1是
                    val createTimeOrder: Int? = null, //录入日期排序：0升序1降序
                    val buildingOrder: Int? = null, //座栋排序：0升序1降序
                    val floorageOrder: Int? = null, //面积排序：0升序1降序
                    val priceOrder: Int? = null //售价排序：0升序1降序
 ) : ISearchResult
