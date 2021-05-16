package com.huihe.usercenter.ext

import android.widget.Button
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import com.huihe.usercenter.data.protocol.AreaBeanRep
import com.huihe.usercenter.ui.widget.EditInputView
import com.kotlin.base.widgets.DefaultTextWatcher

/*
    扩展Button可用性
 */
fun Button.enable(et: EditInputView, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

fun getConvertProvinceList(data: MutableList<AreaBeanRep.AreaBean>?): ArrayList<Province> {
    var dataList: ArrayList<Province> = ArrayList()
    data?.apply {
        forEach { item ->
            var hasItem = hasProvinceItem(item, dataList)
            if (hasItem == null) {
                val cities = mutableListOf<City>()
                addCityList(cities, item)
                var province = Province(
                    item.districtId!!,
                    item.districtName!!
                )
                province.cities = cities
                dataList?.add(province)
            } else {
                var cities = hasItem.cities
                addCityList(cities, item)
            }
        }
    }
    return dataList
}

fun hasProvinceItem(areaBean: AreaBeanRep.AreaBean, dataList: MutableList<Province>): Province? {
    dataList?.forEach { item ->
        if (areaBean?.districtId?.equals(item?.areaId)!!) {
            return item
        }
    }
    return null
}

fun addCityList(cities: MutableList<City>, item: AreaBeanRep.AreaBean) {
    var hasCityItem = hasCityItem(item, cities)
    if (hasCityItem == null) {
        var counties = mutableListOf<County>()
        addCountyItem(item, counties)
        var city =
            City(item.zoneId, item.zoneName)
        city.counties = counties
        city.provinceId = item.districtId
        cities?.add(city)
    } else {
        var counties = hasCityItem.counties
        addCountyItem(item, counties)
        var city =
            City(item.zoneId, item.zoneName)
        city.counties = counties
        cities?.add(city)
    }
}

fun hasCityItem(areaBean: AreaBeanRep.AreaBean, cities: MutableList<City>): City? {
    cities?.forEach { item ->
        if (item?.areaId.equals(areaBean?.zoneId)) {
            return item
        }
    }
    return null
}

fun addCountyItem(item: AreaBeanRep.AreaBean, counties: MutableList<County>) {
    var hasDistrictItem = hasCountyItem(item, counties)
    if (hasDistrictItem == null) {
        var county = County(item.id, item.name)
        county.cityId = item.zoneId
        counties?.add(county)
    }
}

fun hasCountyItem(areaBean: AreaBeanRep.AreaBean, counties: MutableList<County>): County? {
    counties?.forEach { item ->
        if (item?.areaId?.equals(areaBean?.id)!!) {
            return item
        }
    }
    return null
}
