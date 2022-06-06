package com.huihe.boyueentities.protocol.home

data class FollowRep(
    val list:MutableList<FollowBean>,
    val totalCount:Int

){
    data class FollowBean(
        var createTime:String,
        var createUser:String,
        var createUserName:String,
        var deptId:String,
        var deptName:String,
        var followContent:String,
        var houseCode:String,
        var houseId:String,
        var houseInfo:String,
        var id:String
    )
}
