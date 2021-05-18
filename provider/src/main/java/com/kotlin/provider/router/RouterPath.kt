package com.kotlin.provider.router


/*
    模块路由 路径定义
 */
object RouterPath{

    //用户模块
    class UserCenter{
        companion object {
            const val PATH_LOGIN = "/userCenter/login"
            const val PATH_MAIN = "/userCenter/main"
        }
    }
    //首页模块
    class HomeCenter{
        companion object {
            const val PATH_HOME = "/homeCenter/home"
            const val PATH_TAKELOOK_RECORD = "/homeCenter/takeLookRecord"
            const val PATH_CUSTOMER_PROFILE = "/homeCenter/CustomerProfile"
        }
    }
    class CustomerCenter{
        companion object {
            const val PATH_CUSTOMER = "/CustomerCenter/Customer"
            const val PATH_CUSTOMER_DETAIL = "/CustomerCenter/CustomerDetail"
        }
    }
    class MessageCenter{
        companion object {
            const val PATH_MESSAGE = "/MessageCenter/Message"
            const val PATH_CHAT = "/MessageCenter/Chat"
        }
    }
}
