package com.learning.schema.input;

import java.util.List;

public class CatalogInput {

    private String pid;
    private String dirName;
    private String dirType;
    private String topicID;
    private String userName;
    private String dirDesc;
    private String hql;
    private String brief;
    private List<String> dirLabel;
    private String format;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public CatalogInput() {
    }

    public CatalogInput(String pid, String dirName, String dirType, String topicID, String userName,
                        String dirDesc, String hql, String brief, List<String> dirLabel, String format) {
        this.pid = pid;
        this.dirName = dirName;
        this.dirType = dirType;
        this.topicID = topicID;
        this.userName = userName;
        this.dirDesc = dirDesc;
        this.hql = hql;
        this.brief = brief;
        this.dirLabel = dirLabel;
        this.format = format;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public void setDirType(String dirType) {
        this.dirType = dirType;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDirDesc(String dirDesc) {
        this.dirDesc = dirDesc;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setDirLabel(List<String> dirLabel) {
        this.dirLabel = dirLabel;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPid() {
        return pid;
    }

    public String getDirName() {
        return dirName;
    }

    public String getDirType() {
        return dirType;
    }

    public String getTopicID() {
        return topicID;
    }

    public String getUserName() {
        return userName;
    }

    public String getDirDesc() {
        return dirDesc;
    }

    public String getHql() {
        return hql;
    }

    public String getBrief() {
        return brief;
    }

    public List<String> getDirLabel() {
        return dirLabel;
    }

    public String getFormat() {
        return format;
    }
}
