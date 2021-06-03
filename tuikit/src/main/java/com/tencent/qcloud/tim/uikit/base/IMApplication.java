package com.tencent.qcloud.tim.uikit.base;

import com.kotlin.base.BuildConfig;
import com.kotlin.base.common.BaseApplication;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.help.ConfigHelper;

public class IMApplication extends BaseApplication {

    @Override
    public void initIM() {
        if (TUIKit.isMainProcess(getApplicationContext())) {
//            TUIKit.init(this, 1400497202, new ConfigHelper().getConfigs());
            TUIKit.init(this, 1400530379, new ConfigHelper().getConfigs());
        }
    }
}
