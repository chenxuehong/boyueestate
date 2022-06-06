package com.huihe.boyueentities.protocol.user

data class DeptUserRep(
    val children:MutableList<DeptUser>?,
    val label:String?,
    val value:String?
){
    data class DeptUser(
        val label:String?,
        val uid:String?,
        val value:String?
    )
}