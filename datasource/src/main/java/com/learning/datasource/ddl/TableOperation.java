package com.learning.datasource.ddl;

import com.learning.publics.DDLResult;
import com.learning.schema.*;
import com.learning.schema.input.TableCreateInput;
import com.learning.schema.output.DDLOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class TableOperation {

    public List<ColumnInfo> getTableDetails(Connection connection, String databaseName, String tableName, boolean isFormatted, List<KeyMap> partitionList) {
        TableHQL operation = new TableHQL();
        List<ColumnInfo> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery("DESCRIBE "+(isFormatted?"FORMATTED":"EXTENDED")+" " + databaseName + "." + tableName+
            operation.addOnePartition(partitionList).getStringbuilder().toString());
            while (rs.next()) {
                ColumnInfo info = new ColumnInfo();
                info.setColumnName(rs.getString("col_name"));
                info.setColumnType(rs.getString("data_type"));
                info.setComment(rs.getString("comment"));
                result.add(info);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String getTableCreation(Connection connection, String databaseName, String tableName, List<ColumnInfo> list) {
        StringBuilder result = new StringBuilder();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery("SHOW CREATE TABLE " + databaseName + "." + tableName);
            Map map = new java.util.HashMap();
            for(ColumnInfo info : list){
                if(!isNullOrEmpty(info.getColumnName()) && !info.getColumnName().contains("#") && !info.getColumnName().contains(":"))
                    map.put(info.getColumnName(),info.getComment());
            }
            while (rs.next()) {
                String value = rs.getString("createtab_stmt");
                if(!rs.getString("createtab_stmt").contains("�"))
                    result.append(value).append("\n");
                else{
                    String name = value.substring(value.indexOf("`")+1,value.indexOf("`",3));
                    result.append(value.substring(0,value.indexOf("�"))).append(map.get(name)).append("'").append("\n");
                }
            }
        } catch (SQLException e) {
            result = new StringBuilder("Data Not Found!Please check server or net connection!");
        }
        return result.toString();
    }

    /**
     * TEXTFILE格式以'\t'作为分隔符
     */
    public DDLOperation createTable(Connection connection, TableCreateInput input) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection,
                input.getDatabaseName(),
                operation.createTableName(input.getDatabaseName(), input.getTableType(), input.getTableName())
                        .createColumn(input.getColumnList())
                        .createComment(input.getComment())
                        .createPartition(input.getColumnPartition())
                        .createCluster( input.getColumnSort(), input.getBucketNumber())
                        .createSkew(input.getSkewName(), input.getSkewValue(), isNullOrEmpty(input.getDirectories()))
                        .createStore(input.getFileFormat()).getStringbuilder().toString(),
                "create table " + input.getTableName() + " successful!");
    }

    public DDLOperation dropTable(Connection connection, String databaseName, String tableName, String Trashable) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.dropTable(databaseName, tableName, isNullOrEmpty(Trashable)).getStringbuilder().toString(),
                "drop table " + tableName + " successful!");
    }

    public DDLOperation truncateTable(Connection connection, String databaseName, String tableName, List<KeyMap> partitionList) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.truncateTable(databaseName, tableName, partitionList).getStringbuilder().toString(),
                "truncate table " + tableName + " successful!");
    }

    public DDLOperation renameTable(Connection connection, String databaseName, String tableName, String newName) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.renameTable(databaseName, tableName, newName).getStringbuilder().toString(),
                "rename table " + tableName + " name to " + newName + " successful!");
    }

    public DDLOperation alterTableProperties(Connection connection, String databaseName, String tableName, List<KeyMap> propertyList) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.alterTableProperties(databaseName, tableName, propertyList).getStringbuilder().toString(),
                "alter table " + databaseName + " properties successful!");
    }

    public DDLOperation alterTableComment(Connection connection, String databaseName, String tableName, String newComment) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.alterTableComment(databaseName, tableName, newComment).getStringbuilder().toString(),
                "alter table " + tableName + " change comment rename to \"" + newComment + "\" successful!");
    }

    public DDLOperation alterTableStorage(Connection connection, String databaseName, String tableName, List<String> columnList,
                                          List<ColumnSort> sortList, int bucketNumber) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.alterTableStorage(databaseName, tableName, columnList, sortList, bucketNumber).getStringbuilder().toString(),
                "alter table " + tableName + " storage properties successful!");
    }

    public DDLOperation alterTableSkew(Connection connection, String databaseName, String tableName, boolean isSkewed, boolean isStored,
                                       List<String> skewName, List<List<String>> skewValue) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.alterTableSkew(databaseName, tableName, isSkewed, isStored, skewName, skewValue).getStringbuilder().toString(),
                "alter table " + tableName + " successful!");
    }

    public DDLOperation alterTableSkewLocation(Connection connection, String databaseName, String tableName, List<KeyMap> location) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.alterTableSkewLocation(databaseName, tableName, location).getStringbuilder().toString(),
                "alter table " + tableName + " location successful!");
    }

    public DDLOperation alterFileFormat(Connection connection, String databaseName, String tableName, List<KeyMap> partitionItems, String fileFormat) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.alterFileFormat(databaseName, tableName, partitionItems, fileFormat).getStringbuilder().toString(),
                "change table " + tableName + " fileformat to " + fileFormat + " successful!");
    }

    public DDLOperation alterLocation(Connection connection, String databaseName, String tableName, List<KeyMap> partitionItems, String location) {
        DDLResult result = new DDLResult();
        TableHQL operation = new TableHQL();
        return result.getResult(connection, databaseName,
                operation.alterLocation(databaseName, tableName, partitionItems, location).getStringbuilder().toString(),
                "change table " + tableName + " location to '" + location + "' successful!");
    }

    public List<TreeItem> getTables(Connection connection, String databaseName, String expression){
        ArrayList<TreeItem> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery("SHOW TABLES IN " + databaseName + " '*" + expression + "*'");
            while (rs.next()) {
                String tableName = rs.getString("tab_name");
                TreeItem table = new TreeItem();
                table.setId(databaseName + "." + tableName);
                table.setName(tableName);
                table.setType("table");
                result.add(table);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<KeyMap> getPartitions(Connection connection, String databaseName, String tableName, List<KeyMap> partitionList){
        TableHQL operation = new TableHQL();
        ArrayList<KeyMap> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery(
                    operation.showPartition(databaseName,tableName)
                    .addOnePartition(partitionList)
                    .getStringbuilder().toString()
            );
            while (rs.next()) {
                KeyMap keymap = new KeyMap();
                keymap.setName("partition");
                keymap.setValue(rs.getString("partition"));
                result.add(keymap);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<TableDetailItem> getTableExtended(Connection connection, String databaseName, String like, List<KeyMap> partitionList){
        TableHQL operation = new TableHQL();
        ArrayList<TableDetailItem> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            String sql = "SHOW TABLE EXTENDED IN "+databaseName+" LIKE '*"+like+"*'"+
                    operation.addOnePartition(partitionList).getStringbuilder().toString();
            ResultSet rs = smt.executeQuery(sql);
            TableDetailItem item = new TableDetailItem();
            while (rs.next()) {
                String str = rs.getString("tab_name");
                if(str.equals("")) {
                    result.add(item);
                    item = new TableDetailItem();
                }
                else if(str.startsWith("tableName:"))
                    item.setTableName(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("owner:"))
                    item.setOwner(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("location:"))
                    item.setLocation(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("inputformat:"))
                    item.setInputformat(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("outputformat:"))
                    item.setOutputformat(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("columns:"))
                    item.setColumns(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("partitioned:"))
                    item.setPartitioned(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("partitionColumns:"))
                    item.setPartitionColumns(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("totalNumberFiles:"))
                    item.setTotalNumberFiles(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("totalFileSize:"))
                    item.setTotalFileSize(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("maxFileSize:"))
                    item.setMaxFileSize(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("minFileSize:"))
                    item.setMinFileSize(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("lastAccessTime:"))
                    item.setLastAccessTime(str.substring(str.indexOf(":")+1));
                else if(str.startsWith("lastUpdateTime:"))
                    item.setLastUpdateTime(str.substring(str.indexOf(":")+1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<KeyMap> getTableProperties(Connection connection, String databaseName, String tableName, String property){
        String sql = "SHOW TBLPROPERTIES "+databaseName+"."+tableName;
        if(!isNullOrEmpty(property)){
            sql += " ('"+property+"')";
        }
        ArrayList<KeyMap> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                KeyMap keymap = new KeyMap();
                keymap.setName(rs.getString("prpt_name"));
                keymap.setValue(rs.getString("prpt_value"));
                result.add(keymap);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<String> getTableColumnName(Connection connection, String databaseName, String tableName){
        String sql = "";
        if(isNullOrEmpty(databaseName)) {
            databaseName = "default";
            sql += "SHOW COLUMNS IN " + tableName;
        }
        else
            sql += "SHOW COLUMNS IN "+tableName +" IN "+databaseName;
        ArrayList<String> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getString("field"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
