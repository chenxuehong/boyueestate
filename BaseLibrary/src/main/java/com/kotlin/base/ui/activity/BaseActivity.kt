package com.kotlin.base.ui.activity

import android.content.ComponentCallbacks2
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.hideKeyboard
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.AppUtils
import com.kotlin.base.utils.GlideUtils
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import org.jetbrains.anko.find

/*
    Activity基类，业务无关
 */
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        AppManager.instance.finishActivity(this)
        Glide.get(this).clearMemory()
        AppUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
    }

    //获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content
        }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            GlideUtils.clearMemory(this)
        }
        GlideUtils.trimMemory(this, level)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            var view = currentFocus
            if (isShouldHideKeyboard(view, ev)) {
                hideKeyboard(view)
            }
            return super.dispatchTouchEvent(ev)
        }
        if (window.superDispatchTouchEvent(ev)) {
            return true
        }
        return onTouchEvent(ev)
    }

    private fun isShouldHideKeyboard(view: View?, ev: MotionEvent): Boolean {
        if (view is EditText) {
            var l: IntArray = intArrayOf(0, 0)
            view.getLocationOnScreen(l)
            var left = l[0]
            var top = l[1]

            return (ev.getX() < left || (ev.getX() > left + view.getWidth())
                    || ev.getY() < top || (ev.getY() > top + view.getHeight()))
        }
        return false
    }

    open fun setFragment(fragment:BaseFragment,args: Bundle){
        fragment.arguments = args
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            fragment
        ).commitAllowingStateLoss()
    }

    open fun setFragment(fragment:BaseFragment){
        supportFragmentManager.beginTransaction().replace(
            contentView.id,
            fragment
        ).commitAllowingStateLoss()
    }
}
