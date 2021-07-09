package com.bafuestate.message.base;

import com.bafuestate.message.TUIKit;
import com.bafuestate.message.help.ConfigHelper;
import com.kotlin.base.common.BaseApplication;

public class IMApplication extends BaseApplication {

    @Override
    public void initIM() {
        if (TUIKit.isMainProcess(getApplicationContext())) {
//            TUIKit.init(this, 1400497202, new ConfigHelper().getConfigs());
            TUIKit.init(this, 1400493176, new ConfigHelper().getConfigs());
        }
    }
}
