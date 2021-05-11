package com.kotlin.base.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kotlin.base.utils.DensityUtils;

public class SharpView extends View {
    private int mWidth =0; //三角形的宽度
    private int mHeight =0; //三角形的高度
    private Context mContext;

    public SharpView(Context context) {
        super(context);
        this.mContext=context;
        initView();
    }

    public SharpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView();
    }

    private void initView() {
        mWidth = DensityUtils.dp2px(mContext,10);
        mHeight = DensityUtils.dp2px(mContext,6);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建画笔
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setAntiAlias(true); //抗锯齿
        paint.setStyle(Paint.Style.FILL);//实线
        //创建路径
        Path path = new Path();
        path.moveTo(0,mHeight);
        path.lineTo(mWidth,mHeight);
        path.lineTo(mWidth/2,0);
        path.close();//闭合路径
        //画在画布上
        canvas.drawPath(path,paint);
    }

}
