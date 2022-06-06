package com.huihe.boyueentities.protocol.customer

data class MoreReq(
    var userType: Int ?=null,
    var isOwn: Int ?=null,
    var isTakeLook: Int ?=null,
    var isCollection: Int ?=null
): ISearchResult