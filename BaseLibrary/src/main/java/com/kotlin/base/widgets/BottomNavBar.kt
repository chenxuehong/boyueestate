package com.kotlin.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.kotlin.base.R

/*
    底部导航
 */
class BottomNavBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    //消息Tab 标签
    private val mMsgBadge:ShapeBadgeItem

    init {
        //首页
        val homeItem = BottomNavigationItem(R.drawable.nav_bar_home_press,resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.nav_bar_home_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)
        //地图
        val mapItem = BottomNavigationItem(R.drawable.nav_bar_ditu_press,resources.getString(R.string.nav_bar_map))
                .setInactiveIconResource(R.drawable.nav_bar_ditu_mormal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)

        //客源
        val customerItem = BottomNavigationItem(R.drawable.nav_bar_customer_press,resources.getString(R.string.nav_bar_customer))
                .setInactiveIconResource(R.drawable.nav_bar_customer_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)

        //消息
        val msgItem = BottomNavigationItem(R.drawable.nav_bar_msg_press,resources.getString(R.string.nav_bar_msg))
                .setInactiveIconResource(R.drawable.nav_bar_msg_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)

        mMsgBadge = ShapeBadgeItem()
        mMsgBadge.setShape(ShapeBadgeItem.SHAPE_OVAL)
        msgItem.setBadgeItem(mMsgBadge)

        //我的
        val userItem = BottomNavigationItem(R.drawable.nav_bar_me_press,resources.getString(R.string.nav_bar_user))
                .setInactiveIconResource(R.drawable.nav_bar_me_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)
        //设置底部导航模式及样式
        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)
        //添加Tab
        addItem(homeItem)
                .addItem(mapItem)
                .addItem(customerItem)
                .addItem(msgItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0)
                .initialise()
    }

//    /*
//        检查购物车Tab是否显示标签
//     */
//    fun checkCartBadge(count:Int){
//        if (count == 0){
//            mCartBadge.hide()
//        }else{
//            mCartBadge.show()
//            mCartBadge.setText("$count")
//        }
//    }

    /*
        检查消息Tab是否显示标签
     */
    fun checkMsgBadge(isVisiable:Boolean){
        if (isVisiable){
            mMsgBadge.show()
        }else {
            mMsgBadge.hide()
        }
    }
}
