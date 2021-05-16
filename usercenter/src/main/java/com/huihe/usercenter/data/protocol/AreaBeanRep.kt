package com.huihe.usercenter.data.protocol

class AreaBeanRep(
    val list:MutableList<AreaBean>,
    val totalCount:Int
){
    data class AreaBean(

        /**
         * address : null
         * createTime : 2021-03-30 21:50:35
         * districtId : 10
         * districtName : 杨浦区
         * firstPy : csxqtj
         * fitPrimarySchool : null
         * hot : 0
         * id : 2934
         * latitude : null
         * location : null
         * longitude : null
         * name : 测试小区添加
         * schoolDistrictId : null
         * schoolDistrictName : null
         * url : null
         * villagePropertyYear : null
         * zoneId : 33
         * zoneName : 鞍山
         */

        val address: String? = null,
        val createTime: String? = null,
        val districtId: String? = null,
        val districtName: String? = null,
        val firstPy: String? = null,
        val fitPrimarySchool: String? = null,
        val hot: String? = null,
        val id: String? = null,
        val latitude: String? = null,
        val location: String? = null,
        val longitude: String? = null,
        val name: String? = null,
        val schoolDistrictId: String? = null,
        val schoolDistrictName: String? = null,
        val url: String? = null,
        val villagePropertyYear: String? = null,
        val zoneId: String? = null,
        val zoneName: String? = null
    )
}