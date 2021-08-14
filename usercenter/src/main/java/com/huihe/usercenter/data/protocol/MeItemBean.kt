package com.huihe.usercenter.data.protocol

data class MeItemBean(
    var title:String,
    var list: MutableList<ItemData>
){
    data class ItemData(
        var title:String,
        var icon:Int,
        var unread:Int=0
    )
}