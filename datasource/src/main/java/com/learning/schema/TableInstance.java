package com.learning.schema;

import java.util.ArrayList;
import java.util.List;

public class TableInstance {
    private List<String> columnList=new ArrayList<>();
    private List<List<String>> queryResult=new ArrayList<>();
    private long timeConsuming=0;//ms

    public TableInstance() {
    }

    public TableInstance(List<String> columnList, List<List<String>> queryResult, long timeConsuming) {
        this.columnList = columnList;
        this.queryResult = queryResult;
        this.timeConsuming = timeConsuming;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

    public List<List<String>> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<List<String>> queryResult) {
        this.queryResult = queryResult;
    }

    public long getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }
}
