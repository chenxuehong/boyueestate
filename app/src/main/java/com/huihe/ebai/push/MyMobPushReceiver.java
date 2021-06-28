package com.huihe.ebai.push;

import android.content.Context;
import android.util.Log;

import com.kotlin.base.utils.LogUtils;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyMobPushReceiver implements MobPushReceiver {
    private static final String TAG = MyMobPushReceiver.class.getSimpleName();

    @Override
    public void onCustomMessageReceive(Context context, MobPushCustomMessage mobPushCustomMessage) {
        //接收到自定义消息（透传消息）
        String content = mobPushCustomMessage.getContent();
        LogUtils.i(TAG, "onCustomMessageReceive: content: " + content);
        HashMap<String, String> extrasMap = mobPushCustomMessage.getExtrasMap();
        Iterator<Map.Entry<String, String>> iterator = extrasMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String value = next.getValue();
            String key = next.getKey();
            LogUtils.i(TAG, "onCustomMessageReceive: key : " + key);
            LogUtils.i(TAG, "onCustomMessageReceive: value : " + value);
        }
    }

    @Override
    public void onNotifyMessageReceive(Context context, MobPushNotifyMessage mobPushNotifyMessage) {
        //接收到通知消息
        String title = mobPushNotifyMessage.getTitle();
        String content = mobPushNotifyMessage.getContent();
        String styleContent = mobPushNotifyMessage.getStyleContent();
        int channel = mobPushNotifyMessage.getChannel();
        LogUtils.i(TAG, "onNotifyMessageReceive: title: " + title);
        LogUtils.i(TAG, "onNotifyMessageReceive: content: " + content);
        LogUtils.i(TAG, "onNotifyMessageReceive: styleContent: " + styleContent);
        LogUtils.i(TAG, "onNotifyMessageReceive: channel: " + channel);
        HashMap<String, String> extrasMap = mobPushNotifyMessage.getExtrasMap();
        Iterator<Map.Entry<String, String>> iterator = extrasMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            LogUtils.i(TAG, "onNotifyMessageReceive: key : " + key);
            LogUtils.i(TAG, "onNotifyMessageReceive: value : " + value);
        }
        String data = extrasMap.get("pushMessageParam");
        Log.d(TAG, "receiver data = " + data);
    }

    @Override
    public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage mobPushNotifyMessage) {
        //通知被点击事件
        String title = mobPushNotifyMessage.getTitle();
        String content = mobPushNotifyMessage.getContent();
        String styleContent = mobPushNotifyMessage.getStyleContent();
        LogUtils.i(TAG, "onNotifyMessageOpenedReceive: title: " + title);
        LogUtils.i(TAG, "onNotifyMessageOpenedReceive: content: " + content);
        LogUtils.i(TAG, "onNotifyMessageOpenedReceive: styleContent: " + styleContent);
        HashMap<String, String> extrasMap = mobPushNotifyMessage.getExtrasMap();
        Iterator<Map.Entry<String, String>> iterator = extrasMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            LogUtils.i(TAG, "onNotifyMessageOpenedReceive: key : " + key);
            LogUtils.i(TAG, "onNotifyMessageOpenedReceive: value : " + value);
        }
        String data = extrasMap.get("pushMessageParam");
        Log.d(TAG, "receiver data = " + data);

    }

    @Override
    public void onTagsCallback(Context context, String[] strings, int i, int i1) {
        //标签操作回调
    }

    @Override
    public void onAliasCallback(Context context, String s, int i, int i1) {
        //别名操作回调
    }
}
