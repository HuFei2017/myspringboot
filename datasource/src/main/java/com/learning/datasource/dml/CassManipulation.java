package com.learning.datasource.dml;

import com.datastax.driver.core.Session;
import com.learning.publics.DMLResult;
import com.learning.schema.KeyMap;
import com.learning.schema.Operation;
import com.learning.schema.output.DMLOperation;

import java.util.List;

public class CassManipulation {

    public DMLOperation executeQuery(Session session, String databaseName, String cql, int pageIndex, int pageSize, boolean isFill){
        DMLResult result = new DMLResult();
        return result.getResult(session, databaseName,cql+" ALLOW FILTERING","operation executed successful!",pageIndex,pageSize,isFill);
    }

    public DMLOperation getQueryResult(Session session, String databaseName, String tableName, String format,
                                       boolean isAll, List<KeyMap> clauseList, List<Operation> whereList,
                                       List<String> groupList, List<KeyMap> orderList, int partitionLimit,
                                       int limit, boolean isFiltering, int pageIndex, int pageSize){
        DMLResult result = new DMLResult();
        DMLCQL operation = new DMLCQL();
        return result.getResult(session, databaseName,
                operation.addSelect()
                        .addFormat(format)
                        .addClause(isAll,clauseList)
                        .addFrom(databaseName,tableName)
                        .addWhere(whereList)
                        .addGroup(groupList)
                        .addOrder(orderList)
                        .addPartitionLimit(partitionLimit)
                        .addLimit(limit)
                        .addFilter(isFiltering).getStringbuilder().toString(),
                "select datas has done!",
                pageIndex,pageSize,true);
    }

    public DMLOperation insertByJson(Session session, String databaseName, String tableName, String json, String def){
        DMLResult result = new DMLResult();
        DMLCQL operation = new DMLCQL();
        return result.getResult(session, databaseName,
                operation.addInsert(databaseName,tableName)
                        .addJson(json,def)
                        .getStringbuilder().toString(),
                "insert datas into table "+tableName+" has done!",
                -1,-1,true);
    }

    public DMLOperation  insertByValues(Session session, String databaseName, String tableName,
                                        List<String> columnList, List<List<String>> valueList, List<KeyMap> paramList){
        DMLResult result = new DMLResult();
        DMLCQL operation = new DMLCQL();
        return result.getResult(session, databaseName,
                operation.addInsert(databaseName,tableName)
                        .addValues(columnList,valueList)
                        .addIfNot(false)
                        .addParameter(paramList)
                        .getStringbuilder().toString(),
                "insert datas into table "+tableName+" has done!",
                -1,-1,true);
    }

    public DMLOperation updateValues(Session session, String databaseName, String tableName, List<KeyMap> paramList,
                                     List<KeyMap> valueList, List<Operation> whereList, List<Operation> conditionList){
        DMLResult result = new DMLResult();
        DMLCQL operation = new DMLCQL();
        return result.getResult(session, databaseName,
                operation.addUpdate(databaseName,tableName)
                        .addParameter(paramList)
                        .addSet(valueList)
                        .addWhere(whereList)
                        .addConditions(conditionList)
                        .getStringbuilder().toString(),
                "update datas from table "+tableName+" has done!",
                -1,-1,true);
    }

    public DMLOperation deleteValues(Session session, String databaseName, String tableName, List<String> simple,
                                     List<KeyMap> paramList, List<Operation> whereList, List<Operation> conditionList){
        DMLResult result = new DMLResult();
        DMLCQL operation = new DMLCQL();
        return result.getResult(session, databaseName,
                operation.addDelete()
                        .addSimple(simple)
                        .addFrom(databaseName,tableName)
                        .addParameter(paramList)
                        .addWhere(whereList)
                        .addConditions(conditionList)
                        .getStringbuilder().toString(),
                "delete datas from table "+tableName+" has done!",
                -1,-1,true);
    }

    public DMLOperation batch(Session session, String databaseName, String format, List<KeyMap> paramList, List<String> cqlList){
        DMLResult result = new DMLResult();
        DMLCQL operation = new DMLCQL();
        return result.getResult(session, databaseName,
                operation.addBatch(format)
                        .addParameter(paramList)
                        .addCql(cqlList)
                        .addApply()
                        .getStringbuilder().toString(),
                "operations has done!",
                -1,-1,true);
    }

}
