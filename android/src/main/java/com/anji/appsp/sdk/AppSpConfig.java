package com.anji.appsp.sdk;

import android.content.Context;

/**
 * @author chenhailong
 * Create Date:2020.7.2
 */
public class AppSpConfig {
    private static volatile AppSpConfig appSpConfig;
    private static final String Host = "http://10.108.26.128/version/";
    private SpHandler spHandler;
    private String url;

    private AppSpConfig() {
    }

    public static synchronized AppSpConfig getInstance() {
        if (appSpConfig == null) {
            appSpConfig = new AppSpConfig();
        }
        return appSpConfig;
    }

    /**
     * App-Sp init
     *
     * @param context context may indicate activity/fragment/view
     * @param appkey  we combine appkey with url to fetch json file from ngnix server
     */
    public void init(Context context, String appkey) {
        if (!NetworkStatusUtil.isAvailable(context)) {
            SpLog.e("Network is not available, please checkout network setting");
            return;
        }
        if (spHandler == null) {
            spHandler = new SpHandler(context);
        }
        url = Host + appkey + "_Android.json";
        SpLog.d(" url is  " + url);
    }

    public void setVersionUpdateCallback(IAppSpVersionUpdateCallback iAppSpVersionUpdateCallback) {
        spHandler.setIAppSpVersionCallback(iAppSpVersionUpdateCallback);
        spHandler.setIAppSpNoticeCallback(null);
        startDowload();
    }

    public void setNoticeCallback(IAppSpNoticeCallback iAppSpNoticeCallback) {
        spHandler.setIAppSpNoticeCallback(iAppSpNoticeCallback);
        spHandler.setIAppSpVersionCallback(null);
        startDowload();
    }

    private void startDowload() {
        FileDownloadTask fileDownloadTask = new FileDownloadTask(url, spHandler);
        fileDownloadTask.execute(url);
    }
}
