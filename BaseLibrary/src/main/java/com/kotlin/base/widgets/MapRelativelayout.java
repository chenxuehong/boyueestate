package com.kotlin.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

public class MapRelativelayout extends RelativeLayout {

    private RecyclerView scrollView;

    public MapRelativelayout(Context context) {
        super(context);
    }

    public MapRelativelayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MapRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollView(RecyclerView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (scrollView!=null){
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                scrollView.requestDisallowInterceptTouchEvent(false);
            } else {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}