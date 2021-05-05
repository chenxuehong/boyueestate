package com.huihe.module_home.data.protocol

data class AreaBeanConvertModel(
    val districtId:String?,
    val districtName:String?,
    val zoneBean:MutableList<ZoneBean>?
){
    data class ZoneBean(
        val zoneId:String?,
        val zoneName:String?,
        var districtBean:MutableList<DistrictBean>?
        )

    data class DistrictBean(
        val id:String?,
        val name:String?,
        var isChecked:Boolean?=false)
}
