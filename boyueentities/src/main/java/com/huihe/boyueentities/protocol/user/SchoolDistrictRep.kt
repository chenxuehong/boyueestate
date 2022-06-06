package com.huihe.boyueentities.protocol.user

data class SchoolDistrictRep(
    var list:MutableList<SchoolDistrict>,
    var totalCount:Int
){
    data class SchoolDistrict(
        var createTime:String?,
        var createUser:String?,
        var id:String?,
        var schoolDistrictName:String?,
        var updateTime:String?,
        var updateUser:String?
    )
}
