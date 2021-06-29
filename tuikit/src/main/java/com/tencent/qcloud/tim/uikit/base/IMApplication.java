package com.tencent.qcloud.tim.uikit.base;

import com.kotlin.base.common.BaseApplication;
import com.kotlin.provider.BuildConfig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.help.ConfigHelper;

public class IMApplication extends BaseApplication {

    @Override
    public void initIM() {
        if (TUIKit.isMainProcess(getApplicationContext())) {
            TUIKit.init(this, BuildConfig.MessageAppID, new ConfigHelper().getConfigs());
        }
    }
}
