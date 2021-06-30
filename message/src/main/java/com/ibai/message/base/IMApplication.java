package com.ibai.message.base;

import com.ibai.message.TUIKit;
import com.ibai.message.help.ConfigHelper;
import com.kotlin.base.common.BaseApplication;

import static com.kotlin.base.ext.CommonExtKt.getMessageAppID;

public class IMApplication extends BaseApplication {

    @Override
    public void initIM() {
        if (TUIKit.isMainProcess(getApplicationContext())) {
            TUIKit.init(this, getMessageAppID(), new ConfigHelper().getConfigs());
        }
    }
}
