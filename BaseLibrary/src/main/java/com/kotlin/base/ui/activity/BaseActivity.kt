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

                var imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideKeyboard(view: View?, ev: MotionEvent): Boolean {
        if (view is EditText) {
            var l: IntArray = intArrayOf(0, 0)
            view.getLocationInWindow(l)
            var left = l[0]
            var top = l[1]
            var bottom = top + view.height
            var right = left + view.width
            return !(ev.x>left&&
                    ev.x<right&&
                    ev.y>top&&
                    ev.y<bottom)
        }
        return false
    }
}
