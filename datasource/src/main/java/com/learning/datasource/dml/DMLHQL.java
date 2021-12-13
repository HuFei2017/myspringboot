package com.learning.datasource.dml;

import com.learning.schema.KeyMap;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class DMLHQL {
    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public DMLHQL createSelect() {
        String sql = "SELECT ";
        stringbuilder.append(sql);
        return this;
    }

    /**
     * @param distinct ALL | DISTINCT
     */
    public DMLHQL addDistinct(String distinct) {
        String sql = "";
        if (!isNullOrEmpty(distinct))
            sql += distinct + " ";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addSelectAll() {
        String sql = " * ";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addSelect(List<String> selectList) {
        StringBuilder sql = new StringBuilder();
        for (String s : selectList) {
            sql.append(s).append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addFrom(List<String> fromList) {
        StringBuilder sql = new StringBuilder("FROM ");
        for (String s : fromList) {
            sql.append(s).append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addWhere(String where) {
        String sql = "";
        if (!isNullOrEmpty(where))
            sql += "WHERE " + where + " ";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addGroup(List<String> groupList, String having) {
        StringBuilder sql = new StringBuilder();
        if (groupList.size() > 0) {
            sql.append("GROUP BY ");
            for (String s : groupList) {
                sql.append(s).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
            if (!isNullOrEmpty(having))
                sql.append("HAVING ").append(having).append(" ");
        }
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addOrder(List<KeyMap> orderList) {
        StringBuilder sql = new StringBuilder();
        if (orderList.size() > 0) {
            sql.append("ORDER BY ");
            for (KeyMap s : orderList) {
                if (!isNullOrEmpty(s.getValue()))
                    sql.append(s.getName()).append(" ").append(s.getValue()).append(",");
                else
                    sql.append(s.getName()).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
        }
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addCluster(List<String> clusterList){
        StringBuilder sql = new StringBuilder();
        if (clusterList.size() > 0) {
            sql.append("CLUSTER BY ");
            for (String s : clusterList) {
                sql.append(s).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
        }
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addSort(List<String> distributeList, List<KeyMap> sortList){
        StringBuilder sql = new StringBuilder();
        if(distributeList.size()>0){
            sql.append("DISTRIBUTE BY ");
            for (String s : distributeList) {
                sql.append(s).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
            if (sortList.size() > 0) {
                sql.append("SORT BY ");
                for (KeyMap s : sortList) {
                    if (!isNullOrEmpty(s.getValue()))
                        sql.append(s.getName()).append(" ").append(s.getValue()).append(",");
                    else
                        sql.append(s.getName()).append(",");
                }
                sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
            }
        }
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addLimit(int limit){
        String sql = "";
        if(limit>=0)
            sql += "LIMIT "+limit;
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL loadFile(boolean isLocal, String path, boolean isOverwrite, String databaseName, String tableName){
        String sql="LOAD DATA ";
        if(isLocal)
            sql += "LOCAL ";
        sql+="INPATH '" + path +"' ";
        if(isOverwrite)
            sql += "OVERWRITE ";
        sql += "INTO TABLE "+databaseName+"."+tableName;
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addOnePartition(List<KeyMap> partitionList){
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

    public DMLHQL writeFile(boolean isLocal, String directory){
        String sql = "INSERT OVERWRITE ";
        if(isLocal)
            sql += "LOCAL ";
        sql += "DIRECTORY '"+directory+"' ";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL rowFormat(String fieldChar, String escapeChar, String collectChar, String mapChar, String lineChar, String nullChar){
        String sql = "ROW FORMAT DELIMITED ";
        if(!isNullOrEmpty(fieldChar)) {
            sql += "FIELDS TERMINATED BY '" + fieldChar + "' ";
            if(!isNullOrEmpty(escapeChar))
                sql += "ESCAPED BY '"+escapeChar+"' ";
        }
        else if(!isNullOrEmpty(collectChar))
            sql += "COLLECTION ITEMS TERMINATED BY '"+collectChar+"' ";
        else if(!isNullOrEmpty(mapChar))
            sql += "MAP KEYS TERMINATED BY '"+mapChar+"' ";
        else if(!isNullOrEmpty(lineChar))
            sql += "LINES TERMINATED BY '"+lineChar+"' ";
        else if(!isNullOrEmpty(nullChar))
            sql += "NULL DEFINED AS '"+nullChar+"' ";
        else
            sql = "";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL fileFormat(String fileFormat){
        String sql = "";
        if(!isNullOrEmpty(fileFormat))
            sql += "STORED AS "+fileFormat+" ";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addSelect(String hql){
        String sql = " "+hql;
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL insertData(String databaseName, String tableName){
        String sql = "INSERT INTO TABLE "+databaseName+"."+tableName;
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addValues(List<List<String>> valueList){
        StringBuilder sql = new StringBuilder(" VALUES ");
        for(List<String> s :valueList){
            sql.append("(");
            for(String k : s){
                sql.append("'").append(k).append("',");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + "),");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL insertQueryData(boolean isOverwrite, String databaseName, String tableName){
        String sql = "";
        if(isOverwrite)
            sql += "INSERT OVERWRITE TABLE "+databaseName+"."+tableName;
        else
            sql += "INSERT INTO TABLE "+databaseName+"."+tableName;
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addSelect(boolean existion, String hql){
        String sql = " ";
        if(existion)
            sql += "IF NOT EXISTS ";
        sql += hql;
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addUpdate(String databaseName, String tableName, List<KeyMap> valueList){
        StringBuilder sql = new StringBuilder("UPDATE " + databaseName + "." + tableName + " SET ");
        for(KeyMap s:valueList){
            sql.append(s.getName()).append("='").append(s.getValue()).append("',");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " ");
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addDelete(String databaseName, String tableName){
        String sql = "DELETE FROM "+databaseName+"."+tableName+" ";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addMerge(String targetDatabaseName, String targetTableName, boolean isTable,
                           String sourceDatabaseName, String sourceTableName, String sqlExpression, String judgeExpression){
        String sql = "MERGE INTO "+targetDatabaseName+"."+targetTableName+" AS T USING ";
        if(isTable)
            sql += sourceDatabaseName+"."+sourceTableName;
        else
            sql += sqlExpression;
        sql += " AS S \n"+"ON "+judgeExpression+" \n";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addUpdateMatch(String updateExpression, List<KeyMap> valueList){
        StringBuilder sql = new StringBuilder("WHEN MATCHED ");
        if(!isNullOrEmpty(updateExpression))
            sql.append("AND (").append(updateExpression).append(") ");
        sql.append("THEN UPDATE SET ");
        for(KeyMap s:valueList){
            sql.append(s.getName()).append("=").append(s.getValue()).append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + " \n");
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addDeleteMatch(String deleteExpression){
        String sql = "WHEN MATCHED ";
        if(!isNullOrEmpty(deleteExpression))
            sql += "AND ("+deleteExpression+") ";
        sql += "THEN DELETE \n";
        stringbuilder.append(sql);
        return this;
    }

    public DMLHQL addInsertMatch(String insertExpression, List<String> valueList){
        StringBuilder sql = new StringBuilder("WHEN NOT MATCHED ");
        if(!isNullOrEmpty(insertExpression))
            sql.append("AND (").append(insertExpression).append(") ");
        sql.append("THEN INSERT VALUES ");
        sql.append("(");
        for(String s : valueList){
            sql.append(s).append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        stringbuilder.append(sql);
        return this;
    }

}
