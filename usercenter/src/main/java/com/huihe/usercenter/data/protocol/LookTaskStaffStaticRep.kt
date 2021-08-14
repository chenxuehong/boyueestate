package com.huihe.usercenter.data.protocol

data class LookTaskStaffStaticRep(
    var data: MutableList<LookTaskStaffStatic>
){
    data class LookTaskStaffStatic(
        var count:Int,
        var status:Int
    )
    data class StaffStatic(
        var to_start:Int,
        var take_look:Int,
        var in_summary:Int,
        var under_review:Int
    )
}