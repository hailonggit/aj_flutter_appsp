package com.anji.appsp.sdk.model;


import java.io.Serializable;

/**
 * @author chenhailong
 * Create Date:2020.7.2
 */


public class SpRespUpdateModel implements Serializable {
    //     apk download url
    public String url;
    //      if set true,we snap to H5 page to download apk,
    //      otherwise,we install apk internally
    public boolean isExternalUrl;
    //      check if show update dialog
    public boolean showUpdate;
    //     if set true,we do block user interaction,
    //     we exit application if we do not want to update,
    //     otherwise,user can cancel update schedule,
    public boolean mustUpdate;

    public int statusCode;
    //release note
    public String updateLog;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isExternalUrl() {
        return isExternalUrl;
    }

    public void setExternalUrl(boolean externalUrl) {
        isExternalUrl = externalUrl;
    }

    public boolean isShowUpdate() {
        return showUpdate;
    }

    public void setShowUpdate(boolean showUpdate) {
        this.showUpdate = showUpdate;
    }

    public boolean isMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(boolean mustUpdate) {
        this.mustUpdate = mustUpdate;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "SpRespUpdateModel{" +
                "url='" + url + '\'' +
                ", isExternalUrl=" + isExternalUrl +
                ", showUpdate=" + showUpdate +
                ", mustUpdate=" + mustUpdate +
                ", statusCode=" + statusCode +
                ", updateLog='" + updateLog + '\'' +
                '}';
    }
}
