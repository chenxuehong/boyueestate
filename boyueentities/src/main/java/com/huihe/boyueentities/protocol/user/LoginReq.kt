package com.huihe.boyueentities.protocol.user

data class LoginReq(val account: String, val password: String, var type: String)
