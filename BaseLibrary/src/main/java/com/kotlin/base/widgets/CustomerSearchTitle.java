package com.kotlin.base.widgets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import com.kotlin.base.R;

public class CustomerSearchTitle extends RelativeLayout {

    private SearchView search;
    private TextView tvSearch;
    private String text = "搜索";
    private Context context;
    private View mImgLeft;

    public CustomerSearchTitle(Context context) {
        this(context,null);
    }

    public CustomerSearchTitle(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerSearchTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.search_view);
        text = ta.getString(R.styleable.search_view_searchHint);
        init();
        ta.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomerSearchTitle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        View inflate = inflate(getContext(), R.layout.common_search_title, this);
        mImgLeft = inflate.findViewById(R.id.mImgLeft);
        mImgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search = inflate.findViewById(R.id.search);
        tvSearch = inflate.findViewById(R.id.tvSearch);
        if (search == null) {
            return;
        }

        search.setIconifiedByDefault(false);
        setQueryHint(text);
        search.setSubmitButtonEnabled(false);
        tvSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onQueryTextSubmit(search.getQuery().toString().trim());
                }
            }
        });
    }

    private void finish() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    public void setQueryHint(String str) {
        if (search != null) {
            search.setQueryHint(str);
        }
    }

    SearchView.OnQueryTextListener listener;

    public void setOnQueryTextListener(SearchView.OnQueryTextListener listener) {
        this.listener = listener;
        search.setOnQueryTextListener(listener);
    }

}
