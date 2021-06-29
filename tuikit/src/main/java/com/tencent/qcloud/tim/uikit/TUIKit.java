package com.tencent.qcloud.tim.uikit;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMGroupMemberOperationResult;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.component.activity.ChatActivity;
import com.tencent.qcloud.tim.uikit.config.MessageConstants;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TUIKit {

    /**
     * TUIKit的初始化函数
     *
     * @param context  应用的上下文，一般为对应应用的ApplicationContext
     * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
     * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
     */
    public static void init(Context context, int sdkAppID, TUIKitConfigs configs) {
        TUIKitImpl.init(context, sdkAppID, configs);
    }

    public static boolean isMainProcess(Context context){
        return SessionWrapper.isMainProcess(context);
    }

    /**
     * 释放一些资源等，一般可以在退出登录时调用
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void unInit() {
        TUIKitImpl.unInit();
    }

    /**
     * 获取TUIKit保存的上下文Context，该Context会长期持有，所以应该为Application级别的上下文
     *
     * @return
     */
    public static Context getAppContext() {
        return TUIKitImpl.getAppContext();
    }

    /**
     * 获取TUIKit的全部配置
     *
     * @return
     */
    public static TUIKitConfigs getConfigs() {
        return TUIKitImpl.getConfigs();
    }

    /**
     * 设置TUIKit的IM消息的全局监听
     *
     * @param listener
     */
    public static void addIMEventListener(IMEventListener listener) {
        TUIKitImpl.addIMEventListener(listener);
    }

    /**
     * 删除TUIKit的IM消息的全局监听
     *
     * @param listener 如果为空，则删除全部的监听
     */
    public static void removeIMEventListener(IMEventListener listener) {
        TUIKitImpl.removeIMEventListener(listener);
    }

    private static Map<String,String> useridAndsigMap;
    static {
        useridAndsigMap = new HashMap<>();
        useridAndsigMap.put("4260","eJw1zF0LgjAUxvHvsuuws7mzpdAHiCzohSDoJtuSk6ymrhKi755pXT6-B-4vts020cPWLGUiAjbqNxl7DXSmnqVQf29MefSeDEu5BJAJRzkZHtt6qm3niCgAYNBA7muKY6xAJ-xXoaLL3pvDeBcWqOP96bai2brN*MXNK9e65TPHzJVBWK*rXIhiyt4fumcxIw__");
        useridAndsigMap.put("4262","eJwtzF0LgjAYBeD-sttC3s1tLKELIQhbUJGKXSZb8ibJ-ECk6L9n6uV5zuF8SHy8er1tSECYB2Q9ZTS26vCBE1Ngvr8UrSnvzqEhAeUAfEMFV3NjB4eNHV0IwQBg1g5ff5NUUCkFW7YtFuNvzaJ9rvWJZuUhqdNdkuaq0e9wyEJQ50sVrXoZ356Fc2pLvj*QsDAS");
    }
    /**
     * 用户IM登录
     *
     * @param userid   用户名
     * @param usersig  从业务服务器获取的usersig
     * @param callback 登录是否成功的回调
     */
    public static void login(String userid, String usersig, final IUIKitCallBack callback) {

        TUIKitImpl.login(userid, usersig, callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void startLive(Context context, String data) {
        TUIKitImpl.startLive(context, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void enterLive(Context context, String data) {
        TUIKitImpl.enterLive(context,data);
    }

    public static void logout(final IUIKitCallBack callback) {
        TUIKitImpl.logout(callback);
    }

    public static void createGroup(String groupId, String groupName, String type, final TIMValueCallBack timValueCallBack) {
        TUIKitImpl.createGroup(groupId, groupName, type, timValueCallBack);
    }

    public static void inviteUserToGroup(
            String groupID,
            List<String> userList,
            V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>> callback) {
        TUIKitImpl.inviteUserToGroup(groupID, userList, callback);
    }

    public static void startChat(
            Context context,
            String id,
            String chatName) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(V2TIMConversation.V2TIM_C2C);
        chatInfo.setId(id);
        chatInfo.setChatName(chatName);
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(MessageConstants.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
