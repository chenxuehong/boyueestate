package com.kotlin.base.data.protocol

data class Configs(
    var configs: MutableList<ConfigBean>
) {
    data class ConfigBean(
        var PName: String,
        var MessageAppID: Int,
        var CompanyCode: String,
        var ServerAddress: String,
        var ip: String,
        var XM_PUSH_BUZID: Long,
        var HW_PUSH_BUZID: Long,
        var MEIZHU_PUSH_BUZID: Long,
        var VIVO_PUSH_BUZID: Long,
        var OPPO_PUSH_BUZID: Long
    )
}