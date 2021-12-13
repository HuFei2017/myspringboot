package com.learning.schema.output;

public class AutoCtlDetail {

    private String name;
    private String calTime;
    private String type;
    private float value;
    private short seq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalTime() {
        return calTime;
    }

    public void setCalTime(String calTime) {
        this.calTime = calTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public short getSeq() {
        return seq;
    }

    public void setSeq(short seq) {
        this.seq = seq;
    }
}
