package com.learning.datasource.ddl;

import com.learning.schema.KeyMap;

import java.util.List;

public class DatabaseCQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public DatabaseCQL addCreate(String databaseName){
        String cql = "CREATE KEYSPACE IF NOT EXISTS " + databaseName;
        stringbuilder.append(cql);
        return this;
    }

    /**
     * options described as:
     * name             kind	    mandatory	default	    description
     * replication	    map	        yes	        	        The replication strategy and options to use for the keyspace (see details below).
     * durable_writes	simple	    no	        true	    Whether to use the commit log for updates on this keyspace (disable this option at your own risk!).
     */
    public DatabaseCQL addOptions(List<KeyMap> options){
        StringBuilder cql = new StringBuilder();
        if(options.size()>0) {
            cql.append("\n WITH ");
            for (KeyMap k : options){
                cql.append(k.getName()).append("=").append(k.getValue()).append("\n AND ");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 5));
        }
        stringbuilder.append(cql);
        return this;
    }

    public DatabaseCQL addUse(String databaseName){
        String cql = "USE " + databaseName;
        stringbuilder.append(cql);
        return this;
    }

    public DatabaseCQL addAlter(String databaseName){
        String cql = "ALTER KEYSPACE " + databaseName;
        stringbuilder.append(cql);
        return this;
    }

    public DatabaseCQL addDrop(String databaseName){
        String cql = "DROP KEYSPACE IF EXISTS " + databaseName;
        stringbuilder.append(cql);
        return this;
    }

}
