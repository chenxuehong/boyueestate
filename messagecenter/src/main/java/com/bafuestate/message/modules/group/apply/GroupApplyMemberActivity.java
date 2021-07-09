package com.bafuestate.message.modules.group.apply;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bafuestate.message.modules.contact.FriendProfileLayout;
import com.bafuestate.message.utils.TUIKitConstants;
import com.bafuestate.message.R;

public class GroupApplyMemberActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_apply_member_activity);
        FriendProfileLayout layout = findViewById(R.id.friend_profile);

        layout.initData(getIntent().getSerializableExtra(TUIKitConstants.ProfileType.CONTENT));
    }

}
