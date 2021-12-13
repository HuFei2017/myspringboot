package com.learning.schema;

public class ColumnSort {
    private String columnName;
    private String sortType;

    public ColumnSort() {
    }

    public ColumnSort(String columnName, String sortType) {
        this.columnName = columnName;
        this.sortType = sortType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
