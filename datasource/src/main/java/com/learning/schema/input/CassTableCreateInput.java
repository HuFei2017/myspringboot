package com.learning.schema.input;

import com.learning.schema.CassColumnInfo;
import com.learning.schema.KeyMap;

import java.util.List;

public class CassTableCreateInput {

    private String databaseName;
    private String tableName;
    private List<CassColumnInfo> columnList;
    private List<List<String>> primaryList;
    private boolean isStorage = false;
    private List<KeyMap> orderList;
    private List<KeyMap> optionList;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<CassColumnInfo> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<CassColumnInfo> columnList) {
        this.columnList = columnList;
    }

    public List<List<String>> getPrimaryList() {
        return primaryList;
    }

    public void setPrimaryList(List<List<String>> primaryList) {
        this.primaryList = primaryList;
    }

    public boolean isStorage() {
        return isStorage;
    }

    public void setStorage(boolean storage) {
        isStorage = storage;
    }

    public List<KeyMap> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<KeyMap> orderList) {
        this.orderList = orderList;
    }

    public List<KeyMap> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<KeyMap> optionList) {
        this.optionList = optionList;
    }
}
