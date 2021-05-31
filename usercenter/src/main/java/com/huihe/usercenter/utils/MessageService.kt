package com.huihe.usercenter.utils


import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.provider.event.ErrorEntity
import com.kotlin.provider.event.MessageLoginEvent
import javax.inject.Inject


class MessageService @Inject constructor(){

    var listsner:OnMessageListener?=null
    init {
        Bus.observe<ErrorEntity>().subscribe {
            if (it.code == ErrorEntity.TYPE_LOGIN_SUCCESS) {
                listsner?.onLoginSuccess()
            } else {
                listsner?.onLoginFail(it.error,it.code)
            }
        }.registerInBus(this)
    }

    fun login(userId: String, userSign: String, listsner:OnMessageListener ) {
        this.listsner = listsner
        Bus.send(MessageLoginEvent(userId, userSign))
    }

    interface OnMessageListener {
        fun onLoginSuccess()
        fun onLoginFail(message: String, code: Int)
    }

    fun onDestory(){
        Bus.unregister(this)
    }
}
