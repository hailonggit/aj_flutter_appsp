package com.anji.appsp.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.anji.appsp.sdk.model.AppSpModel;
import com.anji.appsp.sdk.model.SpNotice;
import com.anji.appsp.sdk.model.SpNoticeModelItem;
import com.anji.appsp.sdk.model.SpRespNoticeModel;
import com.anji.appsp.sdk.model.SpRespUpdateModel;
import com.anji.appsp.sdk.model.SpVersion;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler json data
 *
 * @author chenhailong
 * Create Date:2020.7.2
 */
public class SpHandler extends Handler {
    private Context context;
    private String jsonContent;
    private IAppSpVersionUpdateCallback iAppSpVersionUpdateCallback;
    private IAppSpNoticeCallback iAppSpNoticeCallback;

    public SpHandler(Context context) {
        this.context = context;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public void setIAppSpVersionCallback(IAppSpVersionUpdateCallback iAppSpVersionUpdateCallback) {
        this.iAppSpVersionUpdateCallback = iAppSpVersionUpdateCallback;
    }

    public void setIAppSpNoticeCallback(IAppSpNoticeCallback iAppSpNoticeCallback) {
        this.iAppSpNoticeCallback = iAppSpNoticeCallback;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case SpConstant.Msg_FileDownload_Success:
                handleSuccess(AppSpStatusCode.StatusCode_Success);
                break;
            case SpConstant.Msg_FileDownload_Cancel:
                handleException(AppSpStatusCode.StatusCode_Cancel);
                break;
            case SpConstant.Msg_FileDownload_Timeout:
                handleException(AppSpStatusCode.StatusCode_Timeout);
                break;
            case SpConstant.Msg_FileDownload_UrlFormatError:
                handleException(AppSpStatusCode.StatusCode_UrlFormatError);
                break;
            default:
                break;
        }
        super.handleMessage(msg);
    }

    private void handleSuccess(int statusCode) {
        if (iAppSpVersionUpdateCallback != null) {
            //Update
            SpRespUpdateModel updateModel = getRespModel();
            updateModel.setStatusCode(statusCode);
            iAppSpVersionUpdateCallback.update(updateModel);
        }
        if (iAppSpNoticeCallback != null) {
            //Notice
            SpRespNoticeModel noticeModel = new SpRespNoticeModel();
            List<SpNoticeModelItem> noticeModels = getNoticeList();
            noticeModel.setModelItemList(noticeModels);
            noticeModel.setStatusCode(statusCode);
            iAppSpNoticeCallback.notice(noticeModel);
        }
    }

    private void handleException(int statusCode) {
        if (iAppSpVersionUpdateCallback != null) {
            //Update
            SpRespUpdateModel updateModel = new SpRespUpdateModel();
            updateModel.setStatusCode(statusCode);
            iAppSpVersionUpdateCallback.update(updateModel);
        }
        if (iAppSpNoticeCallback != null) {
            //Notice
            SpRespNoticeModel noticeModel = new SpRespNoticeModel();
            noticeModel.setStatusCode(statusCode);
            iAppSpNoticeCallback.notice(noticeModel);
        }
    }

    /**
     * @return json string -> AppSpModel
     */
    private AppSpModel createSpModel() {
        AppSpModel spModel = null;
        SpLog.d("SpHandler jsonContent " + jsonContent);
        if (jsonContent != null) {
            try {
                spModel = new Gson().fromJson(jsonContent, AppSpModel.class);
            } catch (Exception e) {
                SpLog.e("Json parse error " + e.toString());
            }
        }
        return spModel;
    }

    /**
     * @return get apk download url
     */
    private SpRespUpdateModel getRespModel() {
        boolean external = false;
        SpRespUpdateModel updateModel = new SpRespUpdateModel();
        AppSpModel spModel = createSpModel();
        SpLog.d(" getRespModel " + spModel);
        if (spModel == null) {
            return updateModel;
        }
        SpVersion spVersion = spModel.getSpVersion();
        if (spVersion == null) {
            return updateModel;
        }
        String url = spVersion.getDownloadUrl();
        if (url != null && !url.contains(".apk")) {
            external = true;
        }
        updateModel.url = url;
        updateModel.isExternalUrl = external;
        updateModel.showUpdate = checkShowUpdate(spVersion);
        updateModel.mustUpdate = checkMustUpdate(spVersion);
        updateModel.updateLog = spVersion.getUpdateLog();
        return updateModel;
    }

    /**
     * @return app versionCode,we check to show update dialog by versionCode
     */
    private long getVersionCode() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            //如果SDK>=28，返回long型的
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return info.getLongVersionCode();
            }
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * @return app versionCode,we check to show update dialog by versionCode
     */
    private String getSdkVersion() {
        String sdkVersion = Build.VERSION.RELEASE;
        String[] list = sdkVersion.split("[.]");
        if (list != null) {
            if (list.length > 0) {
                return list[0];
            }
        }
        return "1";
    }

    /**
     * check if shw update dialog
     *
     * @return
     */
    private boolean checkShowUpdate(SpVersion spVersion) {
        long versionCode = getVersionCode();
        long versionNumber = 1;

        try {
            versionNumber = Long.parseLong(spVersion.getVersionNumber());
        } catch (Exception e) {

        }
        SpLog.d("System versionCode " + versionCode + " remote versionCode " + versionNumber);
        //如果APP的版本号小于服务器的，必须弹出更新dialog
        if (versionNumber > versionCode) {
            return true;
        }
        return false;
    }

    /**
     * check if must update
     *
     * @return
     */
    private boolean checkMustUpdate(SpVersion spVersion) {
        String sdkVersion = getSdkVersion();
        int sdkInt = 1;
        try {
            sdkInt = Integer.parseInt(sdkVersion);
        } catch (Exception e) {

        }
        String[] list = null;
        String versionConfig = spVersion.getVersionConfig();
        if (versionConfig != null) {
            list = versionConfig.split("[,]");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    try {
                        int configItem = Integer.parseInt(list[i]);
                        if (sdkInt == configItem) {
                            return true;
                        }
                    } catch (Exception e) {

                    }
                }

            }
        }
        return false;
    }

    /**
     * if notice is expired,we ignore it
     *
     * @return
     */
    private List<SpNoticeModelItem> getNoticeList() {
        List<SpNoticeModelItem> notices = new ArrayList<>();
        AppSpModel spModel = createSpModel();
        if (spModel == null) {
            //确保不报错
            return notices;
        }
        List<SpNotice> noticesList = spModel.getSpNotices();
        if (noticesList == null) {
            noticesList = new ArrayList<>();
        }
        long currentTime = System.currentTimeMillis();
        for (SpNotice notice : noticesList) {
            SpLog.d("currentTime " + currentTime + " notice.getStartTime() " + notice.getStartTime() + " notice.getEndTime() " + notice.getEndTime());
            if (notice.getStartTime() < currentTime && notice.getEndTime() > currentTime) {
                SpNoticeModelItem noticeItem = new SpNoticeModelItem();
                noticeItem.title = notice.getTitle();
                noticeItem.details = notice.getDetails();
                noticeItem.templateType = notice.getTemplateType();
                noticeItem.templateTypeName = notice.getTemplateTypeName();
                notices.add(noticeItem);
            }
        }
        return notices;
    }
}
