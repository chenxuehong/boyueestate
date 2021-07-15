package com.ibai.message.modules.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.ibai.message.component.TitleBarLayout;
import com.ibai.message.modules.chat.base.AbsChatLayout;
import com.ibai.message.modules.chat.base.ChatInfo;
import com.ibai.message.modules.chat.base.ChatManagerKit;
import com.ibai.message.modules.group.apply.GroupApplyInfo;
import com.ibai.message.modules.group.apply.GroupApplyManagerActivity;
import com.ibai.message.modules.group.info.GroupInfo;
import com.ibai.message.modules.group.info.GroupInfoActivity;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.ibai.message.R;
import com.ibai.message.TUIKit;
import com.ibai.message.base.IUIKitCallBack;
import com.ibai.message.utils.TUIKitConstants;
import com.ibai.message.utils.ToastUtil;

import java.util.List;


public class ChatLayout extends AbsChatLayout implements GroupChatManagerKit.GroupNotifyHandler {

    private GroupInfo mGroupInfo;
    private GroupChatManagerKit mGroupChatManager;
    private C2CChatManagerKit mC2CChatManager;
    private boolean isGroup = false;

    public ChatLayout(Context context) {
        super(context);
    }

    public ChatLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setChatInfo(ChatInfo chatInfo) {
        super.setChatInfo(chatInfo);
        if (chatInfo == null) {
            return;
        }

        if (chatInfo.getType() == V2TIMConversation.V2TIM_C2C) {
            isGroup = false;
        } else {
            isGroup = true;
        }

        if (isGroup) {
            mGroupChatManager = GroupChatManagerKit.getInstance();
            mGroupChatManager.setGroupHandler(this);
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setId(chatInfo.getId());
            groupInfo.setChatName(chatInfo.getChatName());
            mGroupChatManager.setCurrentChatInfo(groupInfo);
            mGroupInfo = groupInfo;
            loadChatMessages(null);
            loadApplyList();
            getTitleBar().getRightIcon().setVisibility(View.GONE);
            getTitleBar().getRightIcon().setImageResource(R.drawable.chat_group);
            if (getTitleBar().getRightIcon().getVisibility() == View.VISIBLE) {
                getTitleBar().setOnRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mGroupInfo != null) {
                            Intent intent = new Intent(getContext(), GroupInfoActivity.class);
                            intent.putExtra(TUIKitConstants.Group.GROUP_ID, mGroupInfo.getId());
                            getContext().startActivity(intent);
                        } else {
                            ToastUtil.toastLongMessage(TUIKit.getAppContext().getString(R.string.wait_tip));
                        }
                    }
                });
            }
            mGroupApplyLayout.setOnNoticeClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), GroupApplyManagerActivity.class);
                    intent.putExtra(TUIKitConstants.Group.GROUP_INFO, mGroupInfo);
                    getContext().startActivity(intent);
                }
            });
        } else {
            getTitleBar().getRightIcon().setVisibility(View.GONE);
            getTitleBar().getRightIcon().setImageResource(R.drawable.chat_c2c);
            mC2CChatManager = C2CChatManagerKit.getInstance();
            mC2CChatManager.setCurrentChatInfo(chatInfo);
            loadChatMessages(null);
        }
    }

    @Override
    public ChatManagerKit getChatManager() {
        if (isGroup) {
            return mGroupChatManager;
        } else {
            return mC2CChatManager;
        }
    }

    private void loadApplyList() {
        mGroupChatManager.getProvider().loadGroupApplies(new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                List<GroupApplyInfo> applies = (List<GroupApplyInfo>) data;
                if (applies != null && applies.size() > 0) {
                    mGroupApplyLayout.getContent().setText(getContext().getString(R.string.group_apply_tips, applies.size()));
                    mGroupApplyLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastLongMessage("loadApplyList onError: " + errMsg);
            }
        });
    }

    @Override
    public void onGroupForceExit() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    @Override
    public void onGroupNameChanged(String newName) {
        getTitleBar().setTitle(newName, TitleBarLayout.POSITION.MIDDLE);
    }

    @Override
    public void onApplied(int size) {
        if (size == 0) {
            mGroupApplyLayout.setVisibility(View.GONE);
        } else {
            mGroupApplyLayout.getContent().setText(getContext().getString(R.string.group_apply_tips, size));
            mGroupApplyLayout.setVisibility(View.VISIBLE);
        }
    }

}