package com.kotlin.base.utils;

import android.util.Log;

import com.kotlin.base.BuildConfig;


public class LogUtils {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String tag,String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag,String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag,String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag,String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }
}
