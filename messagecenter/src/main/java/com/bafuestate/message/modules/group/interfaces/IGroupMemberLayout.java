package com.bafuestate.message.modules.group.interfaces;

import com.bafuestate.message.base.ILayout;
import com.bafuestate.message.modules.group.info.GroupInfo;


public interface IGroupMemberLayout extends ILayout {

    void setDataSource(GroupInfo dataSource);

}
