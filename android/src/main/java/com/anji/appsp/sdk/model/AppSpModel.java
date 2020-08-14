package com.anji.appsp.sdk.model;

import java.util.List;

/**
 * @author chenhailong
 * Create Date:2020.7.2
 */
public class AppSpModel {
    private List<SpNotice> spNotices;
    private SpVersion spVersion;

    public void setSpNotices(List<SpNotice> spNotices) {
        this.spNotices = spNotices;
    }

    public List<SpNotice> getSpNotices() {
        return spNotices;
    }

    public void setSpVersion(SpVersion spVersion) {
        this.spVersion = spVersion;
    }

    public SpVersion getSpVersion() {
        return spVersion;
    }

    @Override
    public String toString() {
        return "AppSpModel{" +
                "spNotices=" + spNotices +
                ", spVersion=" + spVersion +
                '}';
    }
}