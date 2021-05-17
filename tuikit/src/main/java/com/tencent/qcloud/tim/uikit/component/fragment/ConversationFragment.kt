package com.tencent.qcloud.tim.uikit.component.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.kotlin.provider.event.MessageBadgeEvent
import com.kotlin.provider.router.RouterPath

import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.qcloud.tim.uikit.R
import com.tencent.qcloud.tim.uikit.base.BaseFragment
import com.tencent.qcloud.tim.uikit.component.activity.ChatActivity
import com.tencent.qcloud.tim.uikit.config.Constants
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo

@Route(path = RouterPath.MessageCenter.PATH_MESSAGE)
class ConversationFragment : BaseFragment(), ConversationManagerKit.MessageUnreadWatcher {

    internal var mConversationLayout: ConversationLayout? = null

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
        ConversationManagerKit.getInstance().addUnreadWatcher(this)
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
            ConversationListLayout.OnItemDeleteAndTopClickListener {
            override fun onItemDeleted(view: View, position: Int, messageInfo: ConversationInfo) {
                mConversationLayout!!.deleteConversation(position, messageInfo)
            }

            override fun onItemTop(view: View, position: Int, messageInfo: ConversationInfo) {
                mConversationLayout!!.setConversationTop(position, messageInfo)
            }
        })

    }

    private fun startChatActivity(conversationInfo: ConversationInfo) {
        val chatInfo = ChatInfo()
        chatInfo.type = if (conversationInfo.isGroup)
            V2TIMConversation.V2TIM_GROUP
        else
            V2TIMConversation.V2TIM_C2C
        chatInfo.id = conversationInfo.id
        chatInfo.chatName = conversationInfo.title
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra(Constants.CHAT_INFO, chatInfo)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun updateUnread(count: Int) {
        Bus.send(MessageBadgeEvent(count > 0))
    }
}
