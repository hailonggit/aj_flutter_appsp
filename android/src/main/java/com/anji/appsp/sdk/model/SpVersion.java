package com.anji.appsp.sdk.model;

/**
 * @author chenhailong
 * Create Date:2020.7.2
 */
public class SpVersion {
    private String downloadUrl;
    private String platform;
    private String updateLog;
    private String versionConfig;
    private String versionName;
    private String versionNumber;


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public void setVersionConfig(String versionConfig) {
        this.versionConfig = versionConfig;
    }

    public String getVersionConfig() {
        return versionConfig;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    @Override
    public String toString() {
        return "SpVersion{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", platform='" + platform + '\'' +
                ", updateLog='" + updateLog + '\'' +
                ", versionConfig='" + versionConfig + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionNumber='" + versionNumber + '\'' +
                '}';
    }
}