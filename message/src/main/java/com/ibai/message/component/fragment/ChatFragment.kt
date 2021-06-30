package com.ibai.message.component.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibai.message.R
import com.kotlin.base.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : BaseFragment() {

    private var mChatInfo: com.ibai.message.modules.chat.base.ChatInfo? = null

    private var mTitleBar: com.ibai.message.component.TitleBarLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return LayoutInflater.from(context).inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        val bundle = arguments
        mChatInfo = bundle!!.getSerializable(com.ibai.message.config.MessageConstants.CHAT_INFO) as com.ibai.message.modules.chat.base.ChatInfo
        if (mChatInfo == null) {
            return
        }
        /*
         * 需要聊天的基本信息
         */
        chat_layout.setChatInfo(mChatInfo)
        //单聊组件的默认UI和交互初始化
        chat_layout.initDefault()
        //        ChatLayoutHelper.customizeChatLayout(getActivity(), mChatLayout);
        initTitleBar()
        initChatContent()
    }

    private fun initTitleBar() {
        //获取单聊面板的标题栏
        mTitleBar = chat_layout.getTitleBar()
        mTitleBar?.setLeftIcon(R.drawable.icon_back_gray)
        mTitleBar?.setBackgroundColor(resources.getColor(R.color.main_color))
        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar?.setOnLeftClickListener { activity!!.finish() }
    }

    private fun initChatContent() {
        chat_layout.getMessageLayout().setOnItemClickListener(
            object : com.ibai.message.modules.chat.layout.message.MessageLayout.OnItemClickListener {
                override fun onMessageLongClick(
                    view: View,
                    position: Int,
                    messageInfo: com.ibai.message.modules.message.MessageInfo
                ) {
                    //因为adapter中第一条为加载条目，位置需减1
                    chat_layout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view)
                }

                override fun onUserIconClick(view: View, position: Int, messageInfo: com.ibai.message.modules.message.MessageInfo) {

                }
            }
        )
    }

    override fun onDestroy() {
        try {
            chat_layout?.exitChat()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
