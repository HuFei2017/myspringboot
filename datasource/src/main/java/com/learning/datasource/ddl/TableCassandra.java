package com.learning.datasource.ddl;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.learning.publics.DDLResult;
import com.learning.schema.CassColumnInfo;
import com.learning.schema.CassTableInfo;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DDLOperation;

import java.util.ArrayList;
import java.util.List;

public class TableCassandra {

    public DDLOperation createTable(Session session, String databaseName, String tableName,
                                    List<CassColumnInfo> columnList, List<List<String>> primaryList,
                                    boolean isStorage, List<KeyMap> orderList, List<KeyMap> optionList){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addCreate(databaseName,tableName)
                        .addColumn(columnList)
                        .addPrimary(primaryList)
                        .addRightBracket()
                        .addOptions(isStorage,orderList,optionList).getStringbuilder().toString(),
                "create table " + tableName + " successful!");
    }

    public DDLOperation alterTableADD(Session session, String databaseName, String tableName, List<KeyMap> columnList){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addAlter(databaseName,tableName)
                        .addAlterADD(columnList)
                        .getStringbuilder().toString(),
                "alter table " + tableName + " successful!");
    }

    public DDLOperation alterTableDROP(Session session, String databaseName, String tableName, List<String> columnList){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addAlter(databaseName,tableName)
                        .addAlterDROP(columnList)
                        .getStringbuilder().toString(),
                "alter table " + tableName + " successful!");
    }

    public DDLOperation alterTablePro(Session session, String databaseName, String tableName, List<KeyMap> optionList){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addAlter(databaseName,tableName)
                        .addOptions(false,new ArrayList<>(),optionList)
                        .getStringbuilder().toString(),
                "alter table " + tableName + " successful!");
    }

    public DDLOperation dropTable(Session session, String databaseName, String tableName){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addDrop(databaseName,tableName)
                        .getStringbuilder().toString(),
                "drop table " + tableName + " successful!");
    }

    public DDLOperation truncateTable(Session session, String databaseName, String tableName){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addTruncate(databaseName,tableName)
                        .getStringbuilder().toString(),
                "truncate table " + tableName + " successful!");
    }

    public List<CassTableInfo> descTables(Session session, String databaseName){
        List<CassTableInfo> result = new ArrayList<>();
        List<String> tables = new ArrayList<>();
        try {
            session.execute("USE system_schema");
            ResultSet rs = session.execute("select * from tables where keyspace_name='"+databaseName+"'");
            for(Row row:rs){
                tables.add(row.getObject("table_name").toString());
            }
            for(String tableName : tables){
                CassTableInfo ct = new CassTableInfo();
                ct.setTableName(tableName);
                List<KeyMap> regular = new ArrayList<>();//普通字段
                List<KeyMap> parttion = new ArrayList<>();//分区字段
                List<KeyMap> par_tmp = new ArrayList<>();//分区字段备份，用于排序
                List<KeyMap> clustering = new ArrayList<>();//分桶字段
                List<KeyMap> clus_tmp = new ArrayList<>();//分桶字段，用于排序
                List<KeyMap> column = new ArrayList<>();//所有列
                Row rs_pro = session.execute("select * from tables where keyspace_name='"+databaseName+
                        "' AND table_name='"+tableName+"'").one();
                ResultSet rs_column = session.execute("select * from columns where keyspace_name='"+databaseName+
                        "' AND table_name='"+tableName+"'");
                for(Row row : rs_column){
                    switch (row.getObject("kind").toString()) {
                        case "regular": {
                            KeyMap km = new KeyMap();
                            km.setName(row.getObject("column_name").toString());
                            km.setValue(row.getObject("type").toString());
                            regular.add(km);
                            break;
                        }
                        case "partition_key": {
                            KeyMap km = new KeyMap();
                            km.setName(row.getObject("column_name").toString());
                            km.setValue(row.getObject("type").toString()+"."+row.getObject("position").toString());
                            parttion.add(km);
                            par_tmp.add(km);
                            break;
                        }
                        case "clustering": {
                            KeyMap km = new KeyMap();
                            km.setName(row.getObject("column_name").toString()+"."+row.getObject("clustering_order").toString());
                            km.setValue(row.getObject("type").toString()+"."+row.getObject("position").toString());
                            clustering.add(km);
                            clus_tmp.add(km);
                            break;
                        }
                        default:break;
                    }
                }
                //排序
                for(KeyMap k : par_tmp){
                    parttion.set(Integer.parseInt(k.getValue().substring(k.getValue().indexOf(".")+1)),new KeyMap(k.getName(),k.getValue().substring(0,k.getValue().indexOf("."))));
                }
                for(KeyMap k : clus_tmp){
                    clustering.set(Integer.parseInt(k.getValue().substring(k.getValue().indexOf(".")+1)),new KeyMap(k.getName(),k.getValue().substring(0,k.getValue().indexOf("."))));
                }
                //填充列：列名&字段类型
                if(parttion.size()>0){
                    for(KeyMap tmp : parttion){
                        column.add(new KeyMap(tmp.getName(),tmp.getValue()));
                    }
                }
                if(clustering.size()>0){
                    for(KeyMap tmp : clustering){
                        column.add(new KeyMap(tmp.getName().substring(0,tmp.getName().indexOf(".")),tmp.getValue()));
                    }
                }
                if(regular.size()>0){
                    for(KeyMap tmp : regular){
                        column.add(new KeyMap(tmp.getName(),tmp.getValue()));
                    }
                }
                ct.setColumns(column);
                //拼建表语句
                StringBuilder cql = new StringBuilder("CREATE TABLE " + databaseName + "." + tableName + "(");
                for(KeyMap cl : column){
                    cql.append("\n").append(cl.getName()).append(" ").append(cl.getValue()).append(",");
                }
                cql = new StringBuilder(cql.substring(0, cql.length() - 1));
                if(parttion.size()>0||clustering.size()>0) {
                    cql.append(",\nPRIMARY KEY (");
                    if(parttion.size()>0){
                        if(parttion.size() == 1)
                            cql.append(parttion.get(0).getName()).append(",");
                        else {
                            cql.append("(");
                            for(KeyMap km : parttion){
                                cql.append(km.getName()).append(",");
                            }
                            cql = new StringBuilder(cql.substring(0, cql.length() - 1) + "),");
                        }
                    }
                    if(clustering.size()>0){
                        for(KeyMap km : clustering){
                            cql.append(km.getName().substring(0, km.getName().indexOf("."))).append(",");
                        }
                    }
                    cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")");
                }
                cql.append("\n) WITH");
                if(clustering.size()>0){
                    cql.append(" CLUSTERING ORDER BY (");
                    for(KeyMap km : clustering){
                        cql.append(km.getName().substring(0, km.getName().indexOf("."))).append(" ").append(km.getName().substring(km.getName().indexOf(".") + 1).toUpperCase()).append(",");
                    }
                    cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")\nAND");
                }
                cql.append(" bloom_filter_fp_chance = ").append(rs_pro.getObject("bloom_filter_fp_chance").toString());
                cql.append("\nAND caching = ").append(rs_pro.getObject("caching").toString());
                cql.append("\nAND comment = '").append(rs_pro.getObject("comment").toString()).append("'");
                cql.append("\nAND compaction = ").append(rs_pro.getObject("compaction").toString());
                cql.append("\nAND compression = ").append(rs_pro.getObject("compression").toString());
                cql.append("\nAND crc_check_chance = ").append(rs_pro.getObject("crc_check_chance").toString());
                cql.append("\nAND dclocal_read_repair_chance = ").append(rs_pro.getObject("dclocal_read_repair_chance").toString());
                cql.append("\nAND default_time_to_live = ").append(rs_pro.getObject("default_time_to_live").toString());
                cql.append("\nAND gc_grace_seconds = ").append(rs_pro.getObject("gc_grace_seconds").toString());
                cql.append("\nAND max_index_interval = ").append(rs_pro.getObject("max_index_interval").toString());
                cql.append("\nAND memtable_flush_period_in_ms = ").append(rs_pro.getObject("memtable_flush_period_in_ms").toString());
                cql.append("\nAND min_index_interval = ").append(rs_pro.getObject("min_index_interval").toString());
                cql.append("\nAND read_repair_chance = ").append(rs_pro.getObject("read_repair_chance").toString());
                cql.append("\nAND speculative_retry = '").append(rs_pro.getObject("speculative_retry").toString()).append("';");
                ct.setCreateCQL(cql.toString());
                result.add(ct);
            }
        } finally {
            if(session != null)
                session.close();
        }
        return result;
    }

    public DDLOperation createIndex(Session session, String indexName, String databaseName, String tableName, boolean isCustom,
                                    String identifier, String columnName, String className, String option){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addCreateIndex(isCustom,indexName,databaseName,tableName,identifier,columnName)
                        .addIndexOptions(className,option)
                        .getStringbuilder().toString(),
                "create index "+indexName+" on table " + tableName + " successful!");
    }

    public DDLOperation dropIndex(Session session, String indexName, String databaseName){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addDropIndex(indexName)
                        .getStringbuilder().toString(),
                "drop index "+indexName+" successful!");
    }

    public DDLOperation createTrigger(Session session, String triggerName, String databaseName, String tableName, String className){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.addTrigger(triggerName,databaseName,tableName)
                        .addIndexOptions(className,"")
                        .getStringbuilder().toString(),
                "create trigger "+triggerName+" on table "+tableName+" successful!");
    }

    public DDLOperation dropTrigger(Session session, String triggerName, String databaseName, String tableName){
        DDLResult result = new DDLResult();
        TableCQL operation = new TableCQL();
        return result.getResult(session,databaseName,
                operation.dropTrigger(triggerName,databaseName,tableName)
                        .getStringbuilder().toString(),
                "drop trigger "+triggerName+" on table "+tableName+" successful!");
    }

}
