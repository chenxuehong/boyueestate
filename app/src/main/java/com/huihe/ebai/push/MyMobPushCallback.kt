package com.huihe.ebai.push

import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.LogUtils
import com.mob.pushsdk.MobPush
import com.mob.pushsdk.MobPushCallback

class MyMobPushCallback : MobPushCallback<String> {

    override fun onCallback(rid: String) {
        LogUtils.i(TAG, ": $rid")
        AppPrefsUtils.putString(BaseConstant.KEY_SP_REGISTRATIONID, rid)
        MobPush.getDeviceToken {
            com.ibai.message.utils.ThirdPushTokenMgr.getInstance().thirdPushToken = it

        }
    }

    companion object {
        private val TAG = MyMobPushCallback::class.java.simpleName
    }
}