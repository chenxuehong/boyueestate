package com.ibai.message.modules.group.apply;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ibai.message.component.TitleBarLayout;
import com.ibai.message.modules.group.interfaces.IGroupMemberLayout;
import com.ibai.message.R;
import com.ibai.message.modules.chat.GroupChatManagerKit;
import com.ibai.message.modules.group.info.GroupInfo;
import com.ibai.message.utils.TUIKitConstants;


public class GroupApplyManagerLayout extends LinearLayout implements IGroupMemberLayout {

    private TitleBarLayout mTitleBar;
    private ListView mApplyMemberList;
    private GroupApplyAdapter mAdapter;

    public GroupApplyManagerLayout(Context context) {
        super(context);
        init();
    }

    public GroupApplyManagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GroupApplyManagerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.group_apply_manager_layout, this);
        mApplyMemberList = findViewById(R.id.group_apply_members);
        mAdapter = new GroupApplyAdapter();
        mAdapter.setOnItemClickListener(new GroupApplyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GroupApplyInfo info) {
                Intent intent = new Intent(getContext(), GroupApplyMemberActivity.class);
                intent.putExtra(TUIKitConstants.ProfileType.CONTENT, info);
                ((Activity) getContext()).startActivityForResult(intent, TUIKitConstants.ActivityRequest.CODE_1);
            }
        });
        mApplyMemberList.setAdapter(mAdapter);
        mTitleBar = findViewById(R.id.group_apply_title_bar);
        mTitleBar.getRightGroup().setVisibility(View.GONE);
        mTitleBar.setTitle(getResources().getString(R.string.group_apply_members), TitleBarLayout.POSITION.MIDDLE);
        mTitleBar.setOnLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupChatManagerKit.getInstance().onApplied(mAdapter.getUnHandledSize());
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });
    }

    public TitleBarLayout getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void setParentLayout(Object parent) {

    }

    @Override
    public void setDataSource(GroupInfo dataSource) {
        mAdapter.setDataSource(dataSource);
        mAdapter.notifyDataSetChanged();
    }

    public void updateItemData(GroupApplyInfo info) {
        mAdapter.updateItemData(info);
    }
}
