package com.kotlin.base.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.zhouwei.library.CustomPopWindow;

public class PhotoWindowUtils {

    public static CustomPopWindow popBottomDialog(View rootview, Context context, View containerView) {
        return popBottomDialog(rootview,context,containerView,true);
    }

    public static CustomPopWindow popBottomDialog(View rootview, Context context, View containerView, boolean enableAlfa) {
        CustomPopWindow customPopWindow =  new CustomPopWindow.PopupWindowBuilder(context)
                .setView(containerView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(enableAlfa?0.7f:1f) // 控制亮度
                .setFocusable(true)
                .setInputMethodMode(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .setOutsideTouchable(true)
                .create()
                .showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        return customPopWindow;
    }
}
