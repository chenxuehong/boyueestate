package com.bafuestate.message.component.activity;

import android.view.View;
import android.widget.FrameLayout;

import com.kotlin.base.ui.activity.BaseTitleActivity;
import com.kotlin.base.widgets.HeaderBar;
import com.bafuestate.message.R;
import com.bafuestate.message.modules.contact.ContactItemBean;
import com.bafuestate.message.modules.contact.ContactListView;

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
