package com.bafuestate.message.modules.conversation.holder;

import android.view.View;

import com.bafuestate.message.modules.conversation.ConversationListAdapter;
import com.bafuestate.message.modules.conversation.base.ConversationInfo;

import androidx.recyclerview.widget.RecyclerView;

public abstract class ConversationBaseHolder extends RecyclerView.ViewHolder {

    protected View rootView;
    protected ConversationListAdapter mAdapter;

    public ConversationBaseHolder(View itemView) {
        super(itemView);
        rootView = itemView;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = (ConversationListAdapter) adapter;
    }

    public abstract void layoutViews(ConversationInfo conversationInfo, int position);

}
