package com.learning.datasource.apply;

import com.learning.datasource.ddl.DatabaseOperation;
import com.learning.datasource.ddl.TableOperation;
import com.learning.datasource.dml.Manipulation;
import com.learning.schema.ColumnInfo;
import com.learning.schema.ColumnSort;
import com.learning.schema.KeyMap;
import com.learning.schema.input.TableCreateInput;
import com.learning.schema.output.DMLOperation;
import com.learning.schema.output.Topic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CatalogTopic {

    public boolean addTopic(Connection connection, String userName, String topicName, String picAddress, String attr){
        DMLOperation dml;
        new DatabaseOperation().createDatabase(connection, "catalog", "data catalog");
        List<ColumnInfo> columnList = new ArrayList<>();
        columnList.add(new ColumnInfo("id", "string", "topic id"));
        columnList.add(new ColumnInfo("name", "string", "topic name"));
        columnList.add(new ColumnInfo("creator", "string", "who create the topic"));
        columnList.add(new ColumnInfo("time", "string", "when the topic created"));
        columnList.add(new ColumnInfo("address", "string", "the topic photo address"));
        columnList.add(new ColumnInfo("attr", "string", "some extra descriptions"));
        List<ColumnSort> cluster = new ArrayList<>();
        ColumnSort sort = new ColumnSort();
        sort.setColumnName("name");
        sort.setSortType("-1");
        cluster.add(sort);
        TableCreateInput input = new TableCreateInput("catalog", "", "topics", columnList, "topics of data catalog",
                new ArrayList<>(), cluster, 32, new ArrayList<>(), new ArrayList<>(), "", "ORC TBLPROPERTIES('transactional'='TRUE')");
        new TableOperation().createTable(connection, input);
        List<List<String>> valueList = new ArrayList<>();
        List<String> s = new ArrayList<>();
        s.add(UUID.randomUUID().toString());
        s.add(topicName);
        s.add(userName);
        s.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        s.add(picAddress);
        s.add(attr);
        valueList.add(s);
        dml = new Manipulation().insertData(connection, "catalog", "topics", new ArrayList<>(), valueList);
        return (dml.getOperationState().getStatus() != 500 );
    }

    public boolean deleteTopic(Connection connection, String topicID){
        boolean result = new Manipulation().deleteTableDatas(connection, "catalog", "topics", "id='" + topicID + "'").getOperationState().getStatus() != 500;
        result &= new Manipulation().deleteTableDatas(connection,"catalog","directories","topic='"+topicID+"'").getOperationState().getStatus()!=500;
        result &= new Manipulation().deleteTableDatas(connection,"catalog","labels","topic='"+topicID+"'").getOperationState().getStatus()!=500;
        result &= new Manipulation().deleteTableDatas(connection,"catalog","topic_clickrate","id='"+topicID+"'").getOperationState().getStatus()!=500;
        return result;
    }

    public List<Topic> getTopics(Connection connection, String topicName, String isAccuracy){
        List<Topic> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use catalog");
            String sql = "select * from topics where name like '%"+(topicName.equals("-1")?"":topicName)+"%'";
            if(isAccuracy.toLowerCase().contains("true")||isAccuracy.contains("1"))
                sql = "select * from topics where name ='"+(topicName.equals("-1")?"":topicName)+"'";
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next()){
                Topic topic = new Topic();
                topic.setId(rs.getString("id"));
                topic.setName(rs.getString("name"));
                topic.setCreator(rs.getString("creator"));
                topic.setTime(rs.getString("time"));
                topic.setAddress(rs.getString("address"));
                topic.setAttr(rs.getString("attr"));
                result.add(topic);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public boolean addClickRate(Connection connection, String topicID, String topicName){
        DMLOperation dml;
        new DatabaseOperation().createDatabase(connection, "catalog", "data catalog");
        List<ColumnInfo> columnList = new ArrayList<>();
        columnList.add(new ColumnInfo("id", "string", "topic id"));
        columnList.add(new ColumnInfo("name", "string", "topic name"));
        columnList.add(new ColumnInfo("rate", "int", "how many times the topic clicked"));
        List<ColumnSort> cluster = new ArrayList<>();
        ColumnSort sort = new ColumnSort();
        sort.setColumnName("name");
        sort.setSortType("-1");
        cluster.add(sort);
        TableCreateInput input = new TableCreateInput("catalog", "", "topic_clickrate", columnList, "click rates of topics",
                new ArrayList<>(), cluster, 32, new ArrayList<>(), new ArrayList<>(), "", "ORC TBLPROPERTIES('transactional'='TRUE')");
        new TableOperation().createTable(connection, input);
        List<List<String>> valueList = new ArrayList<>();
        List<String> s = new ArrayList<>();
        s.add(topicID);
        s.add(topicName);
        s.add("1");
        valueList.add(s);
        dml = new Manipulation().executeQuery(connection,"catalog","select * from topic_clickrate where id='"+topicID+"'",-1,-1,true);
        if(dml.getResultSet().getQueryResult().size()<=0)
            dml = new Manipulation().insertData(connection, "catalog", "topic_clickrate", new ArrayList<>(), valueList);
        else {
            int num = Integer.parseInt(dml.getResultSet().getQueryResult().get(0).get(2));
            List<KeyMap> mapList = new ArrayList<>();
            KeyMap km = new KeyMap();
            km.setName("rate");
            km.setValue(Integer.toString(num+1));
            mapList.add(km);
            dml = new Manipulation().updateTableDatas(connection, "catalog", "topic_clickrate",  mapList,"id='"+topicID+"'");
        }
        return (dml.getOperationState().getStatus() != 500 );
    }

    public List<Topic> getHotTopics(Connection connection){
        List<Topic> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use catalog");
            ResultSet rs = smt.executeQuery("select * from topic_clickrate order by rate desc");
            while(rs.next()){
                Topic topic = new Topic();
                topic.setId(rs.getString("id"));
                topic.setName(rs.getString("name"));
                result.add(topic);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
