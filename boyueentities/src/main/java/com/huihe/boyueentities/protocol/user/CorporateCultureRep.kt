package com.huihe.boyueentities.protocol.user

data class CorporateCultureRep(
   var list:MutableList<CorporateCulture>,
   var totalCount:Int
){
    data class CorporateCulture(
        var content:String?,
        var counts:String?,
        var coverImage:String?,
        var coverImageUrl:String?,
        var createTime:String?,
        var createUser:String?,
        var createUserName:String?,
        var id:String?,
        var rigs:String?,
        var title:String?,
        var workNo:String?
    )
}
