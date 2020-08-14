package com.anji.appsp.sdk.model;
/**
 * @author chenhailong
 * Create Date:2020.7.2
 */
public class SpNotice {
    private String details;
    private long endTime;
    private int id;
    private String name;
    private long startTime;
    private String templateType;
    private String templateTypeName;
    private String title;

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateTypeName(String templateTypeName) {
        this.templateTypeName = templateTypeName;
    }

    public String getTemplateTypeName() {
        return templateTypeName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "SpNotice{" +
                "details='" + details + '\'' +
                ", endTime=" + endTime +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", templateType=" + templateType +
                ", templateTypeName='" + templateTypeName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}