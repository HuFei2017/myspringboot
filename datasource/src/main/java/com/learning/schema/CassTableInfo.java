package com.learning.schema;

import java.util.List;

public class CassTableInfo {
    private String tableName;
    private String createCQL;
    private List<KeyMap> columns;
    private String type="table";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreateCQL() {
        return createCQL;
    }

    public void setCreateCQL(String createCQL) {
        this.createCQL = createCQL;
    }

    public List<KeyMap> getColumns() {
        return columns;
    }

    public void setColumns(List<KeyMap> columns) {
        this.columns = columns;
    }
}
