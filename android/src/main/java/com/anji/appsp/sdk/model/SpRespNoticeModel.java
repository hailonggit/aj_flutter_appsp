package com.anji.appsp.sdk.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenhailong
 * Create Date:2020.7.2
 */


public class SpRespNoticeModel implements Serializable {
    List<SpNoticeModelItem> modelItemList;
    public int statusCode;//状态码

    public List<SpNoticeModelItem> getModelItemList() {
        return modelItemList;
    }

    public void setModelItemList(List<SpNoticeModelItem> modelItemList) {
        this.modelItemList = modelItemList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
