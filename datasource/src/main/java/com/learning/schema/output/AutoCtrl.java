package com.learning.schema.output;

import java.util.List;

public class AutoCtrl {

    private short level;
    private List<String> xList;
    private List<Float> autoCtlList;
    private List<Float> autoCountList;
    private List<Float> uncountList;
    private List<Float> countList;

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public List<String> getxList() {
        return xList;
    }

    public void setxList(List<String> xList) {
        this.xList = xList;
    }

    public List<Float> getAutoCtlList() {
        return autoCtlList;
    }

    public void setAutoCtlList(List<Float> autoCtlList) {
        this.autoCtlList = autoCtlList;
    }

    public List<Float> getAutoCountList() {
        return autoCountList;
    }

    public void setAutoCountList(List<Float> autoCountList) {
        this.autoCountList = autoCountList;
    }

    public List<Float> getUncountList() {
        return uncountList;
    }

    public void setUncountList(List<Float> uncountList) {
        this.uncountList = uncountList;
    }

    public List<Float> getCountList() {
        return countList;
    }

    public void setCountList(List<Float> countList) {
        this.countList = countList;
    }
}
