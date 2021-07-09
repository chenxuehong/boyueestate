package com.bafuestate.message.modules.group.member;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.bafuestate.message.component.TitleBarLayout;
import com.bafuestate.message.modules.group.info.GroupInfo;
import com.bafuestate.message.utils.PopWindowUtil;
import com.bafuestate.message.R;
import com.bafuestate.message.modules.group.interfaces.IGroupMemberLayout;


public class GroupMemberManagerLayout extends LinearLayout implements IGroupMemberLayout {

    private TitleBarLayout mTitleBar;
    private AlertDialog mDialog;
    private GroupMemberManagerAdapter mAdapter;
    private IGroupMemberRouter mGroupMemberManager;
    private GroupInfo mGroupInfo;

    public GroupMemberManagerLayout(Context context) {
        super(context);
        init();
    }

    public GroupMemberManagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GroupMemberManagerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.group_member_layout, this);
        mTitleBar = findViewById(R.id.group_member_title_bar);
        mTitleBar.setTitle(getContext().getString(R.string.manager), TitleBarLayout.POSITION.RIGHT);
        mTitleBar.getRightIcon().setVisibility(GONE);
        mTitleBar.setOnRightClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buildPopMenu();
            }
        });
        mAdapter = new GroupMemberManagerAdapter();
        mAdapter.setMemberChangedCallback(new IGroupMemberChangedCallback() {

            @Override
            public void onMemberRemoved(GroupMemberInfo memberInfo) {
                mTitleBar.setTitle(getContext().getString(R.string.group_members) + "(" + mGroupInfo.getMemberDetails().size() + ")", TitleBarLayout.POSITION.MIDDLE);
            }
        });
        GridView gridView = findViewById(R.id.group_all_members);
        gridView.setAdapter(mAdapter);
    }

    public TitleBarLayout getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void setParentLayout(Object parent) {

    }

    public void setDataSource(GroupInfo groupInfo) {
        mGroupInfo = groupInfo;
        mAdapter.setDataSource(groupInfo);
        if (groupInfo != null) {
            mTitleBar.setTitle(getContext().getString(R.string.group_members) + "(" + groupInfo.getMemberDetails().size() + ")", TitleBarLayout.POSITION.MIDDLE);
        }
    }

    private void buildPopMenu() {
        if (mGroupInfo == null) {
            return;
        }
        if (mDialog == null) {
            mDialog = PopWindowUtil.buildFullScreenDialog((Activity) getContext());
            View moreActionView = inflate(getContext(), R.layout.group_member_pop_menu, null);
            moreActionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                }
            });
            Button addBtn = moreActionView.findViewById(R.id.add_group_member);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mGroupMemberManager != null) {
                        mGroupMemberManager.forwardAddMember(mGroupInfo);
                    }
                    mDialog.dismiss();

                }
            });
            Button deleteBtn = moreActionView.findViewById(R.id.remove_group_member);
            if (!mGroupInfo.isOwner()) {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mGroupMemberManager != null) {
                        mGroupMemberManager.forwardDeleteMember(mGroupInfo);
                    }
                    mDialog.dismiss();
                }
            });
            Button cancelBtn = moreActionView.findViewById(R.id.cancel);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                }
            });
            mDialog.setContentView(moreActionView);
        } else {
            mDialog.show();
        }

    }

    public void setRouter(IGroupMemberRouter callBack) {
        this.mGroupMemberManager = callBack;
    }

}
