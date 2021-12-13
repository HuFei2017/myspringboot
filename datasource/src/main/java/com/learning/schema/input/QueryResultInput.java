package com.learning.schema.input;

public class QueryResultInput {
    private String databaseName = "default";
    private String hql;
    private int pageIndex;
    private int pageSize;
    private String userName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        if (databaseName != null && !databaseName.equals(""))
            this.databaseName = databaseName;
    }

    public QueryResultInput() {
    }

    public QueryResultInput(String databaseName, String hql, int pageIndex, int pageSize, String userName) {
        this.databaseName = databaseName;
        this.hql = hql;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.userName = userName;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getHql() {
        return hql;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }
}
