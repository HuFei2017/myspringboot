package com.learning.schema.input;

import com.learning.schema.KeyMap;

import java.util.List;

public class TableAltering {

    private String databaseName;
    private String tableName;
    private List<KeyMap> columnList;
    private List<String> dropColumnList;

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

    public List<KeyMap> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<KeyMap> columnList) {
        this.columnList = columnList;
    }

    public List<String> getDropColumnList() {
        return dropColumnList;
    }

    public void setDropColumnList(List<String> dropColumnList) {
        this.dropColumnList = dropColumnList;
    }
}
