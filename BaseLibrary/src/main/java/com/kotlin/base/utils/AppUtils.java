package com.kotlin.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.List;

public class AppUtils {

    /**
     * 防止手指在底部滑动的时候拉出导航栏和状态栏
     */
    private final static int SYSTEM_UI_FLAG_PREVENT_TRANSIENT_BARS = 0x00020000;

    /**
     * @param destContext 上下文对象
     *                    用于解决输入法内存泄露
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }

        String[] viewArray = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field filed;
        Object filedObject;

        for (String view : viewArray) {
            try {
                filed = inputMethodManager.getClass().getDeclaredField(view);
                if (!filed.isAccessible()) {
                    filed.setAccessible(true);
                }
                filedObject = filed.get(inputMethodManager);
                if (filedObject != null && filedObject instanceof View) {
                    View fileView = (View) filedObject;
                    // 被InputMethodManager持有引用的context是想要目标销毁的
                    if (fileView.getContext() == destContext) {
                        // 置空，破坏掉path to gc节点
                        filed.set(inputMethodManager, null);
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static void setFullScreen(Window window) {
        int v = window.getDecorView().getSystemUiVisibility();
        v = v | getSystemUiImmersiveVisibility();
        window.getDecorView().setSystemUiVisibility(v);
    }

    public static int getSystemUiImmersiveVisibility() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | SYSTEM_UI_FLAG_PREVENT_TRANSIENT_BARS;
    }

    /**
     * 获取进程名称
     */
    public static String getProcessName(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfoList) {
                if (runningAppProcessInfo != null && runningAppProcessInfo.pid == android.os.Process.myPid()) {
                    return runningAppProcessInfo.processName;
                }
            }
        } catch (Throwable throwable) {
            //
        }
        return "";
    }
}
