package com.learning.schema;

public class DatabaseDescription {
    private DatabaseInfo databaseInfo;
    private String databaseCreation;

    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public String getDatabaseCreation() {
        return databaseCreation;
    }

    public DatabaseDescription(DatabaseInfo databaseInfo, String databaseCreation) {
        this.databaseInfo = databaseInfo;
        this.databaseCreation = databaseCreation;
    }

    public DatabaseDescription() {
    }

    public void setDatabaseCreation(String databaseCreation) {
        this.databaseCreation = databaseCreation;
    }
}
