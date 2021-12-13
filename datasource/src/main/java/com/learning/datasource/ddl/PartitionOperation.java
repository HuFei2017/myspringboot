package com.learning.datasource.ddl;

import com.learning.publics.DDLResult;
import com.learning.schema.KeyMap;
import com.learning.schema.PartitionItem;
import com.learning.schema.output.DDLOperation;

import java.sql.Connection;
import java.util.List;

public class PartitionOperation {

    public DDLOperation alterPartitionAdd(Connection connection, String databaseName, String tableName, List<PartitionItem> partitionItems){
        DDLResult result = new DDLResult();
        PartitionHQL operation = new PartitionHQL();
        return result.getResult(connection,databaseName,operation.alterPartitionAdd(databaseName, tableName, partitionItems).getStringbuilder().toString(),"alter table "+tableName+" add partitions successful!");
    }

    public DDLOperation alterPartitionRename(Connection connection, String databaseName, String tableName, List<PartitionItem> oldItems, List<PartitionItem> newItems){
        DDLResult result = new DDLResult();
        PartitionHQL operation = new PartitionHQL();
        return result.getResult(connection,databaseName,operation.alterPartitionRename(databaseName, tableName, oldItems,newItems).getStringbuilder().toString(),"alter table "+tableName+" rename partition successful!");
    }

    public DDLOperation alterPartitionExchange(Connection connection, String newDatabaseName, String newTableName, String oldDatabaseName, String oldTableName, List<KeyMap> partitionItems){
        DDLResult result = new DDLResult();
        PartitionHQL operation = new PartitionHQL();
        return result.getResult(connection,newDatabaseName,operation.alterPartitionExchange(newDatabaseName, newTableName, oldDatabaseName,oldTableName,partitionItems).getStringbuilder().toString(),"partitions exchanged successful!");
    }

    public DDLOperation alterPartitionRecover(Connection connection, String databaseName, String tableName, boolean isNewVersion){
        DDLResult result = new DDLResult();
        PartitionHQL operation = new PartitionHQL();
        return result.getResult(connection,databaseName,operation.alterPartitionRecover(databaseName, tableName, isNewVersion).getStringbuilder().toString(),"recover all partitions successful!");
    }

    public DDLOperation alterPartitionDrop(Connection connection, String databaseName, String tableName, List<PartitionItem> partitionItems, boolean isMoveToTrash){
        DDLResult result = new DDLResult();
        PartitionHQL operation = new PartitionHQL();
        return result.getResult(connection,databaseName,operation.alterPartitionDrop(databaseName, tableName,partitionItems, isMoveToTrash).getStringbuilder().toString(),"drop partitions successful!");
    }

}
