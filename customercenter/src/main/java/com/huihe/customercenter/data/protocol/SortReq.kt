package com.huihe.customercenter.data.protocol

import com.huihe.customercenter.ui.widget.ISearchResult

data class SortReq(
                    val defaultOrder: Int? = null, //当前是否默认排序：0否1是
                    val createTimeOrder: Int? = null, //录入日期排序：0升序1降序
                    val latestFollowTimeOrder: Int? = null //最后跟进时间排序：0升序1降序
 ) : ISearchResult
