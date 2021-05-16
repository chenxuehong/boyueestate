package com.huihe.usercenter.data.protocol


data class UserInfo(
    val uid: String?,
    val account: String?,
    val avatar: String?,
    val avatarUrl: String?,
    val collectHouseIds: String?,
    val createTime: String?,
    val deptId: String?,
    val deptName: String?,
    val email: String?,
    val employName: String?,
    val employTime: String?,
    val employUser: String?,
    val isLive: String?,
    val jobId: String?,
    val jobName: String?,
    val lastLoginTime: String?,
    val loginCredentials: String?,
    val mobile: String?,
    val password: String?,
    val quitFlag: String?,
    val registrationId: String?,
    val roles: MutableList<Role>?,
    val state: String?,
    val userName: String?,
    val userSig: String?
) {
    data class Role(
        var id: String?,
        var roleId: String?,
        var roleName: String?,
        var userId: String?
    )
}
