package com.tencent.qcloud.tim.uikit.component.fragment;

import java.lang.System;

@com.alibaba.android.arouter.facade.annotation.Route(path = "/MessageCenter/Message")
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0006\u0010\n\u001a\u00020\u000bJ&\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u001a\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u0010\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u001c"}, d2 = {"Lcom/tencent/qcloud/tim/uikit/component/fragment/ConversationFragment;", "Lcom/tencent/qcloud/tim/uikit/base/BaseFragment;", "Lcom/tencent/qcloud/tim/uikit/modules/conversation/ConversationManagerKit$MessageUnreadWatcher;", "()V", "mConversationLayout", "Lcom/tencent/qcloud/tim/uikit/modules/conversation/ConversationLayout;", "getMConversationLayout$tuikit_debug", "()Lcom/tencent/qcloud/tim/uikit/modules/conversation/ConversationLayout;", "setMConversationLayout$tuikit_debug", "(Lcom/tencent/qcloud/tim/uikit/modules/conversation/ConversationLayout;)V", "initView", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "startChatActivity", "conversationInfo", "Lcom/tencent/qcloud/tim/uikit/modules/conversation/base/ConversationInfo;", "updateUnread", "count", "", "tuikit_debug"})
public final class ConversationFragment extends com.tencent.qcloud.tim.uikit.base.BaseFragment implements com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit.MessageUnreadWatcher {
    @org.jetbrains.annotations.Nullable()
    private com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout mConversationLayout;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout getMConversationLayout$tuikit_debug() {
        return null;
    }
    
    public final void setMConversationLayout$tuikit_debug(@org.jetbrains.annotations.Nullable()
    com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void initView() {
    }
    
    private final void startChatActivity(com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo conversationInfo) {
    }
    
    @java.lang.Override()
    public void updateUnread(int count) {
    }
    
    public ConversationFragment() {
        super();
    }
}