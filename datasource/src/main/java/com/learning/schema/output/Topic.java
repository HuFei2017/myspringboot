package com.learning.schema.output;

public class Topic {
    private String id;
    private String name;
    private String creator;
    private String time;
    private String address;
    private String attr;

    public Topic() {
    }

    public Topic(String id, String name, String creator, String time, String address, String attr) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.time = time;
        this.address = address;
        this.attr = attr;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
