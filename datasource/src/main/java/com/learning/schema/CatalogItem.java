package com.learning.schema;

import java.util.ArrayList;
import java.util.List;

public class CatalogItem {
    private String id;
    private String name;
    private String type="topic";
    private String createTime;
    private String creator;
    private String brief;
    private List<String> labels=new ArrayList<>();
    private String format;

    public CatalogItem() {
    }

    public CatalogItem(String id, String name, String type, String createTime, String creator, String brief, List<String> labels, String format) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createTime = createTime;
        this.creator = creator;
        this.brief = brief;
        this.labels = labels;
        this.format = format;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
