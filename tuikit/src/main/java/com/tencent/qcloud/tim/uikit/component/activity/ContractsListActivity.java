package com.tencent.qcloud.tim.uikit.component.activity;

import android.view.View;
import android.widget.FrameLayout;

import com.kotlin.base.ui.activity.BaseTitleActivity;
import com.kotlin.base.widgets.HeaderBar;
import com.tencent.qcloud.tim.uikit.R;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView;

import org.jetbrains.annotations.NotNull;

public class ContractsListActivity extends BaseTitleActivity {

    ContactListView mContactListView;

    @Override
    public void initView(@NotNull FrameLayout baseTitleContentView) {
        View view = View.inflate(this, R.layout.acivity_contracts_list, null);
        mContactListView = view.findViewById(R.id.activity_contracts_list);
        baseTitleContentView.addView(view);
        mContactListView.loadDataSource(ContactListView.DataSource.CONTACT_LIST);
        mContactListView.setOnItemClickListener(new ContactListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ContactItemBean contact) {

            }
        });
    }

    @Override
    public void initTitle(@NotNull HeaderBar baseTitleHeaderBar) {
        baseTitleHeaderBar.setTitle("联系人");
    }
}
