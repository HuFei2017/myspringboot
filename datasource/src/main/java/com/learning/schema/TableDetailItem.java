package com.learning.schema;

public class TableDetailItem {
    private String tableName;
    private String owner;
    private String location;
    private String inputformat;
    private String outputformat;
    private String columns;
    private String partitioned;
    private String partitionColumns;
    private String totalNumberFiles;
    private String totalFileSize;
    private String maxFileSize;
    private String minFileSize;
    private String lastAccessTime;
    private String lastUpdateTime;

    public TableDetailItem() {
    }

    public TableDetailItem(String tableName, String owner, String location, String inputformat,
                           String outputformat, String columns, String partitioned, String partitionColumns,
                           String totalNumberFiles, String totalFileSize, String maxFileSize,
                           String minFileSize, String lastAccessTime, String lastUpdateTime) {
        this.tableName = tableName;
        this.owner = owner;
        this.location = location;
        this.inputformat = inputformat;
        this.outputformat = outputformat;
        this.columns = columns;
        this.partitioned = partitioned;
        this.partitionColumns = partitionColumns;
        this.totalNumberFiles = totalNumberFiles;
        this.totalFileSize = totalFileSize;
        this.maxFileSize = maxFileSize;
        this.minFileSize = minFileSize;
        this.lastAccessTime = lastAccessTime;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInputformat() {
        return inputformat;
    }

    public void setInputformat(String inputformat) {
        this.inputformat = inputformat;
    }

    public String getOutputformat() {
        return outputformat;
    }

    public void setOutputformat(String outputformat) {
        this.outputformat = outputformat;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getPartitioned() {
        return partitioned;
    }

    public void setPartitioned(String partitioned) {
        this.partitioned = partitioned;
    }

    public String getPartitionColumns() {
        return partitionColumns;
    }

    public void setPartitionColumns(String partitionColumns) {
        this.partitionColumns = partitionColumns;
    }

    public String getTotalNumberFiles() {
        return totalNumberFiles;
    }

    public void setTotalNumberFiles(String totalNumberFiles) {
        this.totalNumberFiles = totalNumberFiles;
    }

    public String getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(String totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMinFileSize() {
        return minFileSize;
    }

    public void setMinFileSize(String minFileSize) {
        this.minFileSize = minFileSize;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
