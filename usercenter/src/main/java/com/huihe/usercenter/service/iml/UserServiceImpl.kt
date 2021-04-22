package com.huihe.usercenter.service.iml

import com.huihe.usercenter.data.respository.UserRepository
import com.huihe.usercenter.service.UserService
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

}
