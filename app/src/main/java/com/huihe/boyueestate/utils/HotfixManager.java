package com.huihe.boyueestate.utils;

import android.content.Context;
import android.widget.Toast;

import com.huihe.boyueestate.BuildConfig;
import com.kotlin.base.utils.LogUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;

import java.util.Locale;

public class HotfixManager {
    private static final String TAG = "HotfixManager";

    private static volatile HotfixManager mInstance = null;

    private Context mContext;

    public static HotfixManager getInstance(Context context) {
        if (null == mInstance) {
            synchronized (HotfixManager.class) {
                if (null == mInstance) {
                    mInstance = new HotfixManager(context);
                }
            }
        }
        return mInstance;
    }

    private HotfixManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public void init(String appId, final boolean debug) {
        LogUtils.d(TAG, "HotfixManager init");
        // 60-600秒随机
        int delay = (int) (Math.random() * (600 - 60)) + 60;
        LogUtils.d(TAG, "init delay = " + delay);
        // true表示app启动自动初始化升级模块; false不会自动初始化;
        Beta.autoInit = true;
        // 设置启动延时
        Beta.initDelay = delay * 1000;
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动检查更新,默认为true
        Beta.autoCheckUpgrade = true;
        // 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略
        Beta.upgradeCheckPeriod = 0;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否显示消息通知
        Beta.enableNotification = false;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = false;
        // 设置监听
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                if (debug) {
                    Toast.makeText(mContext, "补丁下载地址dd" + patchFile, Toast.LENGTH_SHORT).show();
                }
                LogUtils.e(TAG, "onPatchReceived " + patchFile);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                if (debug) {
                    Toast.makeText(mContext, String.format(Locale.getDefault(), "%s %d%%",
                            Beta.strNotificationDownloading,
                            (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDownloadSuccess(String msg) {
                if (debug) {
                    Toast.makeText(mContext, "补丁下载成功dd", Toast.LENGTH_SHORT).show();
                }
                LogUtils.d(TAG, "onDownloadSuccess " + msg);

                Beta.applyDownloadedPatch();
            }

            @Override
            public void onDownloadFailure(String msg) {
                if (debug) {
                    Toast.makeText(mContext, "补丁下载失败dd" + msg, Toast.LENGTH_SHORT).show();
                }
                LogUtils.e(TAG, "onDownloadFailure " + msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                if (debug) {
                    Toast.makeText(mContext, "补丁应用成功dd " + msg, Toast.LENGTH_SHORT).show();
                }
                LogUtils.d(TAG, "onApplySuccess " + msg);
            }

            @Override
            public void onApplyFailure(String msg) {
                if (debug) {
                    Toast.makeText(mContext, "补丁应用失败dd " + msg, Toast.LENGTH_SHORT).show();
                }
                LogUtils.e(TAG, "onApplyFailure " + msg);
            }

            @Override
            public void onPatchRollback() {
                if (debug) {
                    Toast.makeText(mContext, "补丁回滚", Toast.LENGTH_SHORT).show();
                }
                LogUtils.d(TAG, "onPatchRollback ");
            }
        };

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        //Bugly.setIsDevelopmentDevice(this, Build.IS_EMULATOR);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        Bugly.init(mContext, appId, debug);
    }

    public void unInit() {
        Beta.unInit();
    }

    /**
     * 代理模式时调用
     */
    public static void installDependency() {
        Beta.installTinker();
    }

    /**
     * 非代理模式时必须调用这个
     *
     * @param context 上下文
     */
    public static void installDependency(Object context) {
        Beta.installTinker(context);
    }

    public void checkHotfix() {
        LogUtils.e("check hotfix", "check hotfix");
        //第一个参数表示是否通过点击检查更新,第二个参数表示是否静默检查更新，如果静默不会弹窗，toast与用户交互
        Beta.checkUpgrade(false, !BuildConfig.DEBUG);
    }
}
