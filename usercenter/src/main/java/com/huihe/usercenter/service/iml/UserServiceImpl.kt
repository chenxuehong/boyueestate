package com.huihe.usercenter.service.iml

import com.huihe.usercenter.data.respository.UserRepository
import com.huihe.usercenter.service.UserService
import com.kotlin.base.ext.convert
import io.reactivex.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun login(account: String, password: String): Observable<String> {

        return repository.login(account,password).convert()
    }
}
