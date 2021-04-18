package com.huihe.module_home.data.protocol

data class GetMoreCustomersListReq(
    val myHouse: Int,
    val hasKey: Int,
    val hasSole: Int,
    val myMaintain: Int,
    val isCirculation: Int,
    val entrustHouse: Int,
    val myCollect: Int,
    val floorageRanges: Map<String, String>,
    val roomNumRanges: Map<String, String>
)
