package com.anji.appsp.sdk.model;

import java.io.Serializable;

public class SpNoticeModelItem implements Serializable {
    public String title;//公告标题
    public String details;//公告内容
    public String templateType;//模板风格
    public String templateTypeName;//模板名称

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateTypeName() {
        return templateTypeName;
    }

    public void setTemplateTypeName(String templateTypeName) {
        this.templateTypeName = templateTypeName;
    }

    @Override
    public String toString() {
        return "SpNoticeModelItem{" +
                "title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", templateType='" + templateType + '\'' +
                ", templateTypeName='" + templateTypeName + '\'' +
                '}';
    }
}
