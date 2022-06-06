package com.huihe.boyueentities.protocol.user

data class MineLookTaskRep(
    var list:MutableList<MineLookTask>,
    var totalCount:Long
){
    data class MineLookTask(
        var accompanyUser:String?,
        var createUser:String?,
        var customerCode:String?,
        var customerName:String?,
        var deptName:String?,
        var houseCode:String?,
        var id:String?,
        var isQualified:String?,
        var lookTime:String?,
        var status:Int?,
        var takeLookUser:String?,
        var taskNo:String?
    )
}