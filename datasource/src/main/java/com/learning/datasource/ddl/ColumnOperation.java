package com.learning.datasource.ddl;

import com.learning.publics.DDLResult;
import com.learning.schema.ColumnInfo;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DDLOperation;

import java.sql.Connection;
import java.util.List;

public class ColumnOperation {

    public DDLOperation alterColumnChange(Connection connection, String databaseName, String tableName, List<KeyMap> partitionList,
                                          String oldName, String newName, String columnType, String comment,
                                          String isFirst, String columnName, String override){
        DDLResult result = new DDLResult();
        ColumnHQL operation = new ColumnHQL();
        return result.getResult(connection,
                databaseName,
                operation.addTableName(databaseName,tableName)
                        .addOnePartition(partitionList)
                        .addColumnChange(oldName,newName,columnType)
                        .addComment(comment)
                        .addOrder(isFirst,columnName)
                        .addRestrict(override).getStringbuilder().toString(),
                "change column successful!");
    }

    public DDLOperation alterColumnAdd(Connection connection, String databaseName, String tableName, List<KeyMap> partitionList,
                                       boolean isAdd, List<ColumnInfo> columnList, String override){
        DDLResult result = new DDLResult();
        ColumnHQL operation = new ColumnHQL();
        return result.getResult(connection,
                databaseName,
                operation.addTableName(databaseName,tableName)
                        .addOnePartition(partitionList)
                        .addColumnReplace(isAdd,columnList)
                        .addRestrict(override).getStringbuilder().toString(),
                (isAdd?"add":"replace")+" columns successful!");
    }

}
