package com.learning.schema.output;

import java.util.List;

public class SoftRate {
    private String id;
    private String itemname;
    private List<String> schema;
    private List<List<Object>> values;
    private int count=0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public List<String> getSchema() {
        return schema;
    }

    public void setSchema(List<String> schema) {
        this.schema = schema;
    }

    public List<List<Object>> getValues() {
        return values;
    }

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
