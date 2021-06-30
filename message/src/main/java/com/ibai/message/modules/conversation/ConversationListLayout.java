package com.ibai.message.modules.conversation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ibai.message.component.CustomLinearLayoutManager;
import com.ibai.message.component.SlideRecyclerView;
import com.ibai.message.modules.conversation.base.ConversationInfo;
import com.ibai.message.modules.conversation.interfaces.IConversationAdapter;
import com.ibai.message.modules.conversation.interfaces.IConversationListLayout;

public class ConversationListLayout extends SlideRecyclerView implements IConversationListLayout {

    private ConversationListAdapter mAdapter;

    public ConversationListLayout(Context context) {
        super(context);
        init();
    }

    public ConversationListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConversationListLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        setLayoutFrozen(false);
        setItemViewCacheSize(0);
        setHasFixedSize(true);
        setFocusableInTouchMode(false);
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setBackground(int resId) {
        setBackgroundColor(resId);
    }

    @Override
    public void disableItemUnreadDot(boolean flag) {
        mAdapter.disableItemUnreadDot(flag);
    }

    @Override
    public void setItemAvatarRadius(int radius) {
        mAdapter.setItemAvatarRadius(radius);
    }

    @Override
    public void setItemTopTextSize(int size) {
        mAdapter.setItemTopTextSize(size);
    }

    @Override
    public void setItemBottomTextSize(int size) {
        mAdapter.setItemBottomTextSize(size);
    }

    @Override
    public void setItemDateTextSize(int size) {
        mAdapter.setItemDateTextSize(size);
    }

    @Override
    public ConversationListLayout getListLayout() {
        return this;
    }

    @Override
    public ConversationListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(IConversationAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (ConversationListAdapter) adapter;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mAdapter.setOnItemLongClickListener(listener);
    }

    @Override
    public void setOnItemDeleteAndTopClickListener(OnItemDeleteAndTopClickListener listener) {
        mAdapter.setOnItemDeleteAndTopClickListener(listener);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ConversationInfo messageInfo);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, int position, ConversationInfo messageInfo);
    }

    public interface OnItemDeleteAndTopClickListener {
        void onItemDeleted(View view, int position, ConversationInfo messageInfo);

        void onItemTop(View view, int position, ConversationInfo messageInfo);
    }
}
