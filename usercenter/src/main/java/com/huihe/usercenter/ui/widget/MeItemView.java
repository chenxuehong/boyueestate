package com.huihe.usercenter.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huihe.usercenter.R;

public class MeItemView extends LinearLayout {
    public MeItemView(Context context) {
        this(context, null);
    }

    public MeItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private TextView tvTitle;
    TextView tvContent;

    public MeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.layout_me_itemview, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.meItemView);
        Drawable drawable = typedArray.getDrawable(R.styleable.meItemView_leftIcon);
        String title = typedArray.getString(R.styleable.meItemView_title);
        String content = typedArray.getString(R.styleable.meItemView_meContent);
        Boolean isShowLine = typedArray.getBoolean(R.styleable.meItemView_isShowLine, false);
        typedArray.recycle();

        ImageView ivLeftIcon = findViewById(R.id.ivMeLeftIcon);
        tvTitle = findViewById(R.id.tvMeTitle);
        tvContent = findViewById(R.id.tvMeContent);
        View line = findViewById(R.id.meLine);
        ivLeftIcon.setImageDrawable(drawable);
        tvTitle.setText(title);
        tvContent.setText(content);
        line.setVisibility(isShowLine ? View.VISIBLE : View.GONE);
    }

    public String getTitleContent() {
        return tvTitle.getText().toString().trim();
    }

    public void setContent(String content){
        tvContent.setText(content);
    }
}
