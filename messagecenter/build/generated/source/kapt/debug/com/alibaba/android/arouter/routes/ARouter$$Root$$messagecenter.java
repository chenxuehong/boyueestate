package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.alibaba.android.arouter.facade.template.IRouteRoot;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.Map;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class ARouter$$Root$$messagecenter implements IRouteRoot {
  @Override
  public void loadInto(Map<String, Class<? extends IRouteGroup>> routes) {
    routes.put("MessageCenter", ARouter$$Group$$MessageCenter.class);
  }
}
