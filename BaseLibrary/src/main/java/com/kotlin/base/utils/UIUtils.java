package com.kotlin.base.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class UIUtils {
    private static final String TAG = UIUtils.class.getSimpleName();
    private static final long NO_QUICK_CLICK_INTERVAL = 500;
    private static long sLastClickTime;

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 通过id名来获取View，可能返回null
     * @param context
     * @param view
     * @param idName
     * @return
     */
    public static View findViewById(Context context, View view, String idName) {
        int id = getResId(context, idName, "id");
        if (id > 0) {
            return view.findViewById(id);
        }
        return null;
    }

    public static int getResId(Context context, String resIdName, String resType) {
        return context.getResources().getIdentifier(resIdName, resType, context.getPackageName());
    }

    public static void setNewClickListener(@NonNull View view, View.OnClickListener listener) {
        setNewClickListener(view, false, listener);
    }

    public static void setNewClickListener(@NonNull View view, boolean hasTouchEffect, View.OnClickListener listener) {
        setNewClickListener(view, hasTouchEffect, true, listener);
    }

    public static void setNewClickListener(@NonNull final View view, boolean hasTouchEffect, final boolean supportNoQuickClick, View.OnClickListener listener) {
        if (view == null) {
            return;
        }

        if (listener != null) {
            if (hasTouchEffect) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                if (supportNoQuickClick &&
                                        (Math.abs(System.currentTimeMillis() - sLastClickTime) > NO_QUICK_CLICK_INTERVAL)) {
                                    view.setAlpha(0.5f);
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:
                            case MotionEvent.ACTION_HOVER_EXIT:
                                view.setAlpha(1f);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }

            if (supportNoQuickClick) {
                setNoQuickClickListener(view, listener);
            } else {
                view.setOnClickListener(listener);
            }
        } else {
            view.setOnClickListener(null);
        }
    }

    private static void setNoQuickClickListener(final View view, final View.OnClickListener clickListener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long nowTime = System.currentTimeMillis();
                if (Math.abs(nowTime - sLastClickTime) > NO_QUICK_CLICK_INTERVAL) {
                    if (clickListener != null) {
                        clickListener.onClick(v);
                    }
                    sLastClickTime = nowTime;
                } else {
//                    LogUtils.d(TAG, "quick click now " + view);
                }
            }
        });
    }

    public static void clearListener(View view) {
        if (view != null) {
            view.setOnTouchListener(null);
            view.setOnClickListener(null);
        }
    }

}
