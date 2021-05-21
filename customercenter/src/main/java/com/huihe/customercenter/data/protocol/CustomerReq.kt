package com.huihe.customercenter.data.protocol

data class CustomerReq(
    val page:Int=1,
    val pageSize:Int=30,
    val customerType:Int=1,
    val createUserList: String? = null,
    val status: Int? = null,
    val userType: Int? = null,
    var isOwn: Int ?=null,
    var isTakeLook: Int ?=null,
    var isCollection: Int ?=null,
    val createDateAsc: Int? = null, //录入日期排序：0升序1降序
    val followUpDateAsc: Int? = null, //最后跟进时间排序：0升序1降序
    var customerCode:String?=null,
    var mobile:String?=null,
    var customerName:String?=null,
    var demandBeat:String?=null,
    var remarks:String?=null
)