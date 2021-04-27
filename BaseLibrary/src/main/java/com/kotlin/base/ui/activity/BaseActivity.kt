package com.kotlin.base.ui.activity

import android.content.ComponentCallbacks2
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.hideKeyboard
import com.kotlin.base.utils.GlideUtils
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import org.jetbrains.anko.find

/*
    Activity基类，业务无关
 */
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    //获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
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
}
