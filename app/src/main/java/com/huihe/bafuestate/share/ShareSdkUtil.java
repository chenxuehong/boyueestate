package com.huihe.bafuestate.share;

import android.text.TextUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareSdkUtil {

    public static void shareWechat(
            String title,
            String content,
            String url,
            String imagePath,
            String imageUrl,
            PlatformActionListener actionListener) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(title);
        sp.setText(content);
        if (!TextUtils.isEmpty(imagePath)) {
            sp.setImagePath(imagePath);
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            sp.setImageUrl(imageUrl);
        }
        if (!TextUtils.isEmpty(url)) {
            sp.setUrl(url);
        }
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform tw = ShareSDK.getPlatform(Wechat.NAME);
        tw.setPlatformActionListener(actionListener); // 设置分享事件回调
        // 执行图文分享
        tw.share(sp);
    }

    public static void shareWechatMoments(
            String title,
            String content,
            String url,
            String imagePath,
            String imageUrl,
            PlatformActionListener actionListener) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(title);
        sp.setText(content);
        if (!TextUtils.isEmpty(imagePath)) {
            sp.setImagePath(imagePath);
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            sp.setImageUrl(imageUrl);
        }
        sp.setShareType(Platform.SHARE_WEBPAGE);
        if (!TextUtils.isEmpty(url)) {
            sp.setUrl(url);
        }
        Platform tw = ShareSDK.getPlatform(WechatMoments.NAME);
        tw.setPlatformActionListener(actionListener); // 设置分享事件回调
        // 执行图文分享
        tw.share(sp);
    }

    public static void shareWxminiProgram(
            String title,
            String content,
            String url,
            String imagePath,
            String imageUrl,
            String wxPath,
            PlatformActionListener actionListener) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WXMINIPROGRAM);
        sp.setTitle(title);
        sp.setText(content);
        sp.setWxPath(wxPath);
        if (!TextUtils.isEmpty(imagePath)) {
            sp.setImagePath(imagePath);
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            sp.setImageUrl(imageUrl);
        }
        sp.setUrl(wxPath);
        Platform tw = ShareSDK.getPlatform(Wechat.NAME);
        tw.setPlatformActionListener(actionListener); // 设置分享事件回调
        // 执行图文分享
        tw.share(sp);
    }
}
