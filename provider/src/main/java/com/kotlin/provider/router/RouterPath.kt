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
        }
    }
    class CustomerCenter{
        companion object {
            const val PATH_CUSTOMER = "/CustomerCenter/Customer"
        }
    }
    class MessageCenter{
        companion object {
            const val PATH_MESSAGE = "/MessageCenter/Message"
        }
    }
}
