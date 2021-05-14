package com.huihe.customercenter.data.protocol

import com.huihe.customercenter.ui.widget.ISearchResult

data class MoreReq(
    var userType: Int ?=null,
    var isOwn: Int ?=null,
    var isTakeLook: Int ?=null,
    var isCollection: Int ?=null
): ISearchResult