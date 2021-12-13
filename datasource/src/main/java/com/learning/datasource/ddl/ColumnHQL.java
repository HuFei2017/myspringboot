package com.learning.datasource.ddl;

import com.learning.schema.ColumnInfo;
import com.learning.schema.KeyMap;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class ColumnHQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public ColumnHQL addTableName(String databaseName, String tableName){
        String sql = "ALTER TABLE "+ databaseName+"."+tableName;
        stringbuilder.append(sql);
        return this;
    }

    public ColumnHQL addOnePartition(List<KeyMap> partitionList){
        StringBuilder sql = new StringBuilder();
        if(partitionList.size()>0) {
            sql.append(" PARTITION (");
            for (KeyMap k : partitionList) {
                if (!isNullOrEmpty(k.getValue()))
                    sql.append(k.getName()).append("='").append(k.getValue()).append("',");
                else
                    sql.append(k.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        }
        stringbuilder.append(sql);
        return this;
    }

    public ColumnHQL addColumnChange(String oldName, String newName, String columnType){
        String sql = " CHANGE COLUMN "+oldName+" "+newName+" "+columnType;
        stringbuilder.append(sql);
        return this;
    }

    public ColumnHQL addComment(String comment){
        String sql = "";
        if(!isNullOrEmpty(comment)){
            sql += " COMMENT '" + comment+"'";
        }
        stringbuilder.append(sql);
        return this;
    }

    public ColumnHQL addOrder(String isFirst, String columnName){
        String sql = "";
        if(!isNullOrEmpty(isFirst)){
            if(isFirst.toUpperCase().equals("FIRST"))
                sql+=" FIRST";
            else
                sql += " AFTER " + columnName;
        }
        stringbuilder.append(sql);
        return this;
    }

    /**
     * @param override CASCADE|RESTRICT
     */
    public ColumnHQL addRestrict(String override){
        String sql = "";
        if(!isNullOrEmpty(override)){
            sql += " " + override;
        }
        stringbuilder.append(sql);
        return this;
    }

    public ColumnHQL addColumnReplace(boolean isAdd, List<ColumnInfo> columnList){
        StringBuilder sql = new StringBuilder();
        if(isAdd)
            sql.append(" ADD");
        else
            sql.append(" REPLACE");
        sql.append(" COLUMNS (");
        for(ColumnInfo info : columnList){
            sql.append(info.getColumnName()).append(" ").append(info.getColumnType());
            if(!isNullOrEmpty(info.getComment()))
                sql.append(" COMMENT '").append(info.getComment()).append("'");
            sql.append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        stringbuilder.append(sql);
        return this;
    }

}
