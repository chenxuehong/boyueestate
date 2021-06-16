package com.kotlin.provider.data.protocol

data class ServerVersionInfo(
    var name:String,
    var version:String,
    var changelog:String?,
    var updated_at:String?,
    var versionShort:String?,
    var build:String?,
    var installUrl:String?,
    var install_url:String?,
    var direct_install_url:String?,
    var update_url:String?,
    var binary:Binary
){
    data class Binary(
        var fsize:Long
    )
}