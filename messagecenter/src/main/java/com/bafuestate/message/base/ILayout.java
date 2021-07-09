package com.bafuestate.message.base;

import com.bafuestate.message.component.TitleBarLayout;

public interface ILayout {

    /**
     * 获取标题栏
     *
     * @return
     */
    TitleBarLayout getTitleBar();

    /**
     * 设置该 Layout 的父容器
     *
     * @param parent
     */
    void setParentLayout(Object parent);
}
