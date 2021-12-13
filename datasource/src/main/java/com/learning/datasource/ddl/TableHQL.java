package com.learning.datasource.ddl;

import com.learning.schema.ColumnInfo;
import com.learning.schema.ColumnSort;
import com.learning.schema.KeyMap;

import java.util.List;
import java.util.Objects;

import static org.apache.parquet.Strings.isNullOrEmpty;


public class TableHQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public TableHQL createTableName(String databaseName, String tableType, String tableName){
        String sql = "CREATE ";
        if(!isNullOrEmpty(tableType))
            sql += tableType.toUpperCase() + " ";
        sql += "TABLE IF NOT EXISTS " + databaseName+"."+tableName;
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL createColumn(List<ColumnInfo> columnList){
        StringBuilder sql = new StringBuilder("(");
        for(ColumnInfo info : columnList){
            sql.append("\n").append(info.getColumnName()).append(" ");
            sql.append(info.getColumnType());
            if(!isNullOrEmpty(info.getComment()))
                sql.append(" COMMENT '").append(info.getComment()).append("'");
            sql.append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + "\n)\n");
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL createComment(String comment){
        String sql = "";
        if(!isNullOrEmpty(comment))
            sql += " COMMENT '" + comment + "'\n";
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL createPartition(List<ColumnInfo> columnPartition){
        String sql = "";
        if(columnPartition.size()>0) {
            sql += " PARTITIONED BY ";
            StringBuilder s = new StringBuilder("(");
            for (ColumnInfo info : columnPartition) {
                s.append(info.getColumnName()).append(" ");
                s.append(info.getColumnType());
                if (!isNullOrEmpty(info.getComment()))
                    s.append(" COMMENT '").append(info.getComment()).append("'");
                s.append(",");
            }
            sql += s.substring(0,s.length()-1) + ")\n";
        }
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL createCluster(List<ColumnSort> columnSort, int bucketNumber){
        String sql = "";
        if(columnSort.size()>0){
            sql += " CLUSTERED BY ";
            StringBuilder s= new StringBuilder("(");
            for(ColumnSort info : columnSort){
                s.append(info.getColumnName()).append(",");
            }
            sql += s.substring(0,s.length()-1) + ")";
            s = new StringBuilder(" SORTED BY (");
            for(ColumnSort info : columnSort){
                if(!Objects.equals(info.getSortType(), "-1")) {
                    s.append(info.getColumnName());
                    if (!isNullOrEmpty(info.getSortType())) {
                        s.append(" ").append(info.getSortType());
                    }
                    s.append(",");
                }
            }
            if(s.toString().endsWith(","))
                sql += s.substring(0,s.length()-1) + ")";
            else if(s.toString().endsWith("("))
                sql += "";
            else
                sql += s+")";
            sql += " INTO " + bucketNumber + " BUCKETS\n";
        }
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL createSkew(List<String> skewName, List<List<String>> skewValue, boolean directories){
        StringBuilder sql = new StringBuilder();
        if(skewName.size()>0){
            sql.append(" SKEWED BY (");
            for(String s:skewName){
                sql.append(s).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")\n ON (");
            for (List<String> s : skewValue){
                sql.append("(");
                for(String m: s){
                    sql.append(m).append(",");
                }
                sql = new StringBuilder(sql.substring(0, sql.length() - 1) + "),");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")\n");
            if(!directories)
                sql.append(" STORED AS DIRECTORIES\n");
        }
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL createStore(String fileFormat){
        String sql = "";
        if(isNullOrEmpty(fileFormat) || fileFormat.toLowerCase().equals("textfile")){
            sql += " ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\t' STORED AS TEXTFILE";
        }
        else
            sql += " STORED AS " + fileFormat;
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL dropTable(String databaseName, String tableName, boolean ifMoveToTrash){
        String sql = "DROP TABLE IF EXISTS ";
        sql += databaseName+"."+tableName;
        if(!ifMoveToTrash)
            sql += " PURGE";
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL truncateTable(String databaseName, String tableName, List<KeyMap> partitionList){
        StringBuilder sql = new StringBuilder("TRUNCATE TABLE ");
        sql.append(databaseName).append(".").append(tableName);
        if(partitionList.size()>0) {
            sql.append(" PARTITION (");
            for(KeyMap s : partitionList){
                sql.append("'").append(s.getName()).append("'='").append(s.getValue()).append("',");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        }
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL renameTable(String databaseName, String tableName, String newName){
        String sql = "ALTER TABLE " + databaseName+"."+tableName+" RENAME TO "+newName;
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL alterTableProperties(String databaseName, String tableName, List<KeyMap> propertyList){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName + " SET TBLPROPERTIES (");
        for(KeyMap s : propertyList){
            sql.append("'").append(s.getName()).append("'='").append(s.getValue()).append("',");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL alterTableComment(String databaseName, String tableName, String newComment){
        String sql = "ALTER TABLE "+databaseName+"."+tableName+" SET TBLPROPERTIES ('comment' = '"+newComment+"')";
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL alterTableStorage(String databaseName, String tableName, List<String> columnList, List<ColumnSort> sortList, int bucketNumber){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName + " CLUSTERED BY (");
        for(String s:columnList){
            sql.append(s).append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        if(sortList.size()>0){
            sql.append(" SORTED BY (");
            for(ColumnSort s : sortList){
                sql.append(s.getColumnName());
                if(!isNullOrEmpty(s.getSortType()))
                    sql.append(" ").append(s.getSortType());
                sql.append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        }
        sql.append("\n INTO ").append(bucketNumber).append(" BUCKETS");
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL alterTableSkew(String databaseName, String tableName, boolean isSkewed, boolean isStored, List<String> skewName, List<List<String>> skewValue){
        StringBuilder sql = new StringBuilder();
        if(!isSkewed){
            sql.append("ALTER TABLE ").append(databaseName).append(".").append(tableName).append(" NOT SKEWED");
        }
        else if(!isStored && skewName.size()<=0){
            sql.append("ALTER TABLE ").append(databaseName).append(".").append(tableName).append(" NOT STORED AS DIRECTORIES");
        }
        else{
            sql.append("ALTER TABLE ").append(databaseName).append(".").append(tableName).append(" SKEWED BY (");
            for(String s:skewName){
                sql.append(s).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")\n ON (");
            for (List<String> s : skewValue){
                sql.append("(");
                for(String m: s){
                    sql.append(m).append(",");
                }
                sql = new StringBuilder(sql.substring(0, sql.length() - 1) + "),");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
            if(isStored)
                sql.append("\n STORED AS DIRECTORIES");
        }
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL alterTableSkewLocation(String databaseName, String tableName, List<KeyMap> location){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName + " SET SKEWED LOCATION (");
        for (KeyMap s :location){
            sql.append("'").append(s.getName()).append("'='").append(s.getValue()).append("',");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL alterFileFormat(String databaseName, String tableName, List<KeyMap> partitionItems, String fileFormat){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName + " ");
        if(partitionItems.size()>0){
            sql.append("PARTITION (");
            for(KeyMap k:partitionItems){
                if(!isNullOrEmpty(k.getValue()))
                    sql.append(k.getName()).append("='").append(k.getValue()).append("',");
                else
                    sql.append(k.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ") ");
        }
        sql.append("SET FILEFORMAT ").append(fileFormat);
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL alterLocation(String databaseName, String tableName, List<KeyMap> partitionItems, String location){
        StringBuilder sql = new StringBuilder("ALTER TABLE " + databaseName + "." + tableName + " ");
        if(partitionItems.size()>0){
            sql.append("PARTITION (");
            for(KeyMap k:partitionItems){
                if(!isNullOrEmpty(k.getValue()))
                    sql.append(k.getName()).append("='").append(k.getValue()).append("',");
                else
                    sql.append(k.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ") ");
        }
        sql.append("SET LOCATION '").append(location).append("'");
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL showPartition(String databaseName, String tableName){
        String sql = "SHOW PARTITIONS "+databaseName+"."+tableName;
        stringbuilder.append(sql);
        return this;
    }

    public TableHQL addOnePartition(List<KeyMap> partitionList){
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

}
