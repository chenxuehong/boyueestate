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
            const val PATH_DEPTINFO = "/userCenter/DeptInfo"
            const val PATH_COMMUNITY_MANAGER = "/userCenter/CommunityManager"
            const val PATH_DISTRICT = "/userCenter/District" // 学区
            const val PATH_ADDRESSBOOK = "/userCenter/AddressBook"
            const val PATH_SEARCHHOUSELIST_ACTIVITY = "/userCenter/SearchHouseListActivity"
        }
    }
    //首页模块
    class HomeCenter{
        companion object {
            const val PATH_HOME = "/homeCenter/home"
            const val PATH_TAKELOOK_RECORD = "/homeCenter/takeLookRecord"
            const val PATH_CUSTOMER_PROFILE = "/homeCenter/CustomerProfile"
            const val PATH_HOUSE_DETIL = "/homeCenter/HouseDetail"
            const val PATH_HOUSE_FRAGMENT = "/homeCenter/HouseFragment"
            const val PATH_HOUSE_MAP_FRAGMENT = "/homeCenter/HouseMapFragment"
            const val PATH_HOUSE_SELECT_ACTIVITY = "/homeCenter/HouseSelectActivity"
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
