package com.huihe.boyueentities.protocol.home

import java.math.BigDecimal

data class HouseDetail (

    /**
     * bargainPriceUser : b47645c6c9144a0db608d5356c933988
     * building : 12
     * buyPrice : 215万
     * changePrice : 10
     * characteristic : 3333
     * coverImage : 16136555151574875100.jpg
     * coverImages : {"name":"16136555151574875100.jpg","url":"http://img.shanghaihouse.vip/16136555151574875100.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:LE_fMri6E2tm2-LMeQ0XWTbb4_Q="}
     * createTime : 2021-02-18 18:38:47
     * createUser : cf7a781555e637b6d7da059d87cc2dba
     * createUserName : 朱其云
     * decorat : null
     * entrustUser : null
     * floor : 6
     * floorage : 72
     * hFlag : 2
     * hNum : 0603甲-1
     * hShape : 2室1厅1卫
     * houseCode : FY-21-87912
     * houseHolds : 6
     * housePropertyYear : 1999年
     * id : 110799
     * imagUrl : 16136555151574875100.jpg;16136555151574875105.jpg;16136555151574875101.jpg;16136555151574875106.jpg;16136555151574875107.jpg;16136555161574875110.jpg;16136555161574875111.jpg;16136555151574875104.jpg;16136555151574875102.jpg;4c759903-8a85-498a-9212-c2e79c88b4d9.jpg;AF3-8833CD0A2497/Documents/com_tencent_imsdk_data/image/huihe_589610241;631ecc00-894a-11eb-9654-179bba19d5eb
     * imagUrls : [{"name":"16136555151574875100.jpg","url":"http://img.shanghaihouse.vip/16136555151574875100.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:LE_fMri6E2tm2-LMeQ0XWTbb4_Q="},{"name":"16136555151574875105.jpg","url":"http://img.shanghaihouse.vip/16136555151574875105.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:I4aCS_0lk7sD8irvG1TP-RxL1BE="},{"name":"16136555151574875101.jpg","url":"http://img.shanghaihouse.vip/16136555151574875101.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:BhdzZuNOUBh2s3C5Uw1APkExsPU="},{"name":"16136555151574875106.jpg","url":"http://img.shanghaihouse.vip/16136555151574875106.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:wYae8gBQyLnKmr8BD-nPV1vvHQk="},{"name":"16136555151574875107.jpg","url":"http://img.shanghaihouse.vip/16136555151574875107.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:nOCY6-luzqQWnDzQzPC830u0xZY="},{"name":"16136555161574875110.jpg","url":"http://img.shanghaihouse.vip/16136555161574875110.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:uZKzg8VaBFG-tgSSCuUNGcnQEbM="},{"name":"16136555161574875111.jpg","url":"http://img.shanghaihouse.vip/16136555161574875111.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:xIEF02505vvpxJp2I88f1m9ZgcE="},{"name":"16136555151574875104.jpg","url":"http://img.shanghaihouse.vip/16136555151574875104.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:Ygmgb1OeLG9ssXAD2Z9toRjVnj8="},{"name":"16136555151574875102.jpg","url":"http://img.shanghaihouse.vip/16136555151574875102.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:3adQszOQVqiUKbj2Gi1fihDFuDo="},{"name":"4c759903-8a85-498a-9212-c2e79c88b4d9.jpg","url":"http://img.shanghaihouse.vip/4c759903-8a85-498a-9212-c2e79c88b4d9.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:0iZAcMwTSAo25KIjY2-TxvC3YKo="},{"name":"AF3-8833CD0A2497/Documents/com_tencent_imsdk_data/image/huihe_589610241","url":"http://img.shanghaihouse.vip/AF3-8833CD0A2497%2FDocuments%2Fcom_tencent_imsdk_data%2Fimage%2Fhuihe_589610241-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:MWpjT5ndOMNaXq5VTX22rAEaYaw="},{"name":"631ecc00-894a-11eb-9654-179bba19d5eb","url":"http://img.shanghaihouse.vip/631ecc00-894a-11eb-9654-179bba19d5eb-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:_o-sSBgFeeOw_yuaHoQKxgNKo2M="}]
     * imageUser : 1
     * isCirculation : 1
     * isCollect : 1
     * isOnlyHouse : 三月底满五，不唯一
     * label : 荐;带[3];维;委;独;图
     * loanState : 正常贷款，随时可以还
     * maintainUser : b47645c6c9144a0db608d5356c933988
     * orientation : 南
     * ownerLowPrice : null
     * ownerName : 张三
     * ownerTel : null
     * price : 410
     * propertyRightUserSize : 2
     * referUrl : d42fb620-84d1-11eb-9107-016371fe6e2e
     * referUrls : [{"name":"d42fb620-84d1-11eb-9107-016371fe6e2e","url":"http://img.shanghaihouse.vip/d42fb620-84d1-11eb-9107-016371fe6e2e-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:bx5QB7OjmOwbfIHDp4WgFUtk7fM="}]
     * remarks : null
     * rent : null
     * roomNum : 2
     * seeTime : null
     * soldReason : null
     * stairs : 2
     * totalFloor : 22
     * transactionType : 0
     * unit : 一单元
     * unitPrice : 56944
     * updateTime : 2021-05-04 18:03:14
     * updateUser : 1
     * updateUserName : 管理员
     * villageId : 2200
     * villageInfoResponse : {"address":null,"createTime":"2020-09-26 20:02:00","districtId":7,"districtName":"宝山区","firstPy":"jqxc","fitPrimarySchool":null,"hot":0,"id":2200,"latitude":"31.28625674288394","location":null,"longitude":"121.42256967849826","name":"金祁新城","schoolDistrictId":null,"schoolDistrictName":null,"url":"http://www.baidu.com","villagePropertyYear":"2222","zoneId":22,"zoneName":"大华"}
     */

    val bargainPriceUser: String? = null,
    val building: String? = null,
    val buyPrice: String ?=null,
    val changePrice: BigDecimal = BigDecimal(0),
    val characteristic: String? = null,
    val coverImage: String? = null,
    val coverImages: CoverImagesBean? = null,
    val createTime: String? = null,
    val createUser: String? = null,
    val createUserName: String? = null,
    val decorat: String? = null,
    val entrustUser: String? = null,
    val floor: Int = 0,
    val floorage: Float ?= null,
    val hFlag: Int = 0,
    val hNum: String? = null,
    val hShape: String? = null,
    val houseCode: String? = null,
    val houseHolds: String? = null,
    val housePropertyYear: String? = null,
    val id: String = "0",
    val imagUrl: String? = null,
    val imageUser: String? = null,
    val isCirculation: Int = 0,
    val isCollect: Int = 0,
    val isOnlyHouse: String? = null,
    val label: String? = null,
    val loanState: String? = null,
    val maintainUser: String? = null,
    val orientation: String? = null,
    val ownerLowPrice: String? = null,
    val ownerName: String? = null,
    var ownerTel: String? = null,
    val price: BigDecimal =BigDecimal(0),
    val propertyRightUserSize: String? = null,
    val referUrl: String? = null,
    val remarks: String? = null,
    val rent: String? = null,
    val roomNum: Int = 0,
    val seeTime: String? = null,
    val soldReason: String? = null,
    val stairs: String? = null,
    val totalFloor: Int = 0,
    val transactionType: Int = 0,
    val unit: String? = null,
    val unitPrice: String = "0",
    val updateTime: String? = null,
    val updateUser: String? = null,
    val updateUserName: String? = null,
    val villageId: String = "0",
    val villageInfoResponse: VillageInfoResponseBean? = null,
    val imagUrls: MutableList<ImagUrlsBean>? = null,
    val referUrls: MutableList<ReferUrlsBean>? = null,
    var ownerInfo: OwnerInfo? = null

){
    data class ReferUrlsBean (
        /**
         * name : d42fb620-84d1-11eb-9107-016371fe6e2e
         * url : http://img.shanghaihouse.vip/d42fb620-84d1-11eb-9107-016371fe6e2e-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:bx5QB7OjmOwbfIHDp4WgFUtk7fM=
         */

        val name: String? = null,
        val url: String? = null

    )
    data class ImagUrlsBean(
        /**
         * name : 16136555151574875100.jpg
         * url : http://img.shanghaihouse.vip/16136555151574875100.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:LE_fMri6E2tm2-LMeQ0XWTbb4_Q=
         */

        val name: String? = null,
        val url: String? = null
    )

    data class VillageInfoResponseBean (
        /**
         * address : null
         * createTime : 2020-09-26 20:02:00
         * districtId : 7
         * districtName : 宝山区
         * firstPy : jqxc
         * fitPrimarySchool : null
         * hot : 0
         * id : 2200
         * latitude : 31.28625674288394
         * location : null
         * longitude : 121.42256967849826
         * name : 金祁新城
         * schoolDistrictId : null
         * schoolDistrictName : null
         * url : http://www.baidu.com
         * villagePropertyYear : 2222
         * zoneId : 22
         * zoneName : 大华
         */

        val address: String? = null,
        val createTime: String? = null,
        val districtId: String = "0",
        val districtName: String? = null,
        val firstPy: String? = null,
        val fitPrimarySchool: String? = null,
        val hot: String = "0",
        val id: String = "0",
        val latitude: Double? = null,
        val location: String? = null,
        val longitude: Double? = null,
        val name: String? = null,
        val schoolDistrictId: String? = null,
        val schoolDistrictName: String? = null,
        val url: String? = null,
        val villagePropertyYear: String? = null,
        val zoneId: String = "0",
        val zoneName: String? = null
    )

    data class CoverImagesBean (
        /**
         * name : 16136555151574875100.jpg
         * url : http://img.shanghaihouse.vip/16136555151574875100.jpg-boyue?e=1620132403&token=QOlHbxcmp0q_0y_hkPZjAjOSIkQRQSrRF77uAbI5:LE_fMri6E2tm2-LMeQ0XWTbb4_Q=
         */

        val name: String? = null,
        val url: String? = null
    )
}