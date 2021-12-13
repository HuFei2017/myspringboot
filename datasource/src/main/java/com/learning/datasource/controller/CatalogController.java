package com.learning.datasource.controller;

import com.learning.datasource.apply.CatalogDirectory;
import com.learning.datasource.apply.CatalogTopic;
import com.learning.datasource.dml.Manipulation;
import com.learning.schema.CatalogItem;
import com.learning.schema.input.CatalogInput;
import com.learning.schema.input.TopicInput;
import com.learning.schema.output.DMLOperation;
import com.learning.schema.output.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CatalogController {

    @Autowired
    HttpServletRequest request;

    @Qualifier("dataSource")
    @Autowired
    DataSource source;

    //数据目录操作
    @PostMapping("/datahouse/catalog/topic/add")
    public boolean addTopic(@RequestBody TopicInput input){
        boolean result = false;
        try (Connection connection = source.getConnection()) {
            result = new CatalogTopic().addTopic(connection,input.getUserName(),input.getTopicName(),input.getPicAddress(),input.getAttr());
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/datahouse/catalog/get/topic/{topicName}/{isAccuracy}")
    public List<Topic> getTopics(@PathVariable("topicName") String topicName, @PathVariable("isAccuracy") String isAccuracy){
        List<Topic> result = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            result = new CatalogTopic().getTopics(connection,topicName,isAccuracy);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/datahouse/catalog/delete/topicID/{topicID}")
    public boolean deleteTopic(@PathVariable String topicID) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new CatalogTopic().deleteTopic(connection,topicID);
        }
    }

    @GetMapping("/datahouse/catalog/topicClick/{topicID}/{topicName}")
    public boolean clickTopic(@PathVariable("topicID") String topicID, @PathVariable("topicName") String topicName) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new CatalogTopic().addClickRate(connection,topicID,topicName);
        }
    }

    @GetMapping("/datahouse/catalog/get/hottopics")
    public List<Topic> getHotTopics(){
        List<Topic> result = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            result = new CatalogTopic().getHotTopics(connection);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/datahouse/catalog/getDir/directories/{topicID}/{pid}")
    public List<CatalogItem> getDirectoriesDir(@PathVariable("topicID") String topicID, @PathVariable("pid") String pid){
        List<CatalogItem> result = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            result = new CatalogDirectory().getDirectories(connection,topicID,pid,"dir",-1,-1);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/datahouse/catalog/getItem/directories/{topicID}/{pid}/{pageIndex}/{pageSize}")
    public List<CatalogItem> getDirectoriesItem(@PathVariable("topicID") String topicID, @PathVariable("pid") String pid,
                                                @PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize){
        List<CatalogItem> result = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            result = new CatalogDirectory().getDirectories(connection,topicID,pid,"item",pageIndex,pageSize);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @PostMapping("/datahouse/catalog/add/directories")
    public boolean addDirectories(@RequestBody CatalogInput input) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new CatalogDirectory().addDirectory(connection,input.getPid(),input.getDirName(),input.getDirType(),
                    input.getTopicID(),input.getUserName(),input.getDirDesc(),input.getHql(),
                    input.getBrief(),input.getDirLabel(),input.getFormat());
        }
    }

    @GetMapping("/datahouse/catalog/get/metaData/{itemID}/{pageIndex}/{pageSize}")
    public DMLOperation getMetaDta(@PathVariable("itemID") String itemID, @PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize)throws SQLException {
        try (Connection connection1 = source.getConnection(); Connection connection2 = source.getConnection()) {
            Statement smt = connection1.createStatement();
            ResultSet rs = smt.executeQuery("select * from catalog.dir_details where id='"+itemID+"'");
            String sql = "";
            while (rs.next()){
                sql = rs.getString("content");
                break;
            }
            return new Manipulation().executeQuery(connection2,"default",sql,pageIndex,pageSize,true);
        }
    }

    @GetMapping("/datahouse/catalog/delete/itemID/{itemID}")
    public boolean deleteDirectories(@PathVariable("itemID") String itemID) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new CatalogDirectory().deleteDirectory(connection,itemID);
        }
    }

    @GetMapping("/datahouse/catalog/get/labels/topicID/{topicID}")
    public List<String> getLabels(@PathVariable("topicID") String topicID) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new CatalogDirectory().getLabels(connection,topicID);
        }
    }

    @GetMapping("/datahouse/catalog/fromlabel/getDir/{labelName}/{topicID}")
    public List<CatalogItem> getDirFromLabel(@PathVariable("labelName") String labelName, @PathVariable("topicID") String topicID) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new CatalogDirectory().getDirFromLabel(connection,labelName,topicID);
        }
    }

}
