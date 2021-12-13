package com.learning.datasource.dml;

import com.learning.publics.DMLResult;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DMLOperation;

import java.sql.Connection;
import java.util.List;

public class Manipulation {

    public DMLOperation getSelectResult(Connection connection, String databaseName, String distinct, List<String> selectList,
                                        List<String> fromList, String where, List<String> groupList, String having, List<KeyMap> orderList,
                                        List<String> clusterList, List<String> distributeList, List<KeyMap> sortList, int limit, int pageIndex, int pageSize) {
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, databaseName,
                operation.createSelect()
                        .addDistinct(distinct)
                        .addSelect(selectList)
                        .addFrom(fromList)
                        .addWhere(where)
                        .addGroup(groupList, having)
                        .addOrder(orderList)
                        .addCluster(clusterList)
                        .addSort(distributeList, sortList)
                        .addLimit(limit).getStringbuilder().toString(),
                "select datas has done!",
                pageIndex,pageSize,true);
    }

    public DMLOperation getAllSelectResult(Connection connection, String databaseName, String distinct, List<String> fromList,
                                           String where, List<String> groupList, String having, List<KeyMap> orderList,
                                           List<String> clusterList, List<String> distributeList, List<KeyMap> sortList, int limit, int pageIndex, int pageSize) {
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, databaseName,
                operation.createSelect()
                        .addDistinct(distinct)
                        .addSelectAll()
                        .addFrom(fromList)
                        .addWhere(where)
                        .addGroup(groupList, having)
                        .addOrder(orderList)
                        .addCluster(clusterList)
                        .addSort(distributeList, sortList)
                        .addLimit(limit).getStringbuilder().toString(),
                "select datas has done!",
                pageIndex,pageSize,true);
    }

    public DMLOperation executeQuery(Connection connection, String databaseName, String hql, int pageIndex, int pageSize, boolean isFill){
        DMLResult result = new DMLResult();
        return result.getResult(connection, databaseName,hql,"operation executed successful!",pageIndex,pageSize,isFill);
    }

    public DMLOperation loadFiles(Connection connection, boolean isLocal, String path, boolean isOverwrite, String databaseName, String tableName, List<KeyMap> partitionList){
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, databaseName,
                operation.loadFile(isLocal,path,isOverwrite,databaseName,tableName)
                        .addOnePartition(partitionList).getStringbuilder().toString(),
                "load file from '"+path+"' has done!",
                -1,-1,true);
    }

    public DMLOperation writeFiles(Connection connection, boolean isLocal, String directory,
                                   String fieldChar, String escapeChar, String collectChar, String mapChar, String lineChar, String nullChar,
                                   String fileFormat, String sql){
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, "default",
                operation.writeFile(isLocal,directory)
                        .rowFormat(fieldChar,escapeChar,collectChar,mapChar,lineChar,nullChar)
                        .fileFormat(fileFormat)
                        .addSelect(sql).getStringbuilder().toString(),
                "write file to '"+directory+"' has done!",
                -1,-1,true);
    }

    public DMLOperation insertData(Connection connection, String databaseName, String tableName,
                                   List<KeyMap> partitionList, List<List<String>> valueList){
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, databaseName,
                operation.insertData(databaseName,tableName)
                        .addOnePartition(partitionList)
                       .addValues(valueList).getStringbuilder().toString(),
                "insert datas into table "+tableName+" has done!",
                -1,-1,true);
    }

    public DMLOperation insertQueryData(Connection connection, String databaseName, String tableName, List<KeyMap> partitionList, boolean isOverwrite, String hql){
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, databaseName,
                operation.insertQueryData(isOverwrite,databaseName,tableName)
                        .addOnePartition(partitionList)
                        .addSelect(isOverwrite,hql).getStringbuilder().toString(),
                "insert datas from queries into table "+tableName+" has done!",
                -1,-1,true);
    }

    public DMLOperation updateTableDatas(Connection connection, String databaseName, String tableName, List<KeyMap> valueList, String where){
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, databaseName,
                operation.addUpdate(databaseName,tableName,valueList)
                        .addWhere(where).getStringbuilder().toString(),
                "update table "+tableName+" datas has done!",
                -1,-1,true);
    }

    public DMLOperation deleteTableDatas(Connection connection, String databaseName, String tableName, String where){
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, databaseName,
                operation.addDelete(databaseName,tableName)
                        .addWhere(where).getStringbuilder().toString(),
                "delete table "+tableName+" datas has done!",
                -1,-1,true);
    }

    public DMLOperation mergeTable(Connection connection, String targetDatabaseName, String targetTableName, boolean isTable,
                                   String sourceDatabaseName, String sourceTableName, String sqlExpression, String judgeExpression,
                                   String updateExpression, List<KeyMap> setList,
                                   String deleteExpression, String insertExpression, List<String> valueList){
        DMLResult result = new DMLResult();
        DMLHQL operation = new DMLHQL();
        return result.getResult(connection, targetDatabaseName,
                operation.addMerge(targetDatabaseName,targetTableName,isTable,sourceDatabaseName,
                        sourceTableName,sqlExpression,judgeExpression)
                        .addUpdateMatch(updateExpression,setList)
                        .addDeleteMatch(deleteExpression)
                        .addInsertMatch(insertExpression,valueList).getStringbuilder().toString(),
                "merge datas to table "+targetTableName+" has done!",
                -1,-1,true);
    }

}
