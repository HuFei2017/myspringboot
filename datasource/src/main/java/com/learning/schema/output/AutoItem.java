package com.learning.schema.output;

public class AutoItem {

    private String name;
    private float rate;//率
    private float count;//回路总数
    private float num;//数
    private float uncount;//不参与统计

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public float getUncount() {
        return uncount;
    }

    public void setUncount(float uncount) {
        this.uncount = uncount;
    }
}
