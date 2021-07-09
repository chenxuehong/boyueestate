package com.bafuestate.message.modules.chat.base;

import com.bafuestate.message.base.BaseFragment;
import com.bafuestate.message.modules.chat.interfaces.IChatLayout;

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
