package com.bafuestate.message.modules.conversation.interfaces;

import com.bafuestate.message.base.ILayout;
import com.bafuestate.message.component.TitleBarLayout;
import com.bafuestate.message.modules.conversation.ConversationLayout;
import com.bafuestate.message.modules.conversation.ConversationListLayout;
import com.bafuestate.message.modules.conversation.base.ConversationInfo;

/**
 * 会话列表窗口 {@link ConversationLayout} 由标题区 {@link TitleBarLayout} 与列表区 {@link ConversationListLayout}
 * <br>组成，每一部分都提供了 UI 样式以及事件注册的接口可供修改。
 */
public interface IConversationLayout extends ILayout {

    /**
     * 获取会话列表 List
     *
     * @return
     */
    ConversationListLayout getConversationList();

    /**
     * 置顶会话
     *
     * @param position     该item在列表的索引
     * @param conversation 会话内容
     */
    void setConversationTop(int position, ConversationInfo conversation);

    /**
     * 删除会话
     *
     * @param position     该item在列表的索引
     * @param conversation 会话内容
     */
    void deleteConversation(int position, ConversationInfo conversation);
}
