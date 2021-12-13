package com.learning.datasource.ddl;

import com.learning.schema.KeyMap;

import java.util.List;

public class ViewCQL {

    private StringBuilder stringbuilder = new StringBuilder();
    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public ViewCQL addCreate(String viewName, String statement){
        String cql = "CREATE MATERIALIZED VIEW IF NOT EXISTS "+viewName+" AS\n "+statement;
        stringbuilder.append(cql);
        return this;
    }

    public ViewCQL addPrimary(List<String> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            cql.append("\n PRIMARY KEY (");
            for(String str : list){
                cql.append(str).append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")");
        }
        stringbuilder.append(cql);
        return this;
    }

    public ViewCQL addOptions(List<KeyMap> list){
        StringBuilder cql = new StringBuilder();
        if (list.size() > 0) {
            cql.append("\n WITH");
            for (KeyMap option : list) {
                cql.append(" ").append(option.getName()).append("=").append(option.getValue()).append("\n AND");
            }
            cql = new StringBuilder(new StringBuilder(cql.substring(0, cql.length() - 4)));
        }
        stringbuilder.append(cql);
        return this;
    }

    public ViewCQL addAlter(String viewName){
        String cql = "ALTER MATERIALIZED VIEW "+viewName;
        stringbuilder.append(cql);
        return this;
    }

    public ViewCQL addDrop(String viewName){
        String cql = "DROP MATERIALIZED VIEW IF EXISTS "+viewName;
        stringbuilder.append(cql);
        return this;
    }

}
