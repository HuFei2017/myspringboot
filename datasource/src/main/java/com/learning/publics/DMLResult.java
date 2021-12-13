package com.learning.publics;

import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.learning.schema.TableInstance;
import com.learning.schema.output.DMLOperation;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DMLResult {

    public DMLOperation getResult(Connection connection, String databaseName, String hql, String message, int pageIndex, int pageSize, boolean isFill){
        boolean hasResultSet = false;
        int start = (pageIndex-1)*pageSize;
        int end = pageIndex*pageSize;
        TableInstance resultSet = new TableInstance();
        DMLOperation result = new DMLOperation();
        String sql = "use " + databaseName;
        try (Statement smt = connection.createStatement()) {
            StopWatch watch = new StopWatch();
            watch.start();
            smt.execute(sql);
            if(hql.trim().toLowerCase().startsWith("select ")){
                hasResultSet =true;
                ResultSet rs = smt.executeQuery(hql);
                watch.stop();
                if(isFill){
                    ArrayList<String> columnList = new ArrayList<>();
                    if (rs != null) {
                        int size = rs.getMetaData().getColumnCount();
                        for (int i = 1; i <= size; i++) {
                            String name = rs.getMetaData().getColumnName(i);
                            columnList.add(name.substring(name.indexOf(".")+1));
                        }
                        resultSet.setColumnList(columnList);
                        List<List<String>> item = new ArrayList<>();
                        int count = 0;
                        while (rs.next()) {
                            if(pageIndex<0||pageSize<0||(count>=start&&count<end)){
                                List<String> queryitem = new ArrayList<>();
                                for (int i = 1; i <= columnList.size(); i++) {
                                    queryitem.add(rs.getString(i));
                                }
                                item.add(queryitem);
                            }else if(count>=end) {
                               break;
                            }
                            count++;
                        }
                        resultSet.setQueryResult(item);
                        resultSet.setTimeConsuming(watch.getTotalTimeMillis());
                    }
                }
            }
            else {
                smt.execute(hql);
                watch.stop();
                resultSet.setTimeConsuming(watch.getTotalTimeMillis());
            }
            result = result.getResult("OK",resultSet, message,hasResultSet);
        } catch (SQLException e) {
            result = result.getResult("Failed",resultSet, e.getMessage(),hasResultSet);
        }
        return result;
    }

    public DMLOperation getResult(Session session, String databaseName, String cql, String message, int pageIndex, int pageSize, boolean isFill){
        boolean hasResultSet = false;
        int start = (pageIndex-1)*pageSize;
        int end = pageIndex*pageSize;
        TableInstance resultSet = new TableInstance();
        DMLOperation result = new DMLOperation();
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            session.execute("USE "+databaseName);
            if(cql.trim().toLowerCase().startsWith("select ")){
                hasResultSet =true;
                com.datastax.driver.core.ResultSet rs = session.execute(cql);
                watch.stop();
                int size = 0;
                if(isFill){
                    ArrayList<String> columnList = new ArrayList<>();
                    if (rs != null) {
                        for (ColumnDefinitions.Definition definition : rs.getColumnDefinitions()){
                            columnList.add(definition.getName());
                            size ++;
                        }
                        resultSet.setColumnList(columnList);
                        List<List<String>> item = new ArrayList<>();
                        int count = 0;
                        for(Row row : rs) {
                            if(pageIndex<0||pageSize<0||(count>=start&&count<end)){
                                List<String> queryitem = new ArrayList<>();
                                for (int i = 0; i < size; i++) {
                                    queryitem.add(row.getObject(i).toString());
                                }
                                item.add(queryitem);
                            }else if(count>=end) {
                                break;
                            }
                            count++;
                        }
                        resultSet.setQueryResult(item);
                        resultSet.setTimeConsuming(watch.getTotalTimeMillis());
                    }
                }
            }
            else {
                session.execute(cql);
                watch.stop();
                resultSet.setTimeConsuming(watch.getTotalTimeMillis());
            }
            result = result.getResult("OK",resultSet, message,hasResultSet);
        } catch (Exception e) {
            result = result.getResult("Failed",resultSet, e.getMessage(),hasResultSet);
        }
        return result;
    }

}
