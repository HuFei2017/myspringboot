package com.learning.schema.output;

import java.util.List;

public class AutoTable {
    private List<AutoCtlDetail> autoCtlDetail;
    private int count;

    public List<AutoCtlDetail> getAutoCtlDetail() {
        return autoCtlDetail;
    }

    public void setAutoCtlDetail(List<AutoCtlDetail> autoCtlDetail) {
        this.autoCtlDetail = autoCtlDetail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
