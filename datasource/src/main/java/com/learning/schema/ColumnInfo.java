package com.learning.schema;

public class ColumnInfo {
    private String id;
    private String columnName;
    private String columnType;
    private String comment;
    private String type = "column";

    public ColumnInfo(String id, String columnName, String columnType, String comment, String type) {
        this.id = id;
        this.columnName = columnName;
        this.columnType = columnType;
        this.comment = comment;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ColumnInfo() {
    }

    public ColumnInfo(String columnName, String columnType, String comment) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.comment = comment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
