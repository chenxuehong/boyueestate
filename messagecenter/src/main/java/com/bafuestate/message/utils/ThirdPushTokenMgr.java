package com.bafuestate.message.utils;

import android.text.TextUtils;
import android.util.Log;

import com.kotlin.provider.constant.MessageConstant;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushToken;

public class ThirdPushTokenMgr {
    private String mThirdPushToken;
    private boolean mIsTokenSet;

    private static final String TAG = ThirdPushTokenMgr.class.getSimpleName();

    public static ThirdPushTokenMgr getInstance() {
        return ThirdPushTokenHolder.instance;
    }

    private static class ThirdPushTokenHolder {
        private static final
        ThirdPushTokenMgr instance = new ThirdPushTokenMgr();
    }

    public String getThirdPushToken() {
        return mThirdPushToken;
    }

    public void setThirdPushToken(String mThirdPushToken) {

        this.mThirdPushToken = mThirdPushToken;
    }

    public void setPushTokenToTIM() {

        String token = ThirdPushTokenMgr.getInstance().getThirdPushToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        TIMOfflinePushToken param = null;
        if (IMFunc.isBrandXiaoMi()) {
            param = new TIMOfflinePushToken(MessageConstant.XM_PUSH_BUZID, token);
        } else if (IMFunc.isBrandHuawei()) {
            param = new TIMOfflinePushToken(MessageConstant.HW_PUSH_BUZID, token);
        } else if (IMFunc.isBrandOppo()) {
            param = new TIMOfflinePushToken(MessageConstant.OPPO_PUSH_BUZID, token);
        } else if (IMFunc.isBrandMeizu()) {
            param = new TIMOfflinePushToken(MessageConstant.MEIZHU_PUSH_BUZID, token);
        } else if (IMFunc.isBrandVivo()) {
            param = new TIMOfflinePushToken(MessageConstant.VIVO_PUSH_BUZID, token);
        } else {
            return;
        }
        TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.d(TAG, "setOfflinePushToken err code = " + code);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "setOfflinePushToken success");
                mIsTokenSet = true;
            }
        });
    }
}
