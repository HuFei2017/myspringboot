package com.learning.schema.input;

import com.learning.schema.ColumnInfo;
import com.learning.schema.ColumnSort;

import java.util.List;
import java.util.Objects;

public class TableCreateInput {
    private String databaseName = "default";
    private String tableType;
    private String tableName;
    private List<ColumnInfo> columnList;
    private String comment;
    private List<ColumnInfo> columnPartition;
    private List<ColumnSort> columnSort;
    private int bucketNumber;
    private List<String> skewName;
    private List<List<String>> skewValue;
    private String directories;
    private String fileFormat;

    public TableCreateInput(String databaseName, String tableType, String tableName, List<ColumnInfo> columnList,
                            String comment, List<ColumnInfo> columnPartition, List<ColumnSort> columnSort,
                            int bucketNumber, List<String> skewName, List<List<String>> skewValue,
                            String directories, String fileFormat) {
        this.databaseName = databaseName;
        this.tableType = tableType;
        this.tableName = tableName;
        this.columnList = columnList;
        this.comment = comment;
        this.columnPartition = columnPartition;
        this.columnSort = columnSort;
        this.bucketNumber = bucketNumber;
        this.skewName = skewName;
        this.skewValue = skewValue;
        this.directories = directories;
        this.fileFormat = fileFormat;
    }

    public TableCreateInput() {
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public void setColumnPartition(List<ColumnInfo> columnPartition) {
        this.columnPartition = columnPartition;
    }

    public void setColumnSort(List<ColumnSort> columnSort) {
        this.columnSort = columnSort;
    }

    public void setBucketNumber(int bucketNumber) {
        this.bucketNumber = bucketNumber;
    }

    public void setSkewName(List<String> skewName) {
        this.skewName = skewName;
    }

    public void setSkewValue(List<List<String>> skewValue) {
        this.skewValue = skewValue;
    }

    public void setDirectories(String directories) {
        this.directories = directories;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public List<String> getSkewName() {
        return skewName;
    }

    public List<List<String>> getSkewValue() {
        return skewValue;
    }

    public String getDirectories() {
        return directories;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        if (databaseName != null && !Objects.equals(databaseName, ""))
            this.databaseName = databaseName;
    }

    public String getTableType() {
        return tableType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnInfo> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnInfo> columnList) {
        this.columnList = columnList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ColumnInfo> getColumnPartition() {
        return columnPartition;
    }

    public List<ColumnSort> getColumnSort() {
        return columnSort;
    }

    public int getBucketNumber() {
        return bucketNumber;
    }

    public String getFileFormat() {
        return fileFormat;
    }
}
