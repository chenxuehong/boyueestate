package com.ibai.message.component.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.ibai.message.R
import com.ibai.message.component.activity.ChatActivity
import com.kotlin.provider.event.MessageBadgeEvent
import com.kotlin.provider.router.RouterPath

import com.tencent.imsdk.v2.V2TIMConversation
import kotlinx.android.synthetic.main.fragment_conversation.*

@Route(path = RouterPath.MessageCenter.PATH_MESSAGE)
class ConversationFragment : com.ibai.message.base.BaseFragment(), com.ibai.message.modules.conversation.ConversationManagerKit.MessageUnreadWatcher {

    internal var mConversationLayout: com.ibai.message.modules.conversation.ConversationLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_conversation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        mConversationLayout = fragment_conversationLayout
        com.ibai.message.modules.conversation.ConversationManagerKit.getInstance().addUnreadWatcher(this)
        // 从布局文件中获取会话列表面板
        // 会话列表面板的默认UI和交互初始化
        mConversationLayout!!.initDefault()
        // 通过API设置ConversataonLayout各种属性的样例，开发者可以打开注释，体验效果
        //        ConversationLayoutHelper.customizeConversation(mConversationLayout);
        mConversationLayout!!.conversationList.setOnItemClickListener { view, position, conversationInfo ->
            //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
            startChatActivity(conversationInfo)
        }
        mConversationLayout!!.conversationList.setOnItemDeleteAndTopClickListener(object :
            com.ibai.message.modules.conversation.ConversationListLayout.OnItemDeleteAndTopClickListener {
            override fun onItemDeleted(view: View, position: Int, messageInfo: com.ibai.message.modules.conversation.base.ConversationInfo) {
                mConversationLayout!!.deleteConversation(position, messageInfo)
            }

            override fun onItemTop(view: View, position: Int, messageInfo: com.ibai.message.modules.conversation.base.ConversationInfo) {
                mConversationLayout!!.setConversationTop(position, messageInfo)
            }
        })

    }

    private fun startChatActivity(conversationInfo: com.ibai.message.modules.conversation.base.ConversationInfo) {
        val chatInfo = com.ibai.message.modules.chat.base.ChatInfo()
        chatInfo.type = if (conversationInfo.isGroup)
            V2TIMConversation.V2TIM_GROUP
        else
            V2TIMConversation.V2TIM_C2C
        chatInfo.id = conversationInfo.id
        chatInfo.chatName = conversationInfo.title
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra(com.ibai.message.config.MessageConstants.CHAT_INFO, chatInfo)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun updateUnread(count: Int) {
        Bus.send(MessageBadgeEvent(count > 0))
    }
}
