package com.huihe.boyueentities.protocol.customer

data class SortReq(
                    val createDateAsc: Int? = null, //录入日期排序：0升序1降序
                    val followUpDateAsc: Int? = null //最后跟进时间排序：0升序1降序
 ) : ISearchResult
