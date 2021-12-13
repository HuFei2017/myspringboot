package com.learning.datasource.ddl;

import com.learning.publics.DDLResult;
import com.learning.schema.DatabaseInfo;
import com.learning.schema.KeyMap;
import com.learning.schema.TreeItem;
import com.learning.schema.output.DDLOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class DatabaseOperation {

    public DDLOperation createDatabase(Connection connection, String databaseName, String databaseComment) {
        DDLOperation result = new DDLOperation();
        DatabaseHQL operation = new DatabaseHQL();
        try (Statement smt = connection.createStatement()) {
            smt.execute(operation.createDatabase(databaseName, databaseComment).getStringbuilder().toString());
            result = result.getResult("OK", "create database " + databaseName + " successful!");
        } catch (SQLException e) {
            result = result.getResult("Failed", e.getMessage());
        }
        return result;
    }

    /**
     * @param mode  RESTRICT/CASCADE
     */
    public DDLOperation dropDatabase(Connection connection, String databaseName, String mode) {
        DDLResult result = new DDLResult();
        DatabaseHQL operation = new DatabaseHQL();
        return result.getResult(connection, databaseName,
                operation.dropDatabase(databaseName, mode).getStringbuilder().toString(),
                "drop database " + databaseName + " successful!");
    }

    public DDLOperation alterDatabaseProperty(Connection connection, String databaseName, List<KeyMap> property) {
        DDLResult result = new DDLResult();
        DatabaseHQL operation = new DatabaseHQL();
        return result.getResult(connection, databaseName,
                operation.alterDatabaseProperties(databaseName, property).getStringbuilder().toString(),
                "alter database " + databaseName + " properties successful!");
    }

    public DDLOperation alterDatabaseUser(Connection connection, String databaseName, String ownerType, String userName) {
        DDLResult result = new DDLResult();
        DatabaseHQL operation = new DatabaseHQL();
        return result.getResult(connection, databaseName,
                operation.alterDatabaseOwner(databaseName, ownerType, userName).getStringbuilder().toString(),
                "alter database " + databaseName + " owner successful!");
    }

    public DDLOperation alterDatabaseLocation(Connection connection, String databaseName, String locationPath) {
        DDLResult result = new DDLResult();
        DatabaseHQL operation = new DatabaseHQL();
        return result.getResult(connection, databaseName,
                operation.alterDatabaseLocation(databaseName, locationPath).getStringbuilder().toString(),
                "alter database " + databaseName + " location successful!");
    }

    public DatabaseInfo getDatabaseDetails(Connection connection, String databaseName) {
        DatabaseInfo result = new DatabaseInfo();
        try ( Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery("DESCRIBE DATABASE EXTENDED " + databaseName);
            while (rs.next()) {
                result.setName(rs.getString("db_name"));
                result.setComment(rs.getString("comment"));
                result.setLocation(rs.getString("location"));
                result.setOwnerName(rs.getString("owner_name"));
                result.setOwnerType(rs.getString("owner_type"));
                result.setParameters(rs.getString("parameters"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String getDatabaseCreation(Connection connection, String databaseName){
        String result = "";
        try ( Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery("DESCRIBE DATABASE EXTENDED " + databaseName);
            while (rs.next()) {
                result += "CREATE DATABASE IF NOT EXISTS '"+databaseName+"'\n";
                if(!isNullOrEmpty(rs.getString("comment"))){
                    result += " COMMENT '"+rs.getString("comment")+"'\n";
                }
                result += " LOCATION '"+rs.getString("location")+"';\n";
                break;
            }
        } catch (SQLException e) {
            result="Data Not Found!Please check server or net connection!";
        }
        return result;
    }

    public List<TreeItem> getDatabases(Connection connection, String expression, String isAll){
        ArrayList<TreeItem> result = new ArrayList<>();
        try ( Statement smt = connection.createStatement()) {
            String sql = "SHOW databases LIKE '*" + expression + "*'";
            if(!Objects.equals(isAll, "LIKE"))
                sql = sql.replace("*","");
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                String databaseName = rs.getString("database_name");
                TreeItem database = new TreeItem();
                database.setId(databaseName);
                database.setName(databaseName);
                database.setType("database");
                result.add(database);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<String> getFunctions(Connection connection){
        ArrayList<String> result = new ArrayList<>();
        try ( Statement smt = connection.createStatement()) {
            ResultSet rs = smt.executeQuery("SHOW FUNCTIONS");
            while (rs.next()) {
                result.add(rs.getString("tab_name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
