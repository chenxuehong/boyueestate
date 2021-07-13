package com.ibai.message.modules.chat.base;

import com.ibai.message.modules.chat.interfaces.IChatLayout;
import com.ibai.message.base.BaseFragment;

public class BaseInputFragment extends BaseFragment {

    private IChatLayout mChatLayout;

    public IChatLayout getChatLayout() {
        return mChatLayout;
    }

    public BaseInputFragment setChatLayout(IChatLayout layout) {
        mChatLayout = layout;
        return this;
    }
}
