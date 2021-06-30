package com.ibai.message.modules.group.interfaces;

import com.ibai.message.base.ILayout;
import com.ibai.message.modules.group.info.GroupInfo;


public interface IGroupMemberLayout extends ILayout {

    void setDataSource(GroupInfo dataSource);

}
