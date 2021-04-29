package com.huihe.module_home.service

import com.huihe.module_home.data.protocol.CustomerWrapper
import io.reactivex.Observable

/*
    商品 业务层 接口
 */
interface CustomersService {

    /*
        获取房源列表 更多
    通用：我的房源  钥匙  独家    我的维护盘  流通   委托房源   我的收藏
    面积：50以下  50-70  70-90  90-110  110-130  130-150  150以上（多选）
    房型    一室  二室  三室  四室  五室  五室以上（多选）
     */

    fun getMoreCustomersList(
        pageNo: Int? = null, pageSize: Int? = null,
        myHouse: Int?, hasKey: Int?, hasSole: Int?,
        myMaintain: Int?, isCirculation: Int?,
        entrustHouse: Int?, myCollect: Int?,
        floorageRanges: Map<String, String>?,
        roomNumRanges: Map<String, String>?
    ): Observable<CustomerWrapper?>

    /**
     * 获取房源列表 更多
     *  状态 :全部  有效  他售  我售 暂缓    新上 ：今日新上 三日新上   一周新上  类型：出售  出租  租售
     */
//    fun getMoreCustomersList(dataType: Int): Observable<MutableList<Customer>?>

}
