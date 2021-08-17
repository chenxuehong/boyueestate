package com.kotlin.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

public class CompatUtils {
    private static int getStatusBarHeight(@NonNull Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    private static int getNavigationHeight(@NonNull Context context) {
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static void fitSystemWindow(@NonNull View view) {
        if (view.getFitsSystemWindows() && Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            view.setPadding(view.getPaddingLeft(), getStatusBarHeight(view.getContext()), view.getPaddingRight(), getNavigationHeight(view.getContext()));
        }
    }

    @UiThread
    public static void translucentBars(@NonNull Activity activity) {
        translucentBars(activity.getWindow());
    }

    @UiThread
    public static void translucentBars(@NonNull Window window) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // 判断api版本为4.3(18) 则设置上下bar透明属性，需要rom配合修改
            WindowManager.LayoutParams attributes = window.getAttributes();
            final int flagTranslucentNavigation = 0x80000000;
            final int flagTranslucentStatus = 0x10000000;
            attributes.flags |= flagTranslucentStatus
                    | flagTranslucentNavigation;
            window.setAttributes(attributes);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
