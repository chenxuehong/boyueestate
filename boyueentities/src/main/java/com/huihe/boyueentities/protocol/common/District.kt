package com.huihe.boyueentities.protocol.common

class District(

    val createTime: String? = null,
    val id: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val name: String? = null,
    val showOrder: String? = null,
    val zones:MutableList<ZoneBean>

){
    data class ZoneBean(
        var createTime:String?,
        var districtId:String?,
        var id:String?,
        var latitude:String?,
        var longitude:String?,
        var name:String?,
        var villages:MutableList<VillageBean>?
    ){
        data class VillageBean(
            var address:String?,
            var createTime:String?,
            var districtId:String?,
            var districtName:String?,
            var firstPy:String?,
            var fitPrimarySchool:String?,
            var hot:String?,
            var id:String?,
            var latitude:String?,
            var location:String?,
            var longitude:String?,
            var name:String?,
            var schoolDistrictId:String?,
            var schoolDistrictName:String?,
            var url:String?,
            var villagePropertyYear:String?,
            var zoneId:String?,
            var zoneName:String?
        )
    }
}
