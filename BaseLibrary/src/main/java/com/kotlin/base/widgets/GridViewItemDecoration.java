package com.kotlin.base.widgets;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 为RecyclerView增加间距
 * 预设2列，如果是3列，则左右值不同
 */
public class GridViewItemDecoration extends RecyclerView.ItemDecoration  {
    private int space = 0;
    private int pos;
    private int column;

    private int TYPE_1 = 100;
    private int TYPE_2 = 101;
    private int left;
    private int right;
    private int top;
    private int bottom;

    private int type;

    public GridViewItemDecoration(int column, int space) {
        this.column = column;
        this.space = space;
        type = TYPE_1;
    }

    public GridViewItemDecoration(int column, int left, int right, int top, int bottom) {
        this.column = column;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        type = TYPE_2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        pos = parent.getChildAdapterPosition(view);

        if (type == TYPE_1) {
            outRect.top = space;
        } else {
            outRect.top = top;
        }


        //取模
        //  第一列
        if (pos % column == 0) {
            outRect.left = space;
            if (column == 1) {
                if (type == TYPE_1) {
                    outRect.right = space;
                } else {
                    outRect.right = right;
                }

            } else {
                if (type == TYPE_1) {
                    outRect.right = space / 2;
                } else {
                    outRect.right = right / 2;
                }
            }
        } else if (pos % column == column - 1) {
            //最后一列
            if (type == TYPE_1) {
                outRect.left = space / 2;
                outRect.right = space;
            } else {
                outRect.left = left / 2;
                outRect.right = right;
            }

        } else {
            // 中间部分
            if (type == TYPE_1) {
                outRect.left = space / 2;
                outRect.right = space / 2;
            } else {
                outRect.left = left / 2;
                outRect.right = right / 2;
            }

        }
    }

}