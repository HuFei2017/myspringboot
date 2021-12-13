package com.learning.datasource.ddl;

import com.learning.schema.KeyMap;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class DatabaseHQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public DatabaseHQL createDatabase(String databaseName, String databaseComment){
        String sql = "create database if not exists ";
        sql += databaseName;
        if (!isNullOrEmpty(databaseComment))
            sql += " COMMENT '" + databaseComment + "'";
        stringbuilder.append(sql);
        return this;
    }

    /**
     * @param mode RESTRICT|CASCADE
     */
    public DatabaseHQL dropDatabase(String databaseName, String mode){
        String sql = "drop database if exists ";
        sql += databaseName;
        if (!isNullOrEmpty(mode))
            sql += " " + mode;
        stringbuilder.append(sql);
        return this;
    }

    public DatabaseHQL alterDatabaseProperties(String databaseName, List<KeyMap> property){
        StringBuilder sql = new StringBuilder("ALTER DATABASE " + databaseName + " SET DBPROPERTIES (");
        for(KeyMap s : property){
            sql.append("'").append(s.getName()).append("'='").append(s.getValue()).append("',");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        stringbuilder.append(sql);
        return this;
    }

    /**
     * @param ownerType USER|ROLE
     */
    public DatabaseHQL alterDatabaseOwner(String databaseName, String ownerType, String userName){
        String sql = "ALTER DATABASE " + databaseName + " SET OWNER ";
        if(!isNullOrEmpty(ownerType))
            sql += ownerType + " ";
        sql+=userName;
        stringbuilder.append(sql);
        return this;
    }

    public DatabaseHQL alterDatabaseLocation(String databaseName, String locationPath){
        String sql = "ALTER DATABASE " + databaseName + " SET LOCATION " + locationPath;
        stringbuilder.append(sql);
        return this;
    }
}
