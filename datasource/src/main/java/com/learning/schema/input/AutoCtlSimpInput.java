package com.learning.schema.input;

public class AutoCtlSimpInput {
    private String assetid;
    private int pageIndex;
    private int pageSize;

    public String getAssetid() {
        return assetid;
    }

    public void setAssetid(String assetid) {
        this.assetid = assetid;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
