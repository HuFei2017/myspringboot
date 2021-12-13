package com.learning.schema;

import java.util.ArrayList;
import java.util.List;

public class TableDescription {
    private List<ColumnInfo> tableInfo = new ArrayList<>();
    private String tableCreation;

    public TableDescription() {
    }

    public TableDescription(List<ColumnInfo> tableInfo, String tableCreation) {
        this.tableInfo = tableInfo;
        this.tableCreation = tableCreation;
    }

    public List<ColumnInfo> getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(List<ColumnInfo> tableInfo) {
        this.tableInfo = tableInfo;
    }

    public String getTableCreation() {
        return tableCreation;
    }

    public void setTableCreation(String tableCreation) {
        this.tableCreation = tableCreation;
    }
}
