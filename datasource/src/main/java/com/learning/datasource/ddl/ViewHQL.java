package com.learning.datasource.ddl;

import com.learning.schema.ColumnInfo;
import com.learning.schema.KeyMap;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class ViewHQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public ViewHQL addViewName(String databaseName, String viewName){
        String sql = "CREATE VIEW IF NOT EXISTS "+ databaseName+"."+viewName;
        stringbuilder.append(sql);
        return this;
    }

    public ViewHQL addColumns(List<ColumnInfo> columnList){
        StringBuilder sql = new StringBuilder();
        if(columnList.size()>0) {
            sql.append(" (");
            for (ColumnInfo info : columnList) {
                sql.append(info.getColumnName());
                if (!isNullOrEmpty(info.getComment()))
                    sql.append(" COMMENT '").append(info.getComment()).append("'");
                sql.append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        }
        stringbuilder.append(sql);
        return this;
    }

    public ViewHQL addComment(String comment){
        String sql = "";
        if(!isNullOrEmpty(comment)){
            sql += " COMMENT '" + comment+"'";
        }
        stringbuilder.append(sql);
        return this;
    }

    public ViewHQL addSelect(String hql){
        String sql = " AS "+hql;
        stringbuilder.append(sql);
        return this;
    }

    public ViewHQL dropView(String databaseName, String viewName){
        String sql = "DROP VIEW IF EXISTS "+ databaseName+"."+viewName;
        stringbuilder.append(sql);
        return this;
    }

    public ViewHQL alterViewName(String databaseName, String viewName){
        String sql = "ALTER VIEW "+ databaseName+"."+viewName;
        stringbuilder.append(sql);
        return this;
    }

    public ViewHQL alterViewProperties( List<KeyMap> properties){
        StringBuilder sql = new StringBuilder(" SET TBLPROPERTIES (");
        for (KeyMap s :properties){
            sql.append("'").append(s.getName()).append("'='").append(s.getValue()).append("',");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        stringbuilder.append(sql);
        return this;
    }

}
