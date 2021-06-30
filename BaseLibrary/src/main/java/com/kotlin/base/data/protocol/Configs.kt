package com.kotlin.base.data.protocol

data class Configs(
    var projectName: String,
    var configs: MutableList<ConfigBean>
) {
    data class ConfigBean(
        var MessageAppID: Int,
        var CompanyCode: String,
        var ServerAddress: String,
        var ip: String
    )
}