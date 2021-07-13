package com.ibai.message.modules.conversation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ibai.message.component.TitleBarLayout;
import com.ibai.message.modules.conversation.base.ConversationInfo;
import com.ibai.message.modules.conversation.interfaces.IConversationAdapter;
import com.ibai.message.modules.conversation.interfaces.IConversationLayout;
import com.ibai.message.R;
import com.ibai.message.base.IUIKitCallBack;
import com.ibai.message.utils.ToastUtil;

public  class ConversationLayout extends RelativeLayout implements IConversationLayout {

    private ConversationListLayout mConversationList;

    public ConversationLayout(Context context) {
        super(context);
        init();
    }

    public ConversationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConversationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化相关UI元素
     */
    private void init() {
        inflate(getContext(), R.layout.conversation_layout, this);
        mConversationList = findViewById(R.id.conversation_list);
    }

    public void initDefault() {
        final IConversationAdapter adapter = new ConversationListAdapter();
        mConversationList.setAdapter(adapter);
        ConversationManagerKit.getInstance().loadConversation(new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                adapter.setDataProvider((ConversationProvider) data);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastLongMessage(getContext().getString(R.string.load_msg_error));
            }
        });
    }


    @Override
    public TitleBarLayout getTitleBar() {
        return null;
    }

    @Override
    public void setParentLayout(Object parent) {

    }

    @Override
    public ConversationListLayout getConversationList() {
        return mConversationList;
    }

    public void addConversationInfo(int position, ConversationInfo info) {
        mConversationList.getAdapter().addItem(position, info);
    }

    public void removeConversationInfo(int position) {
        mConversationList.getAdapter().removeItem(position);
    }

    @Override
    public void setConversationTop(int position, ConversationInfo conversation) {
        mConversationList.closeMenu();
        ConversationManagerKit.getInstance().setConversationTop(position, conversation);
    }

    @Override
    public void deleteConversation(int position, ConversationInfo conversation) {
        mConversationList.closeMenu();
        ConversationManagerKit.getInstance().deleteConversation(position, conversation);
    }
}
