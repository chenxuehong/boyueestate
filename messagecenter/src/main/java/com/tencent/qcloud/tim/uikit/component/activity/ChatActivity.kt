package com.tencent.qcloud.tim.uikit.component.activity

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.router.RouterPath
import com.tencent.qcloud.tim.uikit.component.fragment.ChatFragment

@Route(path = RouterPath.MessageCenter.PATH_CHAT)
class ChatActivity : BaseActivity() {
    private var mChatFragment: ChatFragment? = null

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        chat(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chat(intent)
    }

    private fun chat(intent: Intent) {
        val bundle = intent.extras
        if (bundle != null) {
            mChatFragment = ChatFragment()
            setFragment(ChatFragment(), bundle)
        }
    }

}
