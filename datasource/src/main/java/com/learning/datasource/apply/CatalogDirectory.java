package com.learning.datasource.apply;

import com.learning.datasource.ddl.DatabaseOperation;
import com.learning.datasource.ddl.TableOperation;
import com.learning.datasource.dml.Manipulation;
import com.learning.schema.CatalogItem;
import com.learning.schema.ColumnInfo;
import com.learning.schema.ColumnSort;
import com.learning.schema.input.TableCreateInput;
import com.learning.schema.output.DMLOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class CatalogDirectory {

    public boolean addDirectory(Connection connection, String pid, String dirName, String dirType, String topicID, String userName, String dirDesc, String hql, String brief, List<String> dirLabel, String format){
        DMLOperation dml;
        createDirectory(connection);
        createDirdetail(connection);
        createLabel(connection);
        UUID id = UUID.randomUUID();
        List<List<String>> valueList = new ArrayList<>();
        List<String> s = new ArrayList<>();
        s.add(id.toString());
        s.add(pid);
        s.add(dirName);
        s.add(dirType);
        s.add(topicID);
        s.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        s.add(userName);
        s.add(dirDesc);
        s.add(brief);
        s.add(dirLabel.toString().replace("[","").replace("]","").replace(" ",""));
        s.add(format);
        valueList.add(s);
        dml = new Manipulation().insertData(connection, "catalog", "directories", new ArrayList<>(), valueList);
        if(Objects.equals(dirType, "item")) {
            List<List<String>> valueList_item = new ArrayList<>();
            List<String> s_item = new ArrayList<>();
            s_item.add(id.toString());
            s_item.add(hql);
            s_item.add("");
            valueList_item.add(s_item);
            dml = new Manipulation().insertData(connection, "catalog", "dir_details", new ArrayList<>(), valueList_item);
            if(dirLabel.size()>0) {
                List<List<String>> valueList_label = new ArrayList<>();
                for (String map : dirLabel) {
                    List<String> s_label = new ArrayList<>();
                    s_label.add(UUID.randomUUID().toString());
                    s_label.add(map);
                    s_label.add(id.toString());
                    s_label.add(dirName);
                    s_label.add(topicID);
                    valueList_label.add(s_label);
                }
                dml = new Manipulation().insertData(connection, "catalog", "labels", new ArrayList<>(), valueList_label);
            }
        }
        return (dml.getOperationState().getStatus() != 500 );
    }

    public boolean deleteDirectory(Connection connection, String id){
        boolean result = new Manipulation().deleteTableDatas(connection, "catalog", "directories", "id='" + id + "'").getOperationState().getStatus() != 500;
        result &= new Manipulation().deleteTableDatas(connection,"catalog","labels","dir_id='"+id+"'").getOperationState().getStatus()!=500;
        return result;
    }

    public boolean updateLabel(Connection connection, String dir_id, String dir_name, String topicID, List<String> dirLabelAdd, List<String> dirLabelDelete){
        boolean result = true;
        StringBuilder s = new StringBuilder();
        if(dirLabelDelete.size()>0) {
            s.append(" and");
            for (String label : dirLabelDelete) {
                s.append(" name='").append(label).append("' or");
            }
            s = new StringBuilder(s.substring(0, s.length() - 2));
            result = new Manipulation().deleteTableDatas(connection, "catalog", "labels", "dir_id='" + dir_id + "'" + s).getOperationState().getStatus() != 500;
        }
        if(dirLabelAdd.size()>0){
            List<List<String>> valueList = new ArrayList<>();
            for (String label : dirLabelAdd) {
                List<String> list = new ArrayList<>();
                list.add(UUID.randomUUID().toString());
                list.add(label);
                list.add(dir_id);
                list.add(dir_name);
                list.add(topicID);
                valueList.add(list);
            }
            result &= new Manipulation().insertData(connection,"catalog","labels",new ArrayList<>(),valueList).getOperationState().getStatus()!=500;
        }
        return result;
    }

    private void createDirectory(Connection connection){
        new DatabaseOperation().createDatabase(connection, "catalog", "data catalog");
        List<ColumnInfo> columnList = new ArrayList<>();
        columnList.add(new ColumnInfo("id", "string", "directory id"));
        columnList.add(new ColumnInfo("pid", "string", "directory parent id"));
        columnList.add(new ColumnInfo("name", "string", "directory name"));
        columnList.add(new ColumnInfo("type", "string", "directory type"));
        columnList.add(new ColumnInfo("topic", "string", "topic id"));
        columnList.add(new ColumnInfo("time", "string", "when the directory created"));
        columnList.add(new ColumnInfo("creator", "string", "who created the directory"));
        columnList.add(new ColumnInfo("desc", "string", "some extra descriptions"));
        columnList.add(new ColumnInfo("brief", "string", "brief contents"));
        columnList.add(new ColumnInfo("label", "string", "labels"));
        columnList.add(new ColumnInfo("format", "string", "data format"));
        List<ColumnSort> cluster = new ArrayList<>();
        ColumnSort sort = new ColumnSort();
        sort.setColumnName("type");
        sort.setSortType("-1");
        cluster.add(sort);
        TableCreateInput input = new TableCreateInput("catalog", "", "directories", columnList, "directories of data catalog",
                new ArrayList<>(), cluster, 32, new ArrayList<>(), new ArrayList<>(), "", "ORC TBLPROPERTIES('transactional'='TRUE')");
        new TableOperation().createTable(connection, input);
    }

    private void createDirdetail(Connection connection){
        new DatabaseOperation().createDatabase(connection, "catalog", "data catalog");
        List<ColumnInfo> columnList = new ArrayList<>();
        columnList.add(new ColumnInfo("id", "string", "directory id"));
        columnList.add(new ColumnInfo("content", "string", "all contents"));
        columnList.add(new ColumnInfo("attr", "string", "some extra descriptions"));
        TableCreateInput input = new TableCreateInput("catalog", "", "dir_details", columnList, "details of directory",
                new ArrayList<>(), new ArrayList<>(), 0, new ArrayList<>(), new ArrayList<>(), "", "PARQUET");
        new TableOperation().createTable(connection, input);
    }

    private void createLabel(Connection connection){
        new DatabaseOperation().createDatabase(connection, "catalog", "data catalog");
        List<ColumnInfo> columnList = new ArrayList<>();
        columnList.add(new ColumnInfo("id", "string", "label id"));
        columnList.add(new ColumnInfo("name", "string", "label name"));
        columnList.add(new ColumnInfo("dir_id", "string", "directory id"));
        columnList.add(new ColumnInfo("dir_name", "string", "directory name"));
        columnList.add(new ColumnInfo("topic", "string", "topic id"));
        List<ColumnSort> cluster = new ArrayList<>();
        ColumnSort sort = new ColumnSort();
        sort.setColumnName("topic");
        sort.setSortType("-1");
        cluster.add(sort);
        TableCreateInput input = new TableCreateInput("catalog", "", "labels", columnList, "directory labels",
                new ArrayList<>(), cluster, 32, new ArrayList<>(), new ArrayList<>(), "", "ORC TBLPROPERTIES('transactional'='TRUE')");
        new TableOperation().createTable(connection, input);
    }

    public List<CatalogItem> getDirectories(Connection connection, String topicID, String pid, String type, int pageIndex, int pageSize){
        pid = Objects.equals(pid, "-1") ?"":pid;
        int start = (pageIndex-1)*pageSize;
        int end = pageIndex*pageSize;
        List<CatalogItem> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use catalog");
            ResultSet rs = smt.executeQuery("select * from directories where pid='"+pid+"' and topic='"+topicID+"' and type='"+type+"'");
            int count = 0;
            while(rs.next()){
                if(pageIndex<0||pageSize<0||(count>=start&&count<end)) {
                    CatalogItem lb = new CatalogItem();
                    lb.setId(rs.getString("id"));
                    lb.setName(rs.getString("name"));
                    lb.setType(rs.getString("type"));
                    lb.setCreateTime(rs.getString("time"));
                    lb.setCreator(rs.getString("creator"));
                    lb.setBrief(rs.getString("brief"));
                    lb.setLabels(Arrays.asList(rs.getString("label").split(",")));
                    lb.setFormat(rs.getString("format"));
                    result.add(lb);
                }else if(count>=end) {
                    break;
                }
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<CatalogItem> getDirFromLabel(Connection connection, String labelName, String topicID){
        List<CatalogItem> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use catalog");
            ResultSet rs = smt.executeQuery("select a.id,a.name,a.type,a.time,a.creator,a.brief,a.label,a.format from (select * from labels where name='"+labelName+"' and topic='"+topicID+"')t left join directories a on t.dir_id=a.id");
            while(rs.next()){
                CatalogItem lb = new CatalogItem();
                lb.setId(rs.getString("id"));
                lb.setName(rs.getString("name"));
                lb.setType(rs.getString("type"));
                lb.setCreateTime(rs.getString("time"));
                lb.setCreator(rs.getString("creator"));
                lb.setBrief(rs.getString("brief"));
                lb.setLabels(Arrays.asList(rs.getString("label").split(",")));
                lb.setFormat(rs.getString("format"));
                result.add(lb);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<String> getLabels(Connection connection, String topicID){
        List<String> result = new ArrayList<>();
        try (Statement smt = connection.createStatement()) {
            smt.execute("use catalog");
            ResultSet rs = smt.executeQuery("select DISTINCT name from labels where topic='"+topicID+"'");
            while(rs.next()){
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
