package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.tencent.qcloud.tim.uikit.component.activity.ChatActivity;
import com.tencent.qcloud.tim.uikit.component.fragment.ConversationFragment;
import java.lang.Override;
import java.lang.String;
import java.util.Map;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class ARouter$$Group$$MessageCenter implements IRouteGroup {
  @Override
  public void loadInto(Map<String, RouteMeta> atlas) {
    atlas.put("/MessageCenter/Chat", RouteMeta.build(RouteType.ACTIVITY, ChatActivity.class, "/messagecenter/chat", "messagecenter", null, -1, -2147483648));
    atlas.put("/MessageCenter/Message", RouteMeta.build(RouteType.FRAGMENT, ConversationFragment.class, "/messagecenter/message", "messagecenter", null, -1, -2147483648));
  }
}
