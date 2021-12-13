package com.learning.datasource.ddl;

import com.learning.schema.KeyMap;
import com.learning.schema.PartitionItem;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class PartitionHQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public PartitionHQL alterPartitionAdd(String databaseName, String tableName, List<PartitionItem> partitionItems){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName + " ADD IF NOT EXISTS");
        for(PartitionItem s:partitionItems){
            sql.append("\n PARTITION (");
            for(KeyMap k:s.getSpec()){
                if(!isNullOrEmpty(k.getValue()))
                    sql.append(k.getName()).append("='").append(k.getValue()).append("',");
                else
                    sql.append(k.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
            if(!isNullOrEmpty(s.getLocation())){
                sql.append(" LOCATION '").append(s.getLocation()).append("'");
            }
            sql.append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        stringbuilder.append(sql);
        return this;
    }

    public PartitionHQL alterPartitionRename(String databaseName, String tableName, List<PartitionItem> oldItems, List<PartitionItem> newItems){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName);
        for(PartitionItem s:oldItems){
            sql.append("\n PARTITION (");
            for(KeyMap k:s.getSpec()){
                if(!isNullOrEmpty(k.getValue()))
                    sql.append(k.getName()).append("='").append(k.getValue()).append("',");
                else
                    sql.append(k.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
            if(!isNullOrEmpty(s.getLocation())){
                sql.append(" LOCATION '").append(s.getLocation()).append("'");
            }
            sql.append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        sql.append(" RENAME TO");
        for(PartitionItem s:newItems){
            sql.append("\n PARTITION (");
            for(KeyMap k:s.getSpec()){
                if(!isNullOrEmpty(k.getValue()))
                    sql.append(k.getName()).append("='").append(k.getValue()).append("',");
                else
                    sql.append(k.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
            if(!isNullOrEmpty(s.getLocation())){
                sql.append(" LOCATION '").append(s.getLocation()).append("'");
            }
            sql.append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        stringbuilder.append(sql);
        return this;
    }

    public PartitionHQL alterPartitionExchange(String newDatabaseName, String newTableName, String oldDatabaseName, String oldTableName, List<KeyMap> partitionItems){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + newDatabaseName + "." + newTableName + " EXCHANGE PARTITION (");
        for(KeyMap k:partitionItems){
            if(!isNullOrEmpty(k.getValue()))
                sql.append(k.getName()).append("='").append(k.getValue()).append("',");
            else
                sql.append(k.getName()).append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        sql.append(" WITH TABLE ").append(oldDatabaseName).append(".").append(oldTableName);
        stringbuilder.append(sql);
        return this;
    }

    public PartitionHQL alterPartitionRecover(String databaseName, String tableName, boolean isNewVersion){
        String sql = "";
        if(isNewVersion)
            sql += "MSCK REPAIR TABLE " + databaseName+"."+tableName;
        else
            sql += "ALTER TABLE "+databaseName+"."+tableName+" RECOVER PARTITIONS";
        stringbuilder.append(sql);
        return this;
    }

    public PartitionHQL alterPartitionDrop(String databaseName, String tableName, List<PartitionItem> partitionItems, boolean isMoveToTrash){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName + " DROP IF EXISTS ");
        for(PartitionItem s:partitionItems){
            sql.append("PARTITION (");
            for(KeyMap k:s.getSpec()){
                if(!isNullOrEmpty(k.getValue()))
                    sql.append(k.getName()).append("='").append(k.getValue()).append("',");
                else
                    sql.append(k.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
            if(!isNullOrEmpty(s.getLocation())){
                sql.append(" LOCATION '").append(s.getLocation()).append("'");
            }
            sql.append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        if(!isMoveToTrash)
            sql.append(" PURGE");
        stringbuilder.append(sql);
        return this;
    }

}
