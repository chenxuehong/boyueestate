package com.huihe.customercenter.data.protocol

import com.huihe.customercenter.ui.widget.ISearchResult

data class StatusRep(
    var dataKey:Int?,
    var dataType:String?,
    var dataValue:String?,
    var description:String?,
    var id:String?,
    var sorts:String?
):ISearchResult